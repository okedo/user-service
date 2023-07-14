package com.spikecorp.authserver;

import com.spikecorp.authserver.entity.Role;
import com.spikecorp.authserver.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializationBean implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public DatabaseInitializationBean(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("USER").isEmpty()) {
            Role userRole = new Role();
            userRole.setName("USER");
            roleRepository.save(userRole);
        }

        if (roleRepository.findByName("ADMIN").isEmpty()) {
            Role adminRole = new Role();
            adminRole.setName("ADMIN");
            roleRepository.save(adminRole);
        }

        if (roleRepository.findByName("SUPERADMIN").isEmpty()) {
            Role superadminRole = new Role();
            superadminRole.setName("SUPERADMIN");
            roleRepository.save(superadminRole);
        }
    }
}