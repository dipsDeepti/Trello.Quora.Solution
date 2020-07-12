package com.upgrad.quora.api.controller;
import com.upgrad.quora.api.model.*;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.business.UserBusinessService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/")
public class QuestionController {
  
  @Autowired
  private QuestionService questionService;
  @Autowired
  UserBusinessService userBusinessService;

    @RequestMapping(method = RequestMethod.POST, path = "/question/create",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionResponse> createQuestion(@RequestParam String content, @RequestHeader("authorization") final String authorizationToken)
          throws AuthorizationFailedException
  {
      final UserAuthEntity userEntity = userBusinessService.getUserAuthByToken(authorizationToken);
      QuestionEntity result = questionService.createQuestion(content, userEntity);
      HttpHeaders headers = new HttpHeaders();
      headers.add("access-token", userEntity.getAccessToken());
      QuestionResponse response = new QuestionResponse();
      response.setId(result.getUuid());
      response.setStatus("Question created successfully");
      return new ResponseEntity<QuestionResponse>(response,headers,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/question/all", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<QuestionDetailsResponse>> getAllQuestions() {
        List<QuestionEntity> questionEntities = questionService.getAllQuestions();
        List<QuestionDetailsResponse> responses = new ArrayList<QuestionDetailsResponse>();
        HttpHeaders headers = new HttpHeaders();
        for(QuestionEntity entity: questionEntities)
        {
            QuestionDetailsResponse questionResponse = new QuestionDetailsResponse();
            questionResponse.setId(entity.getUuid());
            questionResponse.setContent(entity.getContent());
            responses.add(questionResponse);
        }

        return new ResponseEntity<List<QuestionDetailsResponse>>(responses, headers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "question/edit/{questionId}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionEditResponse> editQuestion(@PathVariable("questionId") final String questionId,
                                                             QuestionEditRequest requestModel,
                                                             @RequestHeader("authorization") final String authorizationToken)
            throws AuthorizationFailedException
    {
        final UserAuthEntity userEntity = userBusinessService.getUserAuthByToken(authorizationToken);
        QuestionEntity questionEntity =  questionService.editQuestionContent
                                            (questionId, requestModel.getContent(), userEntity.getUserEntity());
        HttpHeaders headers = new HttpHeaders();
        headers.add("access-token", userEntity.getAccessToken());
        QuestionEditResponse response = new QuestionEditResponse();
        response.setId(questionEntity.getUuid());
        response.setStatus("Question Edited");
        return new ResponseEntity<QuestionEditResponse>(response, headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/question/delete/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable final String questionId,
                                                                 @RequestHeader("authorization") final String authToken)
            throws AuthorizationFailedException, UserNotFoundException, InvalidQuestionException {
        questionService.deleteQuestion(questionId, authToken);
        QuestionDeleteResponse questionDeleteResponse =  new QuestionDeleteResponse().id(questionId).status("QUESTION DELETED");
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);
    }
}
