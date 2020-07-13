package com.upgrad.quora.service.dao;

import com.upgrad.quora.service.entity.AnswerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class AnswerDao {

  @PersistenceContext private EntityManager entityManager;

  /**
   * Creates an answer in the DB.
   *
   * @param answerEntity represents a row of information which is to be persisted.
   * @return persisted answer entity.
   */
  public AnswerEntity createAnswer(AnswerEntity answerEntity) {
    entityManager.persist(answerEntity);
    return answerEntity;
  }

  /**
   * Fetches an answer from DB based on the answerI
   */
  public AnswerEntity getAnswerById(final String answerId) {
    try {
      return entityManager
          .createNamedQuery("getAnswerById", AnswerEntity.class)
          .setParameter("uuid", answerId)
          .getSingleResult();
    } catch (NoResultException nre) {
      return null;
    }
  }

  /**
   * updates the  information in answer table.
   */
  public void updateAnswer(AnswerEntity answerEntity) {
    entityManager.merge(answerEntity);
  }

  /**
   * Delete a answer by given answerId from the DB.
   */
  public AnswerEntity deleteAnswer(final String answerId) {
    AnswerEntity deleteAnswer = getAnswerById(answerId);
    if (deleteAnswer != null) {
      entityManager.remove(deleteAnswer);
    }
    return deleteAnswer;
  }

  /** fetch all the answers to the question using questionId
   *
   * @param questionId
   * @return
   */
  public List<AnswerEntity> getAllAnswersToQuestion(final String questionId) {
    return entityManager
        .createNamedQuery("getAllAnswersToQuestion", AnswerEntity.class)
        .setParameter("uuid", questionId)
        .getResultList();
  }
}
