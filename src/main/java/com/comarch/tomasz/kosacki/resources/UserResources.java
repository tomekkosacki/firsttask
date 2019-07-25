package com.comarch.tomasz.kosacki.resources;

import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.service.UserService;
import com.comarch.tomasz.kosacki.dto.UserDto;
import com.comarch.tomasz.kosacki.mapper.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserResources {

    private Logger log = LoggerFactory.getLogger(getClass());
    private Mapper mapper;
    private UserService userService;

    public UserResources(Mapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@NotNull @PathParam("id") String userId) {

        log.info("Get user by id: {}", userId);
        return Response.ok(this.mapper.userEntityToUserDto(this.userService.getUserById(userId))).build();
    }

    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserBy(@QueryParam("id") String userId,
                              @QueryParam("firstName") String userFirstName,
                              @QueryParam("lastName") String userLastName,
                              @QueryParam("email") String userEmail,
                              @QueryParam("skip") @Min(0) @DefaultValue("0") int skip,
                              @QueryParam("limit") @Min(0) @DefaultValue("0") int limit,
                              @QueryParam("sortBy") String sortBy) {

        log.info("Read user by");
        return Response.ok(this.mapper.userEntityListToUserDtoList(this.userService.getUserBy(userId, userFirstName, userLastName, userEmail, skip, limit, sortBy))).build();
    }

    @POST
    @Timed
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createUser(@Valid @NotNull UserDto newUser) {

        log.info("Creating new user");
        this.userService.createUser(this.mapper.userDtoToUserEntity(newUser));
        return Response.ok().build();
    }

    @DELETE
    @Timed
    @Path("/delete/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response deleteUser(@NotNull @PathParam("id") String userId) {

        log.info("Deleting user with id: {}", userId);
        this.userService.deleteUser(userId);
        return Response.ok().build();
    }

    @PUT
    @Timed
    @Path("/update/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateUser(@NotNull @PathParam("id") String userId, @Valid @NotNull UserDto updatedValue) {

        log.info("Updating user with id: {}", userId);
        this.userService.updateUser(userId, this.mapper.userDtoToUserEntity(updatedValue));
        return Response.ok().build();
    }
}
