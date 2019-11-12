package org.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.Role;
import org.store.domain.User;
import org.store.domain.UserRole;

import java.util.List;

public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {
    List<UserRole> findByRole(Role role) ;
}
