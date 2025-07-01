package main.java.com.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class RolePermissionApplication {

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5433/permission_database",
                "postgres",
                "Brytib+1906")) {

            System.out.println("✅ Conexión a PostgreSQL exitosa");

        } catch (Exception e) {
            System.out.println("❌ Error de conexión: " + e.getMessage());
        }

        // SpringApplication.run(RolePermissionApplication.class, args); // se habilitará en la integración final
    }
}

