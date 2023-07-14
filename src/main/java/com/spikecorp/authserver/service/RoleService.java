package com.spikecorp.authserver.service;

import com.spikecorp.authserver.entity.Role;
import com.spikecorp.authserver.exception.RoleNotExistsException;
import com.spikecorp.authserver.repository.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotExistsException("no such role found"));
    }
}
