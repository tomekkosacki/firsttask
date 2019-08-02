package com.comarch.tomasz.kosacki.configurationClass;

import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class ProjectConfiguration extends Configuration {

    @NotEmpty
    private String tagUrl;
    @NotEmpty
    private String dbName;
    @NotEmpty
    private String userName;
    @NotEmpty
    private String userPassword;

    private int limitPagging;
    private int dateOfBirthJobIntervalInSeconds;
    private int zodiacJobIntervalInSeconds;

    public String getTagUrl() {
        return tagUrl;
    }

    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public int getLimitPagging() {
        return limitPagging;
    }

    public void setLimitPagging(int limitPagging) {
        this.limitPagging = limitPagging;
    }

    public int getDateOfBirthJobIntervalInSeconds() {
        return dateOfBirthJobIntervalInSeconds;
    }

    public void setDateOfBirthJobIntervalInSeconds(int dateOfBirthJobIntervalInSeconds) {
        this.dateOfBirthJobIntervalInSeconds = dateOfBirthJobIntervalInSeconds;
    }

    public int getZodiacJobIntervalInSeconds() {
        return zodiacJobIntervalInSeconds;
    }

    public void setZodiacJobIntervalInSeconds(int zodiacJobIntervalInSeconds) {
        this.zodiacJobIntervalInSeconds = zodiacJobIntervalInSeconds;
    }
}
