package com.comarch.tomasz.kosacki.userEntity;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.IndexOptions;
import org.mongodb.morphia.annotations.Indexed;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity(noClassnameStored = true)
public class UserEntity {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    @Indexed(options = @IndexOptions(unique = true))
    private String email;
    private LocalDateTime creationDate;
    private LocalDateTime dateOfBirth;

    public UserEntity() {
    }

    public UserEntity(String id, String firstName, String lastName, String email, LocalDateTime creationDate, String dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.creationDate = creationDate;
        setDateOfBirth(dateOfBirth);
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (dateOfBirth != null)
            this.dateOfBirth = LocalDateTime.parse(dateOfBirth, formatter);
        else
            this.dateOfBirth = null;

    }

    public boolean equals(Object obj) {

        UserEntity userEntity = (UserEntity) obj;
        if (this.id.equals(userEntity.getId())
                && this.firstName.equals(userEntity.getFirstName())
                && this.lastName.equals(userEntity.getLastName())
                && this.email.equals(userEntity.getEmail())
                && this.creationDate.equals(userEntity.getCreationDate())
                && this.dateOfBirth.equals(userEntity.getDateOfBirth())) {
            return true;
        }
        return false;
    }
}
