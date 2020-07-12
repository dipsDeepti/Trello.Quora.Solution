package com.upgrad.quora.api.controller;
import com.upgrad.quora.api.model.QuestionDetailsResponse;
import com.upgrad.quora.service.entity.QuestionEntity;
import com.upgrad.quora.api.model.QuestionRequest;
import com.upgrad.quora.api.model.QuestionResponse;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.upgrad.quora.service.business.QuestionService;
import com.upgrad.quora.service.business.UserBusinessService;
import java.util.Date;

@RestController
@RequestMapping("/api/v1/")
public class QuestionController {
  
  @Autowired
  private QuestionService questionService;
  @Autowired
  UserBusinessService userBusinessService;
    @RequestMapping(method = RequestMethod.POST, path = "/question/create",  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createQuestion(@RequestParam String content, @RequestHeader("authorization") final String authorizationToken)
          throws AuthorizationFailedException
  {
      final UserEntity userEntity = userBusinessService.getUserIdByAuthorizationToken(authorizationToken);
      final QuestionEntity questionEntity = new QuestionEntity();
      questionEntity.setContent(content);
      questionEntity.setDate(new Date());
      questionEntity.setUuid(userEntity.getUuid());
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
