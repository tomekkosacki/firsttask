package com.comarch.tomasz.kosacki.configurationClass;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty
    public String getTagUrl() {
        return tagUrl;
    }

    @JsonProperty
    public void setTagUrl(String tagUrl) {
        this.tagUrl = tagUrl;
    }

    @JsonProperty
    public String getDbName() {
        return dbName;
    }

    @JsonProperty
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    @JsonProperty
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonProperty
    public String getUserPassword() {
        return userPassword;
    }

    @JsonProperty
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
