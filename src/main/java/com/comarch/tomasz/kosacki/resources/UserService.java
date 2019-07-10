package com.comarch.tomasz.kosacki.resources;


import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserService {

    private UserDB userDB;
    private Mapper mapper;

    public UserService() {
        this.userDB = new UserDB();
        this.mapper = new Mapper();
    }

    @GET
    @Timed
    @Path("/all")
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

    @GET
    @Timed
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUserById(@PathParam("id") int id) {

        System.out.println("Read user by id: " + id);
        if(this.userDB.getUserById(id) != null)
            return this.mapper.userEntityToUserDto(this.userDB.getUserById(id));
        return null;
    }

    @GET
    @Timed
    @Path("/get/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUserByName(@PathParam("name") String userName) {

        System.out.println("Read user by name: " + userName);
        if(this.userDB.getUserByFirstName(userName) != null)
            return this.mapper.userEntityToUserDto(this.userDB.getUserByFirstName(userName));
        return null;
    }
}
