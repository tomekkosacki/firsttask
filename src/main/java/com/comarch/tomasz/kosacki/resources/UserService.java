package com.comarch.tomasz.kosacki.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import com.comarch.tomasz.kosacki.userEntity.UserEntity;
import com.mongodb.DuplicateKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") String userId) {

        log.info("Read user by id: {}", userId);
        if (this.userDB.getUserById(userId) != null) {
            return Response.ok(this.mapper.userEntityToUserDto(this.userDB.getUserById(userId))).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserBy(@QueryParam("id") String userId,
                              @QueryParam("firstName") String userFirstName,
                              @QueryParam("lastName") String userLastName,
                              @QueryParam("email") String userEmail,
                              @QueryParam("offset") int offset,
                              @QueryParam("limit") int limit,
                              @QueryParam("sortBy") String sortBy) {

        log.info("Read user by");
        if (!(this.userDB.getUserBy(userId, userFirstName, userLastName, userEmail, offset, limit, sortBy)).isEmpty()) {
            return Response.ok(this.mapper.userEntityListToUserDtoList(this.userDB.getUserBy(userId, userFirstName, userLastName, userEmail, offset, limit, sortBy))).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @POST
    @Timed
    @Path("/add")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(@NotNull @Valid UserDto newUser) {

        log.info("Creating new user");
        try {
            UserEntity userEntity = this.mapper.userDtoToUserEntity(newUser);
            userEntity.setCreationDate(new Date());
            userEntity.setId(UUID.randomUUID().toString());
            this.userDB.createUser(userEntity);
            return Response.ok().build();
        } catch (DuplicateKeyException ex) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


    }

    @DELETE
    @Timed
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteUser(@NotNull @PathParam("id") String userId) {

        log.info("Deleting user with id: {}", userId);
        if (this.userDB.getUserById(userId) != null) {
            this.userDB.deleteUser(this.userDB.getUserById(userId));
            return Response.ok().build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Timed
    @Path("/update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@NotNull @PathParam("id") String userId, @Valid @NotNull UserDto updatedValue) {

        log.info("Updating user with id: {}", userId);
        if (this.userDB.getUserById(userId) != null) {
            try {
                this.userDB.updateUser(userId, this.mapper.userDtoToUserEntity(updatedValue));
                return Response.ok().build();
            } catch (DuplicateKeyException ex) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

}
