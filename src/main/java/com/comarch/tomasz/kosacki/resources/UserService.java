package com.comarch.tomasz.kosacki.resources;


import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserService {

    private UserDB userDB = new UserDB();
    private Mapper mapper = new Mapper();
    public UserService() {
    }

    @GET
    @Timed
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getAllUsers() {

        List<UserDto> userDtoList = new ArrayList<>();
        List<UserEntity> entityUserList = this.userDB.getAllUsers();
        for (UserEntity userEntity : entityUserList) {
            userDtoList.add(this.mapper.userEntityToUserDto(userEntity));
        }

        System.out.println("Read all users");
        return userDtoList;
    }


}
