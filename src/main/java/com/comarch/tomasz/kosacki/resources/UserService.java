package com.comarch.tomasz.kosacki.resources;


import com.codahale.metrics.annotation.Timed;
import com.comarch.tomasz.kosacki.db.UserDB;
import com.comarch.tomasz.kosacki.dto.UserDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/users")
public class UserService {

    public UserService() {
    }

    @GET
    @Timed
    @Path("/get/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<UserDto>getAllUsers() { return UserDB.getAllUsers(); }

}
