package com.example.rolepermission.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.rolepermission.model.Role;
import com.example.rolepermission.model.UserRole;
import com.example.rolepermission.service.RoleService;
import com.example.rolepermission.util.ResponseHandler;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/create")
    public ResponseEntity<Object> createRole(@RequestBody Role role) {
        try {
            Role saved = roleService.createRole(role);
            return ResponseHandler.generateResponse("Rol creado exitosamente", 201, saved);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse("Error de validación: " + e.getMessage(), 400, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error interno del servidor", 500, null);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRoles() {
        try {
            List<Role> roles = roleService.getAllRoles();
            return ResponseHandler.generateResponse("Lista de roles obtenida", 200, roles);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error al obtener roles", 500, null);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Object> getRoleByName(@PathVariable String name) {
        try {
            Role role = roleService.getRoleByName(name);
            if (role != null) {
                return ResponseHandler.generateResponse("Rol encontrado", 200, role);
            } else {
                return ResponseHandler.generateResponse("Rol no encontrado", 404, null);
            }
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse("Error de validación: " + e.getMessage(), 400, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error interno del servidor", 500, null);
        }
    }

    @PostMapping("/assign")
    public ResponseEntity<Object> assignRoleToUser(@RequestBody Map<String, Object> request) {
        try {
            if (request.get("userId") == null || request.get("roleId") == null) {
                return ResponseHandler.generateResponse("userId y roleId son obligatorios", 400, null);
            }

            Long userId = Long.valueOf(request.get("userId").toString());
            Long roleId = Long.valueOf(request.get("roleId").toString());

            UserRole userRole = roleService.assignRoleToUser(userId, roleId);
            return ResponseHandler.generateResponse("Rol asignado exitosamente", 201, userRole);
        } catch (NumberFormatException e) {
            return ResponseHandler.generateResponse("Formato inválido para userId o roleId", 400, null);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse("Error de validación: " + e.getMessage(), 400, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error interno del servidor", 500, null);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getRolesByUser(@PathVariable Long userId) {
        try {
            List<Role> roles = roleService.getRolesByUserId(userId);
            return ResponseHandler.generateResponse("Roles del usuario obtenidos", 200, roles);
        } catch (IllegalArgumentException e) {
            return ResponseHandler.generateResponse("Error de validación: " + e.getMessage(), 400, null);
        } catch (Exception e) {
            return ResponseHandler.generateResponse("Error interno del servidor", 500, null);
        }
    }
}
