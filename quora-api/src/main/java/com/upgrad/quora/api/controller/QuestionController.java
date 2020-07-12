package com.upgrad.quora.api.controller;
import com.upgrad.quora.api.model.QuestionDeleteResponse;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.InvalidQuestionException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import com.upgrad.quora.service.business.QuestionService;

@RestController
@RequestMapping("/")
public class QuestionController {
  
  @Autowired
  private QuestionService questionService;

 @RequestMapping(method = RequestMethod.POST, path = "/question/create", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> createQuestion(@RequestParam String content) {

//        final QuestionEntity questionEntity = new QuestionEntity();
//        questionEntity.setContent(content);
//        questionEntity.setDate(new Date("2020-05-07"));
//        questionEntity.setUuid("ui");
//        questionService.createQuestion(questionEntity);
       return new ResponseEntity<>("Question created successfully", HttpStatus.CREATED);
    }
  
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

    @RequestMapping(method = RequestMethod.DELETE, path = "/question/delete/{questionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<QuestionDeleteResponse> deleteQuestion(@PathVariable final String questionId,
                                                                 @RequestHeader("authorization") final String authToken)
            throws AuthorizationFailedException, UserNotFoundException, InvalidQuestionException {
        questionService.deleteQuestion(questionId, authToken);
        QuestionDeleteResponse questionDeleteResponse =  new QuestionDeleteResponse().id(questionId).status("QUESTION DELETED");
        return new ResponseEntity<QuestionDeleteResponse>(questionDeleteResponse, HttpStatus.OK);
    }
}
