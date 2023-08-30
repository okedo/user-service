package com.spikecorp.authserver.controller;

import com.spikecorp.authserver.entity.Role;
import com.spikecorp.authserver.service.RoleService;
import com.spikecorp.authserver.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/roles")
@AllArgsConstructor
public class RoleController {
    private final UserService userService;

    @GetMapping("/user/{userId}")
    public Set<Role> getUserRoles(@PathVariable Long userId) {
        return userService.getUserRoles(userId);
    }
}