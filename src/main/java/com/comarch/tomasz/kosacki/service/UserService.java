package com.comarch.tomasz.kosacki.service;

import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.serviceExceptions.*;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserService {

    private UserDB userDB;
    private Logger log = LoggerFactory.getLogger(getClass());

    public UserService() {
    }

    public UserService(UserDB userDB) {
        this.userDB = userDB;
    }

    public UserEntity getUserById(String userId) throws AppException {

        if (userId == null) {
            log.error("User ID can not null");
            throw new NullArgumentException();
        }
        UserEntity userEntity = findUserById(userId);
        if (userEntity != null) {
            return userEntity;
        }
        log.error("User id: {} not found", userId);
        throw new UserEntityNotFoundException(userId);
    }

    public List<UserEntity> getUserBy(String userId, String userFirstName, String userLastName, String userEmail, int skip, int limit, String sortBy) {

        List<UserEntity> userEntityList = this.userDB.getUserBy(userId, userFirstName, userLastName, userEmail, skip, limit, sortBy);
        if (!userEntityList.isEmpty()) {
            return userEntityList;
        }
        log.error("User not found");
        throw new UserEntityNotFoundException("");
    }

    public void createUser(UserEntity newUser) throws AppException {

        if (newUser == null) {
            log.error("Argument can not be null");
            throw new NullArgumentException();
        }
        newUser.setCreationDate(new Date());
        String newUserID = UUID.randomUUID().toString();

        if (findUserById(newUserID) != null) {
            log.error("Duplicate key exception in userId");
            throw new DuplicateKeyExceptionUserId();
        }
        newUser.setId(newUserID);
        try {
            this.userDB.createUser(newUser);
        } catch (DuplicateKeyException ex) {
            log.error("DuplicateKeyException on email");
            throw new DuplicateKeyExceptionEmail();
        }
    }

    public void deleteUser(String userId) throws AppException {

        if (userId == null) {
            log.error("User ID can not be null");
            throw new NullArgumentException();
        }
        UserEntity userToDelete = findUserById(userId);
        if (userToDelete != null) {
            this.userDB.deleteUser(userToDelete);
        } else {
            log.error("User id: {} not found", userId);
            throw new UserEntityNotFoundException(userId);
        }
    }

    public void updateUser(String userId, UserEntity updatedValue) throws AppException {

        if (userId == null || updatedValue == null) {
            log.error("Argument can not be null");
            throw new NullArgumentException();
        }
        if (findUserById(userId) != null) {
            try {
                this.userDB.updateUser(userId, updatedValue);
            } catch (DuplicateKeyException ex) {
                log.error("DuplicateKeyException on email");
                throw new DuplicateKeyExceptionEmail();
            }
        } else {
            log.error("User id: {} not found", userId);
            throw new UserEntityNotFoundException(userId);
        }
    }

    private UserEntity findUserById(String userId) {
        return this.userDB.getUserById(userId);
    }
}
