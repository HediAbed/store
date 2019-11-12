package org.store.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.store.domain.Role;
import org.store.domain.enums.RoleType;
import org.store.repository.RoleRepository;
import org.store.service.RoleService;

import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Override
    public Optional<Role> findByName(RoleType roleType) {
        return Optional.ofNullable(roleRepository.findByName(roleType));
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }
}
