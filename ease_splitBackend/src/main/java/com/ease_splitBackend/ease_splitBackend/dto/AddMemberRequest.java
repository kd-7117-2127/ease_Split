package com.ease_splitBackend.ease_splitBackend.dto;
public class AddMemberRequest {

    private String name;
    private String email;

    public AddMemberRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
