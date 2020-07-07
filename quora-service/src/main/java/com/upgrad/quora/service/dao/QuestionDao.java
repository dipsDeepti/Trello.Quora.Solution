package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.QuestionEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Repository
public class QuestionDao {
    @PersistenceUnit(unitName = "Quora")
    private EntityManager entityManager;

    public QuestionEntity createQuestion(QuestionEntity questionEntity)
    {
        entityManager.persist(questionEntity);
        return questionEntity;
    }

    public void getAllQuestions()
    {
        //entityManager.createNamedQuery("getAllQuestion", QuestionEntity.class).getSingleResult();
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
}
