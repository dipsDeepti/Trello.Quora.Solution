package com.upgrad.quora.service.business;

import com.upgrad.quora.service.dao.UserDao;
import com.upgrad.quora.service.entity.UserAuthEntity;
import com.upgrad.quora.service.entity.UserEntity;
import com.upgrad.quora.service.exception.AuthorizationFailedException;
import com.upgrad.quora.service.exception.SignUpRestrictedException;
import com.upgrad.quora.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserBusinessService {

    @Autowired
    private UserDao userDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public UserEntity deleteUser(String uuid, String authorization) throws UserNotFoundException, AuthorizationFailedException {
        UserAuthEntity userAuthEntity = userDao.getUserAuth(authorization);
        if (userAuthEntity != null) {
            if ((userAuthEntity.getLogoutAt() == null)) {
                UserEntity userEntity = userDao.getUserByUuid(uuid);
                if (userEntity == null) {
                    throw new UserNotFoundException("USR-001", "User with entered uuid does not exist");
                }
                String role = userEntity.getRole();
                if(role.equalsIgnoreCase("admin"))
                {
                    userDao.deleteUser(userEntity);
                }
                throw new AuthorizationFailedException("ATHR-003", "Unauthorized Access, Entered user is not an admin");
            }
            throw new AuthorizationFailedException("ATHR-002", "User is signed out");
        }
        throw new AuthorizationFailedException("ATHR-001", "User has not signed in");
    }


}
