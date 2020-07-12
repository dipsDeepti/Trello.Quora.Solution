package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private UserDao userDao;

    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity createQuestion(QuestionEntity questionEntity)
    {
        return questionDao.createQuestion(questionEntity);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteQuestion(String questionId, String authToken) throws AuthorizationFailedException,
            InvalidQuestionException{

        UserAuthEntity userAuthEntity = userDao.getUserAuthToken(authToken);
        if(userAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
        else{
                if(userAuthEntity.getLogoutAt() !=  null) {
                    throw new AuthorizationFailedException("ATHR-002",
                            "User is signed out.Sign in first to delete a question");
                }
                else{
                    UserEntity userEntity = userAuthEntity.getUser();
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
