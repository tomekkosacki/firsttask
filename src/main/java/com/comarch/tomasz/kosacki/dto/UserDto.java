package com.comarch.tomasz.kosacki.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class UserDto {

    private String firstName;
    private String lastName;
    private String email;
    private String creationDate;


    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String email, Date creationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setCreationDate(creationDate);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCreationDate(Date creationDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.creationDate = formatter.format(creationDate); }
}
