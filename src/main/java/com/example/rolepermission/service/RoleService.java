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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol no puede estar vacío");
        }
        return roleRepository.findByName(name);
    }

    public Role createRole(Role role) {
        if (role == null) {
            throw new IllegalArgumentException("El rol no puede ser nulo");
        }
        if (role.getName() == null || role.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del rol es obligatorio");
        }

        // Verificar si ya existe un rol con ese nombre
        Role existingRole = roleRepository.findByName(role.getName());
        if (existingRole != null) {
            throw new IllegalArgumentException("Ya existe un rol con el nombre: " + role.getName());
        }

        return roleRepository.save(role);
    }

    public UserRole assignRoleToUser(Long userId, Long roleId) {
        if (userId == null || roleId == null) {
            throw new IllegalArgumentException("El ID del usuario y del rol son obligatorios");
        }

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con ID: " + roleId));

        // Verificar si ya existe la asignación
        List<UserRole> existingAssignments = userRoleRepository.findByUserId(userId);
        boolean alreadyAssigned = existingAssignments.stream()
                .anyMatch(ur -> ur.getRole().getId().equals(roleId));

        if (alreadyAssigned) {
            throw new IllegalArgumentException("El usuario ya tiene asignado este rol");
        }

        UserRole userRole = new UserRole(userId, role);
        return userRoleRepository.save(userRole);
    }

    public List<Role> getRolesByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El ID del usuario no puede ser nulo");
        }
        List<UserRole> userRoles = userRoleRepository.findByUserId(userId);
        return userRoles.stream().map(UserRole::getRole).collect(Collectors.toList());
    }
}
