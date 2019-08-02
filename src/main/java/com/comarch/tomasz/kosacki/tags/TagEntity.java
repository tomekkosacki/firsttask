package com.comarch.tomasz.kosacki.tags;

public class TagEntity extends TagDto {

    private String userId;

    public TagEntity(String userId, String tagName, String tagValue) {
        super(tagName, tagValue);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
