package com.comarch.tomasz.kosacki.dao;

import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import java.util.List;

public interface UserDao {

    List<UserEntity> getAllUsers();

    UserEntity getUserById(int id);

    UserEntity getUserByFirstName(String userFirstName);

}
