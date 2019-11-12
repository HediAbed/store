package org.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class CreateUserRequest {
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "User email")
    private String email;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
