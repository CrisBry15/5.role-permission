package com.example.rolepermission.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rolepermission.model.Role;
import com.example.rolepermission.model.UserRole;
import com.example.rolepermission.repository.RoleRepository;
import com.example.rolepermission.repository.UserRoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    public UserRole assignRoleToUser(Long userId, Long roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        UserRole userRole = new UserRole(userId, role);
        return userRoleRepository.save(userRole);
    }

    public List<Role> getRolesByUserId(Long userId) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        return userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
    }
}

