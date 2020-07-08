package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserBusinessService {

    @Autowired
    private UserDao userDao;

    // getUser method get details of user based on userUuid and authorizationToken provided by user
    public UserEntity getUser(final String userUuid, final String authorizationToken) throws UserNotFoundException,
            AuthorizationFailedException {
        UserAuthEntity userAuthTokenEntity = userDao.getUserAuthToken(authorizationToken);
        if (userAuthTokenEntity == null) {
            // case when authorizationToken not found in database
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        } else {
            String uuidString = userAuthTokenEntity.getUser().getUuid();
            if (uuidString != null) {
                UserEntity userEntity = userDao.getUser(userUuid);
                if (userEntity == null) {
                    // case when userUuid not found in database
                    throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
                } else {
                    if (uuidString.equals(userUuid)) {
                        if (userAuthTokenEntity.getLogoutAt() == null) {
                            return userEntity;
                        } else {
                            // case when user is signed out
                            throw new AuthorizationFailedException("ATHR-002", "User is signed out.Sign in first to get user details");
                        }
                    }


                }


            }
            throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
        }
    }
}
