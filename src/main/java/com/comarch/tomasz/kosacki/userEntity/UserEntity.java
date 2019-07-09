package com.comarch.tomasz.kosacki.userEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

public class UserEntity {

    @NotNull
    private int id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    @NotNull
    private Date creationDate;

    public UserEntity() {
    }

    public UserEntity(int id, String firstName, String lastName, String email, Date creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setId(int id) {
        this.id = id;
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
        this.creationDate = creationDate;
    }
}
