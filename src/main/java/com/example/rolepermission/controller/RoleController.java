package com.example.rolepermission.controller;

import com.example.rolepermission.model.Role;
import com.example.rolepermission.service.RoleService;
import com.example.rolepermission.util.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<Object> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseHandler.generateResponse("Roles obtenidos correctamente", 200, roles);
    }

    @GetMapping("/{name}")
    public ResponseEntity<Object> getRoleByName(@PathVariable String name) {
        Role role = roleService.getRoleByName(name);
        if (role == null) {
            return ResponseHandler.generateResponse("Rol no encontrado", 404, null);
        }
        return ResponseHandler.generateResponse("Rol encontrado", 200, role);
    }

    @PostMapping
    public ResponseEntity<Object> createRole(@RequestBody Role role) {
        Role newRole = roleService.createRole(role);
        return ResponseHandler.generateResponse("Rol creado exitosamente", 201, newRole);
    }
}
