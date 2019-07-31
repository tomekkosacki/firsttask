package com.comarch.tomasz.kosacki.userDto;

import com.comarch.tomasz.kosacki.tags.TagDto;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserDto implements Serializable {

    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @NotEmpty
    @Email
    private String email;
    private String dateOfBirth;

    private String creationDate;
    private List<TagDto> tagList;


    public UserDto() {
    }

    public UserDto(String firstName, String lastName, String email, LocalDateTime creationDate, LocalDateTime dateOfBirth, List<TagDto> tagList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        setCreationDate(creationDate);
        setDateOfBirth(dateOfBirth);
        this.tagList = tagList;
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

    public void setCreationDate(LocalDateTime creationDate) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.creationDate = creationDate.format(formatter);
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (dateOfBirth != null)
            this.dateOfBirth = dateOfBirth.format(formatter);
        else
            this.dateOfBirth = null;


    }

    public List<TagDto> getTagList() {
        return tagList;
    }

    public void setTagList(List<TagDto> tagList) {
        this.tagList = tagList;
    }
}
