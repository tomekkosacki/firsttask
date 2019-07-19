package com.comarch.tomasz.kosacki.servisExceptions;

import javax.ws.rs.core.Response;

public class DuplicateKeyExceptionEmail extends AppException {

    public DuplicateKeyExceptionEmail() throws AppException {
        throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Email already exist in database");
    }
}
