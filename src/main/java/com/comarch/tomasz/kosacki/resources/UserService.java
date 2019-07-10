package com.comarch.tomasz.kosacki.resources;


import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.StreamingOutput;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

        System.out.println("Read all users");
        return this.mapper.userEntityListToUserDtoList(this.userDB.getAllUsers());
    }

    @GET
    @Timed
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUserById(@PathParam("id") String userId) {

        System.out.println("Read user by id: " + userId);
        if (this.userDB.getUserById(userId) != null)
            return this.mapper.userEntityToUserDto(this.userDB.getUserById(userId));
        return null;
    }

    @GET
    @Timed
    @Path("/get/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getUserByName(@PathParam("name") String userName) {

        System.out.println("Read user by name: " + userName);
        return this.mapper.userEntityListToUserDtoList(this.userDB.getUserByFirstName(userName));
    }

    @POST
    @Timed
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON})
    public void createUser(UserDto newUser) {

        System.out.println("Creating new user");
        UserEntity userEntity = this.mapper.userDtoToUserEntity(newUser);
        userEntity.setCreationDate(new Date());
        userEntity.setId(UUID.randomUUID().toString());
        this.userDB.createUser(userEntity);

    }
}
