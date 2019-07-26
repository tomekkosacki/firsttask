package com.comarch.tomasz.kosacki.serviceExceptions;

import javax.ws.rs.core.Response;

public class DuplicateKeyExceptionUserId extends AppException {
    public DuplicateKeyExceptionUserId() throws AppException {

        throw new  AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500, "UserId already exist in database");
    }
}
