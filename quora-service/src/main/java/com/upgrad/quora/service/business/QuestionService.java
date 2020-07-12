package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.QuestionDao;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
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
}
