package org.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class ForgetPasswordRequest {
    @ApiModelProperty(notes = "User email for password reset")
    private String email ;
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
