package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserAuthDao userAuthDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity createQuestion(String content, UserAuthEntity userEntity)
    {
        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(content);
        questionEntity.setDate(new Date());
        questionEntity.setUuid(UUID.randomUUID().toString());
        questionEntity.setUser(userEntity.getUserEntity());
        return questionDao.createQuestion(questionEntity);
    }

    public List<QuestionEntity> getAllQuestions()
    {
        return questionDao.getAllQuestions();
    }
    public QuestionEntity editQuestionContent(String questionId, String content, UserEntity userEntity)
    {
        QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setUser(userEntity);
        questionEntity.setId(Integer.parseInt(questionId));
        questionEntity.setContent(content);
        return questionDao.editQuestionContent(questionEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQuestion(String questionId, String authToken) throws AuthorizationFailedException,
            InvalidQuestionException {

        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthToken(authToken);
        if(userAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else{
            if(userAuthEntity.getLogoutAt() !=  null) {
                throw new AuthorizationFailedException("ATHR-002",
                        "User is signed out.Sign in first to delete a question");
            }
            else{
                UserEntity userEntity = userAuthEntity.getUserEntity();
                QuestionEntity questionEntity = questionDao.getQuestion(questionId);
                if(questionEntity == null){
                    throw new InvalidQuestionException("QUES-001",
                            "Entered question uuid does not exist");
                }else{
                    if (userEntity.getRole().equals("admin") ||
                            questionEntity.getUser().getUuid().equals(userEntity.getUuid())) {
                        questionDao.deleteQuestion(questionEntity);
                    }else{
                        throw new AuthorizationFailedException("ATHR-003",
                                "Only the question owner or admin can delete the question");
                    }
                }

            }
        }
    }
}
