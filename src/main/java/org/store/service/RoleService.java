package org.store.service;

import org.store.domain.Role;
import org.store.domain.enums.RoleType;

import java.util.Optional;

public interface RoleService {
    Optional<Role> findByName(RoleType roleType);
    Role save(Role role);
}
