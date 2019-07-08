package com.comarch.tomasz.kosacki.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;

public class UserDto {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String creationDate;
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public UserDto() {
    }

    public UserDto(int id, String firstName, String lastName, String email, Date creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = formatter.format(creationDate);
    }

    @JsonProperty
    public int getId(){ return id; }

    @JsonProperty
    public String getFirstName() { return firstName; }

    @JsonProperty
    public String getLastName() { return lastName; }

    @JsonProperty
    public String getEmail() { return email; }

    @JsonProperty
    public String getCreationDate() { return creationDate; }
}
