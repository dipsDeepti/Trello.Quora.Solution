package com.upgrad.quora.service.Business;

import dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionBusinessService {

    @Autowired
    private QuestionDao questionDao;
}
