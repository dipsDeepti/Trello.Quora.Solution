package com.upgrad.quora.api.controller;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.entity.QuestionEntity;

@RestController
@RequestMapping("/api/v1/")
public class QuestionController {
  
  @Autowired
  private QuestionService questionService;
  @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createQuestion(@RequestParam String content) {

        final QuestionEntity questionEntity = new QuestionEntity();
        questionEntity.setContent(content);
        questionEntity.setDate(new Date("2020-05-07"));
        questionEntity.setUuid("ui");
        questionService.createQuestion(questionEntity);
        return new ResponseEntity<>("Question created successfully", HttpStatus.CREATED);
    }
  
   @RequestMapping(method = RequestMethod.GET, path = "/question/all", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> getAllQuestions() {
      // QuestionDetailsResponse questionResponse = new QuestionDetailsResponse().id(createdUserEntity.getUuid()).status("Questions fetched successfully");
        //return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.OK);
       return new ResponseEntity<>("Question created successfully", HttpStatus.CREATED);
    }

  /* @RequestMapping(method = RequestMethod.GET, path = "question/edit/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestion(final QuestionEditRequest questionEditRequest) {
       QuestionEditResponse questionResponse = new QuestionEditResponse().id(createdUserEntity.getUuid()).status("Questions changed successfully");
        return new ResponseEntity<QuestionEditResponse>(questionResponse, HttpStatus.OK);
    }*/
}
