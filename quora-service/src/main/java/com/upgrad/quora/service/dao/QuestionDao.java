package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public void editQuestionContent()
    {
        /*EntityManager em = entityManager.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            em.merge(updatedImage);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }*/
    }
    
    public QuestionEntity getQuestionById(final String questionId) {
        try {
          return entityManager
              .createNamedQuery("getQuestionById", QuestionEntity.class)
              .setParameter("uuid", questionId)
              .getSingleResult();
        } catch (NoResultException ex) {
          return null;
        }
      }

}