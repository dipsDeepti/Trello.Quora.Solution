package com.upgrad.quora.api.controller;

import com.upgrad.quora.api.model.AnswerDetailsResponse;
import com.upgrad.quora.api.model.UserDetailsResponse;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.upgrad.quora.service.business.UserBusinessService;


@RestController
@RequestMapping
public class CommonController {

    @Autowired
    private UserBusinessService userBusinessService;

    // getUserProfile method authenticates a user via authorisationToken
    // and fetch all profile details of the user from database
    @RequestMapping(method = RequestMethod.GET, path = "/userprofile/{userId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<UserDetailsResponse> getUserProfile(@PathVariable("userId") final String userUuid, @RequestHeader("authorization") final String authorizationToken)
            throws UserNotFoundException, AuthorizationFailedException {
        final UserEntity userEntity = userBusinessService.getUser(userUuid, authorizationToken);
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse().firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName()).emailAddress(userEntity.getEmail()).dob(userEntity.getDob())
                .country(userEntity.getCountry()).country(userEntity.getCountry())
                .contactNumber(userEntity.getContactnumber()).aboutMe(userEntity.getAboutme())
                .userName(userEntity.getUserName());
        return new ResponseEntity<UserDetailsResponse>(userDetailsResponse, HttpStatus.OK);
    }

}
