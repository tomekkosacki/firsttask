package com.comarch.tomasz.kosacki.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserDto implements Serializable {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.creationDate = formatter.format(creationDate);
    }
}
