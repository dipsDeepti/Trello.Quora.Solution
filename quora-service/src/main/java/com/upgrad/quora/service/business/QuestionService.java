package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.dao.UserAuthDao;
import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
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

    @Autowired
    private UserDao userDao;


    /* This method accepts content of the question
     * finally creates it inside DB */
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

    /* This method accepts question Id , content and user information
    *  finally finds the question needs to be updated
    * and update it inside Db */
    @Transactional(propagation = Propagation.REQUIRED)
    public QuestionEntity editQuestionContent(String questionId, String content, UserEntity userEntity) throws InvalidQuestionException, AuthorizationFailedException {
        QuestionEntity questionEntity = questionDao.getQuestion(questionId);
        if(questionEntity == null)
        {
            throw new InvalidQuestionException("QUES-001", "Entered question uuid does not exist");
        }
        if (!questionEntity.getUser().getUuid().equals(userEntity.getUuid())) {
            throw new AuthorizationFailedException("ATHR-003", "Only the question owner can edit the answer");
        }

        questionEntity.setContent(content);
        return questionDao.editQuestionContent(questionEntity);
    }
/*
* This method deletes a question based on question Id received by user
* Only admin user or the user eho created the question can delete it*/
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

    /* this method fetches all the questions created by specific user
    * and user needs to be authenticated
     */
    public List<QuestionEntity> getAllQuestionsByUser(String userId, String authToken)
            throws AuthorizationFailedException, UserNotFoundException
    {
        UserAuthEntity userAuthEntity = userAuthDao.getUserAuthToken(authToken);
        if(userAuthEntity == null){
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }else{
            if(userAuthEntity.getLogoutAt() !=  null) {
                throw new AuthorizationFailedException("ATHR-002",
                        "User is signed out.Sign in first to get all questions posted by a specific user");
            }
            UserEntity userEntity = userDao.getUser(userId);
            if(userEntity == null){
                throw new UserNotFoundException("USR-001",
                        "User with entered uuid whose question details are to be seen does not exist");
            }
            else{
                return questionDao.getAllQuestionsByUser(userEntity);           }

        }

    }
}
