package com.comarch.tomasz.kosacki;

import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.servisExceptions.AppException;
import com.comarch.tomasz.kosacki.servisExceptions.UserEntityNotFoundException;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserServis {

    private Logger log = LoggerFactory.getLogger(getClass());

    private UserDB userDB;

    UserEntity getUserById(String userId) throws AppException {

        log.info("Read user by id: {}", userId);
        UserEntity userEntity = this.userDB.getUserById(userId;
        if ( userEntity != null) {
            return userEntity;
        }
        log.error("User not found");
        throw new UserEntityNotFoundException(userId);
    }
}
