package com.comarch.tomasz.kosacki.servisExceptions;


import java.io.Serializable;

public class ExceptionMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    private int status;
    private int code;
    private String message;

    public ExceptionMessage() {
    }

    public ExceptionMessage(AppException exception) {
        this.status = exception.getStatus();
        this.code = exception.getCode();
        this.message = exception.getMessage();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
