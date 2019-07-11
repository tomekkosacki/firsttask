package com.comarch.tomasz.kosacki.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/users")
public class UserService {

    private Logger log = LoggerFactory.getLogger(getClass());
    private UserDB userDB;
    private Mapper mapper;

    public UserService(UserDB userDB, Mapper mapper) {
        this.userDB = userDB;
        this.mapper = mapper;
    }

    @GET
    @Timed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getAllUsers() {

        log.info("Read all users");
        return this.mapper.userEntityListToUserDtoList(this.userDB.getAllUsers());
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public UserDto getUserById(@PathParam("id") String userId) {

        log.info("Read user by id: {}", userId);
        if (this.userDB.getUserById(userId) != null) {
            return this.mapper.userEntityToUserDto(this.userDB.getUserById(userId));
        }
        return null;
    }

    @GET
    @Timed
    @Path("/name/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto> getUserByName(@PathParam("name") String userName) {

        log.info("Read user by name: {}", userName);
        return this.mapper.userEntityListToUserDtoList(this.userDB.getUserByFirstName(userName));
    }

    @POST
    @Timed
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON})
    public void createUser(UserDto newUser) {

        log.info("Creating new user");
        UserEntity userEntity = this.mapper.userDtoToUserEntity(newUser);
        userEntity.setCreationDate(new Date());
        userEntity.setId(UUID.randomUUID().toString());
        this.userDB.createUser(userEntity);

    }

    @DELETE
    @Timed
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public void deleteUser(@PathParam("id") String userId) {

        log.info("Deleting user with id: {}", userId);
        this.userDB.deleteUser(this.userDB.getUserById(userId));
    }

//    @PUT
//    @Timed
//    @Path("/update/{id}")
    
}
