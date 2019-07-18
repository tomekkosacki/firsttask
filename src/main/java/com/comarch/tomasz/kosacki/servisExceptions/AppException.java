package com.comarch.tomasz.kosacki.servisExceptions;

public class AppException extends Exception {

    private Integer status;
    private int code;
    private String message;

    public AppException(int status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}