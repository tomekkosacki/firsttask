package com.comarch.tomasz.kosacki.db;

import com.comarch.tomasz.kosacki.dao.UserDao;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDB implements UserDao {

    private List<UserEntity> users = new ArrayList<>();

    public UserDB() {
        this.users.add(new UserEntity(1, "FN1", "LN1", "email1@email.com", new Date()));
        this.users.add(new UserEntity(2, "FN2", "LN2", "email2@email.com", new Date()));
        this.users.add(new UserEntity(3, "FN3", "LN3", "email3@email.com", new Date()));
        this.users.add(new UserEntity(4, "FN4", "LN4", "email4@email.com", new Date()));
    }


    @Override
    public List<UserEntity> getAllUsers() {
        return this.users;
    }

    @Override
    public UserEntity getUserById(int id) {
        for (UserEntity userEntity : users) {
            if (userEntity.getId() == id)
                return userEntity;
        }
        return null;
    }

    @Override
    public UserEntity getUserByFirstName(String userFirstName) {
        for (UserEntity userEntity : users) {
            if (userEntity.getFirstName().equals(userFirstName))
                return userEntity;
        }
        return null;
    }

}
