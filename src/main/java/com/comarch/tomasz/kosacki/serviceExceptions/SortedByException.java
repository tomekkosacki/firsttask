package com.comarch.tomasz.kosacki.serviceExceptions;

import javax.ws.rs.core.Response;

public class SortedByException extends AppException {
    public SortedByException() throws AppException {
        throw new AppException(Response.Status.BAD_REQUEST.getStatusCode(), 400, "Sorting argument not valid");
    }
}
