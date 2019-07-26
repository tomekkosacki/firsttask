package com.comarch.tomasz.kosacki.userEntity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.util.Date;

@Entity(noClassnameStored = true)
public class UserEntity {

    @Id
    @Indexed(options = @IndexOptions(unique = true))
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(options = @IndexOptions(unique = true))
    private String email;
    private Date creationDate;

    public UserEntity() {
    }

    public UserEntity(String id, String firstName, String lastName, String email, Date creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean equals(Object obj) {

        UserEntity userEntity = (UserEntity) obj;
        if (this.id.equals((userEntity.getId()))
                && this.firstName.equals(userEntity.getFirstName())
                && this.lastName.equals(userEntity.getLastName())
                && this.email.equals(userEntity.getEmail())
                && this.creationDate.equals(userEntity.getCreationDate())) {
            return true;
        }
        return false;
    }
}
