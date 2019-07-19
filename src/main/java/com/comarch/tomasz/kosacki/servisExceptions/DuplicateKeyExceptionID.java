package com.comarch.tomasz.kosacki.servisExceptions;

import javax.ws.rs.core.Response;

public class DuplicateKeyExceptionID extends AppException {

    public DuplicateKeyExceptionID() throws AppException {
        throw new AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500, "ID already exist in database");
    }
}
