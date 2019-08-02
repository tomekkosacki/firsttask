package com.comarch.tomasz.kosacki.dao;

import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import java.util.List;

public interface UserDao {

    UserEntity getUserById(String userId);

    List<UserEntity> getUserBy(String userId, String userFirstName, String userLastName, String userEmail, int offset, int limit, String sortBy);

    List<UserEntity> getUserByFieldWhenNull(int limit, String fieldName);

    void createUser(UserEntity newUser);

    void deleteUser(UserEntity user);

    void updateUser(String userIdToUpdate, UserEntity updatedValue);

    void updateUserField(String userId, Object value, String fieldName);
}
