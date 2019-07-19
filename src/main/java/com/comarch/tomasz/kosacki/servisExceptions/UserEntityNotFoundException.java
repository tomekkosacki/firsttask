package com.comarch.tomasz.kosacki.servisExceptions;

import javax.ws.rs.core.Response;

public class UserEntityNotFoundException extends AppException {

    public UserEntityNotFoundException(String userId) throws AppException {
        throw new AppException(Response.Status.NOT_FOUND.getStatusCode(), 404, "User " + userId + " not found in database");

    }
}
