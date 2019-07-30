package com.comarch.tomasz.kosacki.service;

import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.serviceExceptions.*;
import com.comarch.tomasz.kosacki.tags.TagClient;
import com.comarch.tomasz.kosacki.tags.TagDto;
import com.comarch.tomasz.kosacki.userDto.UserDto;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.mongodb.DuplicateKeyException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserService {

    private UserDB userDB;
    private Logger log = LoggerFactory.getLogger(getClass());
    private TagClient tagClient;
    private Mapper mapper;

    public UserService() {
    }

    public UserService(UserDB userDB, TagClient tagClient, Mapper mapper) {

        this.userDB = userDB;
        this.tagClient = tagClient;
        this.mapper = mapper;
    }

    public UserDto getUserById(String userId) throws AppException {

        if (userId == null) {
            log.error("User ID can not null");
            throw new NullArgumentException();
        }
        UserEntity userEntity = findUserById(userId);
        if (userEntity != null) {
            UserDto userDto = this.mapper.userEntityToUserDto(userEntity);
            List<TagDto> tagList;
            try {
                tagList = getUserTagList(userId);
            } catch (FeignException ex) {
                log.error("Error with tag service");
                throw new TagConnectionException();
            }
            userDto.setTagList(tagList);
            return userDto;
        }
        log.error("User id: {} not found", userId);
        throw new UserEntityNotFoundException(userId);
    }

    public List<UserDto> getUserBy(String userId, String userFirstName, String userLastName, String userEmail, int skip, int limit, String sortBy) throws AppException {

        List<UserEntity> userEntityList = this.userDB.getUserBy(userId, userFirstName, userLastName, userEmail, skip, limit, sortBy);

        if (!userEntityList.isEmpty()) {

            List<UserDto> userDtoList = this.mapper.userEntityListToUserDtoList(userEntityList);

            for (int i = 0; i < userEntityList.size(); i++) {
                UserEntity userEntity = userEntityList.get(i);
                List<TagDto> tagList;
                try {
                    tagList = getUserTagList(userEntity.getId());
                } catch (FeignException ex) {
                    log.error("Error with TagService");
                    throw new TagConnectionException();
                }
                UserDto userDto = userDtoList.get(i);
                userDto.setTagList(tagList);
            }
            return userDtoList;
        }
        log.error("User not found");
        throw new UserEntityNotFoundException("");
    }

    public void createUser(UserDto newUser) throws AppException {

        if (newUser == null) {
            log.error("Argument can not be null");
            throw new NullArgumentException();
        }
        UserEntity userEntity = this.mapper.userDtoToUserEntity(newUser);
        userEntity.setCreationDate(new Date());
        String newUserID = UUID.randomUUID().toString();

        if (findUserById(newUserID) != null) {
            log.error("Duplicate key exception in userId");
            throw new DuplicateKeyExceptionUserId();
        }
        userEntity.setId(newUserID);
        try {
            this.userDB.createUser(userEntity);
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

    public void updateUser(String userId, UserDto updatedValue) throws AppException {

        if (userId == null || updatedValue == null) {
            log.error("Argument can not be null");
            throw new NullArgumentException();
        }
        if (findUserById(userId) != null) {
            UserEntity newUserValue = this.mapper.userDtoToUserEntity(updatedValue);
            try {
                this.userDB.updateUser(userId, newUserValue);
            } catch (DuplicateKeyException ex) {
                log.error("DuplicateKeyException on email");
                throw new DuplicateKeyExceptionEmail();
            }
        } else {
            log.error("User id: {} not found", userId);
            throw new UserEntityNotFoundException(userId);
        }
    }

    private List<TagDto> getUserTagList(String userId) {
        if (userId == null) {
            log.error("User ID can not null");
            throw new NullArgumentException();
        }
        UserEntity userEntity = findUserById(userId);
        if (userEntity != null) {
            return tagClient.getTagByUserId(userId);
        }
        log.error("User id: {} not found", userId);
        throw new UserEntityNotFoundException(userId);
    }

    private UserEntity findUserById(String userId) {
        return this.userDB.getUserById(userId);
    }
}
