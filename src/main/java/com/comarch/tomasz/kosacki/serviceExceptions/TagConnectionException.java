package com.comarch.tomasz.kosacki.serviceExceptions;

import javax.ws.rs.core.Response;

public class TagConnectionException extends AppException {
    public TagConnectionException() throws AppException {

        throw new  AppException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 500, "No connection with TagService");
    }
}
