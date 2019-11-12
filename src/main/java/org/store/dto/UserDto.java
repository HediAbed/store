package org.store.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class UserDto {
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

    @ApiModelProperty(notes = "Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.")
    private boolean enabled;

    @ApiModelProperty(notes = "create date.")
    private Date createdDate;

    @ApiModelProperty(notes = "update date.")
    private Date updateDate;

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

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Date getCreatedDate() { return createdDate; }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public Date getUpdateDate() { return updateDate; }

    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }
}
