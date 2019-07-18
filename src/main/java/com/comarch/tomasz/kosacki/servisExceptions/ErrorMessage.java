package com.comarch.tomasz.kosacki.servisExceptions;


public class ErrorMessage {

    private int status;
    private int code;
    private String message;

    ErrorMessage() {
    }

    ErrorMessage(AppException exception) {
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
