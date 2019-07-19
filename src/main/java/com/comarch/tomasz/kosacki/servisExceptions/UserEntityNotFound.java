package com.comarch.tomasz.kosacki.servisExceptions;

import javax.ws.rs.core.Response;

public class UserEntityNotFound extends AppException {

    public UserEntityNotFound(String userId) throws AppException {
        throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 404, "User " + userId + " not found in database");

    }
}
