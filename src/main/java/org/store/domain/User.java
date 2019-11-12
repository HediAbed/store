package org.store.domain;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.*;

import org.hibernate.validator.constraints.Email;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements UserDetails {
    static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    @ApiModelProperty(notes = "The database generated user, token and session mapping ID.")
    private Long id;

    @Column(name = "username", nullable = false,unique=true)
    @ApiModelProperty(notes = "username")
    private String username;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    @ApiModelProperty(notes = "User password")
    private String password;

    @Column(name = "firstName")
    @ApiModelProperty(notes = "User firstName")
    private String firstName;

    @Column(name = "lastName")
    @ApiModelProperty(notes = "User lastName")
    private String lastName;

    @Column(name = "phone")
    @ApiModelProperty(notes = "User phone")
    private String phone;

    @Column(name = "email", nullable = false,unique=true)
    @Email
    @ApiModelProperty(notes = "User email")
    private String email;

    @Column(name = "enabled", nullable = false)
    @ApiModelProperty(notes = "Indicates whether the user is enabled or disabled. A disabled user cannot be authenticated.")
    private boolean enabled;

    @ApiModelProperty(notes = "create date.")
    @Column(name = "created_date",updatable = false)
    private Date createdDate;

    @ApiModelProperty(notes = "update date.")
    @Column(name = "update_date")
    private Date updateDate;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<UserPayment> userPaymentList;

    @OneToMany(cascade=CascadeType.ALL, mappedBy = "user")
    private List<UserShipping> userShippingList;

    @OneToOne(cascade=CascadeType.ALL, mappedBy = "user")
    private ShoppingCart shoppingCart;

    @OneToMany(mappedBy="user")
    private List<Order> orderList;

    private void audit() {
        setCreatedDate((new Date()));
        setUpdateDate((new Date()));
    }

    @PrePersist
    protected void onCreate() {
        audit();
    }

    @PreUpdate
    protected void onUpdate() {
        setUpdateDate((new Date()));
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach(ur -> authorities.add(new SimpleGrantedAuthority(ur.getRole().getName().getType())));
        return authorities;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Date getCreatedDate() { return createdDate; }

    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public Date getUpdateDate() { return updateDate; }

    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

    public Set<UserRole> getUserRoles() { return userRoles; }

    public void setUserRoles(Set<UserRole> userRoles) { this.userRoles = userRoles; }

    public List<UserPayment> getUserPaymentList() { return userPaymentList; }

    public void setUserPaymentList(List<UserPayment> userPaymentList) { this.userPaymentList = userPaymentList; }

    public List<UserShipping> getUserShippingList() { return userShippingList; }

    public void setUserShippingList(List<UserShipping> userShippingList) { this.userShippingList = userShippingList; }

    public ShoppingCart getShoppingCart() { return shoppingCart; }

    public void setShoppingCart(ShoppingCart shoppingCart) { this.shoppingCart = shoppingCart; }

    public List<Order> getOrderList() { return orderList; }

    public void setOrderList(List<Order> orderList) { this.orderList = orderList; }

    public User(){};
    public User(String username,String email, String password, String firstName, String lastName,boolean enabled) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", enabled=" + enabled +
                ", createdDate=" + createdDate +
                ", updateDate=" + updateDate +
                ", userRoles=" + userRoles +
                ", userPaymentList=" + userPaymentList +
                ", userShippingList=" + userShippingList +
                ", shoppingCart=" + shoppingCart +
                ", orderList=" + orderList +
                '}';
    }
}

