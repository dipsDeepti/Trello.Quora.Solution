package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuestionDao {
    @PersistenceContext
    private EntityManager entityManager;

    public QuestionEntity createQuestion(QuestionEntity questionEntity)
    {
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    public List<QuestionEntity> getAllQuestions()
    {
        try {
            return entityManager
                    .createNamedQuery("getAllQuestion", QuestionEntity.class).getResultList();
        } catch (NoResultException nre) {
            return null;
        }

    }
    public QuestionEntity editQuestionContent(QuestionEntity questionEntity)
    {
        entityManager.merge(questionEntity);
        return questionEntity;
    }

    public QuestionEntity getQuestion(String questionId) {
        try {
            return entityManager.createNamedQuery("getQuestionByUuid", QuestionEntity.class)
                    .setParameter("questionUuid", questionId).getSingleResult();
        }
        catch (NoResultException nre){
            return null;
        }
    }
    public void deleteQuestion(QuestionEntity questionEntity){
        entityManager.remove(questionEntity);
    }

}
