package org.store.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.store.domain.Role;
import org.store.domain.User;
import org.store.domain.UserRole;
import org.store.domain.enums.RoleType;
import org.store.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements ApplicationRunner {
    static final String ADMIN = "admin";

    private UserService userService;
    @Autowired
    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    public void run(ApplicationArguments args) {
        if(!userService.findByUsername(ADMIN).isPresent()){
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            User adminsitrator = new User(ADMIN, "admin@admin.com",ADMIN,ADMIN,ADMIN,true);
            Set<UserRole> userRoles = new HashSet<>();
            userRoles.add(new UserRole(adminsitrator, new Role(RoleType.ROLE_ADMIN)));
           // userRoles.add(new UserRole(adminsitrator, new Role(RoleType.ROLE_CLIENT)));
            userService.createUser(adminsitrator,userRoles);
        }
    }
}