package com.comarch.tomasz.kosacki.servisExceptions;

import javax.ws.rs.core.Response;

public class NotNullArgumentException extends AppException {

    public NotNullArgumentException() throws AppException {
        throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Argument can not be empty");
    }
}
