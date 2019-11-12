package org.store.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.store.domain.Role;
import org.store.domain.enums.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(RoleType roleType);
}
