package com.comarch.tomasz.kosacki.dao;

import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import java.util.List;

public interface UserDao {

    List<UserEntity> getAllUsers();

    UserEntity getUserById(String userId);

    List<UserEntity> getUserByFirstName(String userFirstName);

    void createUser(UserEntity newUser);

}
