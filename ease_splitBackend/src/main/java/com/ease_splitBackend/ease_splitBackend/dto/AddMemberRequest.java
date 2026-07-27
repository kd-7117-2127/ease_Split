package com.ease_splitBackend.ease_splitBackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class AddMemberRequest {

    @NotBlank(message = "Member name is required")
    private String name;

    @Email(message = "Invalid email format")
    private String email;

    public AddMemberRequest() {
    }

    public AddMemberRequest(String name, String email) {
        this.name = name;
        this.email = email;
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
