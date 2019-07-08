package com.comarch.tomasz.kosacki.db;

import com.comarch.tomasz.kosacki.dto.UserDto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDB {
    private static List<UserDto> users = new ArrayList<>();

    static {
        users.add(new UserDto(1,"FN1", "LN1", "email1@email.com", new Date()));
        users.add(new UserDto(2,"FN2", "LN2", "email2@email.com", new Date()));
        users.add(new UserDto(3,"FN3", "LN3", "email3@email.com", new Date()));
        users.add(new UserDto(4,"FN4", "LN4", "email4@email.com", new Date()));
    }


    public static List<UserDto> getAllUsers(){
        return  users;
    }

}
