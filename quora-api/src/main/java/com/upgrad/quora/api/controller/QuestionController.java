package com.upgrad.quora.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import com.upgrad.quora.service.Business;
import org.springframework.http.MediaType;

@RestController
@RequestMapping("/api/v1/")
public class QuestionController {
  
  @Autowired
  private QuestionBusinessService questionBusinessService;
 /* @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(final QuestionRequest questionRequest) {

        final UserEntity userEntity = new UserEntity();

        final UserEntity createdUserEntity = signupBusinessService.signup(userEntity);
        QuestionResponse questionResponse = new QuestionResponse().id(createdUserEntity.getUuid()).status("Question created successfully");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.CREATED);
    }*/
  
  /* @RequestMapping(method = RequestMethod.GET, path = "/question/all", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDetailsResponse> getAllQuestions() {
       QuestionDetailsResponse questionResponse = new QuestionDetailsResponse().id(createdUserEntity.getUuid()).status("Questions fetched successfully");
        return new ResponseEntity<QuestionResponse>(questionResponse, HttpStatus.OK);
    }*/
  /* @RequestMapping(method = RequestMethod.GET, path = "question/edit/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestion(final QuestionEditRequest questionEditRequest) {
       QuestionEditResponse questionResponse = new QuestionEditResponse().id(createdUserEntity.getUuid()).status("Questions changed successfully");
        return new ResponseEntity<QuestionEditResponse>(questionResponse, HttpStatus.OK);
    }*/
}
