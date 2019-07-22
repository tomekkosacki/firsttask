package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.servisExceptions.AppException;
import com.comarch.tomasz.kosacki.servisExceptions.DuplicateKeyExceptionEmail;
import com.comarch.tomasz.kosacki.servisExceptions.UserEntityNotFoundException;
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


    public UserService(UserDB userDB) {
        this.userDB = userDB;
    }

    public UserEntity getUserById(String userId) throws AppException {

        UserEntity userEntity = this.userDB.getUserById(userId);
        if (userEntity != null) {
            return userEntity;
        }
        log.error("User not found");
        throw new UserEntityNotFoundException(userId);
    }

    public List<UserEntity> getUserBy(String userId, String userFirstName, String userLastName, String userEmail, int offset, int limit, String sortBy) {

        List<UserEntity> userEntityList = this.userDB.getUserBy(userId, userFirstName, userLastName, userEmail, offset, limit, sortBy);
        if (!userEntityList.isEmpty()) {
            return userEntityList;
        }
        log.error("User not found");
        throw new UserEntityNotFoundException("");
    }

    public void createUser(UserEntity newUser) throws AppException {

        newUser.setCreationDate(new Date());
        String newUserID;
        do {
            newUserID = UUID.randomUUID().toString();
        } while (this.userDB.getUserById(newUserID) != null);
        newUser.setId(newUserID);
        try {
            this.userDB.createUser(newUser);
        } catch (DuplicateKeyException ex) {
            log.error("DuplicateKeyException on email");
            throw new DuplicateKeyExceptionEmail();
        }
    }

    public void deleteUser(String userId) throws AppException {

        UserEntity userToDelete = this.userDB.getUserById(userId);
        if (userToDelete != null) {
            this.userDB.deleteUser(userToDelete);
        } else {
            log.error("User not found");
            throw new UserEntityNotFoundException(userId);
        }
    }

    public void updateUser(String userId, UserEntity updatedValue) throws AppException {

        if (this.userDB.getUserById(userId) != null) {
            try {
                this.userDB.updateUser(userId, updatedValue);
            } catch (DuplicateKeyException ex) {
                log.error("DuplicateKeyException on email");
                throw new DuplicateKeyExceptionEmail();
            }
        } else {
            log.error("User not found");
            throw new UserEntityNotFoundException(userId);
        }
    }
}
