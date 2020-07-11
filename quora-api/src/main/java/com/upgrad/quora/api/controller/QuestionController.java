package com.upgrad.quora.api.controller;
import com.upgrad.quora.api.model.QuestionDetailsResponse;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.upgrad.quora.service.business.QuestionService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
  
   @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions(@RequestHeader("authorization") final String authorizationToken) {
      List<QuestionEntity> questionEntities = questionService.getAllQuestions();
      List<QuestionDetailsResponse> responses = new ArrayList<QuestionDetailsResponse>();

      for(QuestionEntity entity: questionEntities)
       {
            QuestionDetailsResponse questionResponse = new QuestionDetailsResponse();
            questionResponse.setId(entity.getUuid());
            questionResponse.setContent(entity.getContent());
            responses.add(questionResponse);
       }

       return new ResponseEntity<List<QuestionDetailsResponse>>(responses, HttpStatus.OK);
    }

  /* @RequestMapping(method = RequestMethod.GET, path = "question/edit/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestion(final QuestionEditRequest questionEditRequest) {
       QuestionEditResponse questionResponse = new QuestionEditResponse().id(createdUserEntity.getUuid()).status("Questions changed successfully");
        return new ResponseEntity<QuestionEditResponse>(questionResponse, HttpStatus.OK);
    }*/
}
