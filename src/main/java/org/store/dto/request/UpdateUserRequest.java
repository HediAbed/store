package org.store.dto.request;

import io.swagger.annotations.ApiModelProperty;

public class UpdateUserRequest {
    @ApiModelProperty(notes = "The database generated user, token and session mapping ID.")
    private Long id;
    @ApiModelProperty(notes = "username")
    private String username;
    @ApiModelProperty(notes = "User firstName")
    private String firstName;
    @ApiModelProperty(notes = "User lastName")
    private String lastName;
    @ApiModelProperty(notes = "User phone")
    private String phone;
    @ApiModelProperty(notes = "User email")
    private String email;
    @ApiModelProperty(notes = "New User password")
    private String newPassword;
    @ApiModelProperty(notes = "current User password")
    private String currentPassword;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getNewPassword() { return newPassword; }

    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getCurrentPassword() { return currentPassword; }

    public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
}
