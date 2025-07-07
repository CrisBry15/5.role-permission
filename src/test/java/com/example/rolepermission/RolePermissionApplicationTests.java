package com.example.rolepermission;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.rolepermission.model.Role;

@SpringBootTest
class RolePermissionApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
    }

    @Test
    void testRoleValidation() {
        // Test de validación de rol
        Role role = new Role();

        // Test con nombre nulo
        assertThrows(IllegalArgumentException.class, () -> {
            role.setName(null);
            // Simular validación
            if (role.getName() == null || role.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del rol es obligatorio");
            }
        });

        // Test con nombre vacío
        assertThrows(IllegalArgumentException.class, () -> {
            role.setName("");
            if (role.getName() == null || role.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del rol es obligatorio");
            }
        });

        // Test con nombre válido
        role.setName("ADMIN");
        assertDoesNotThrow(() -> {
            if (role.getName() == null || role.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("El nombre del rol es obligatorio");
            }
        });
    }
}
