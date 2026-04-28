package com.example.quiz.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Автоматически создаёт базу данных Quiz_db при первом запуске.
 * Если база уже существует — ничего не происходит.
 */
@Configuration
public class DatabaseInitializer {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Извлекает имя БД из JDBC URL вида jdbc:postgresql://host:port/dbname
     */
    private String extractDbName(String url) {
        // jdbc:postgresql://localhost:5432/Quiz_db  →  Quiz_db
        String withoutParams = url.split("\\?")[0];
        return withoutParams.substring(withoutParams.lastIndexOf('/') + 1);
    }

    /**
     * Строит URL для подключения к системной БД postgres
     */
    private String buildAdminUrl(String url) {
        // Заменяем /Quiz_db на /postgres
        String withoutParams = url.split("\\?")[0];
        String base = withoutParams.substring(0, withoutParams.lastIndexOf('/'));
        return base + "/postgres";
    }

    @Bean(name = "dbCreator")
    public Boolean ensureDatabaseExists() {
        String dbName = extractDbName(datasourceUrl);
        String adminUrl = buildAdminUrl(datasourceUrl);

        try (Connection conn = DriverManager.getConnection(adminUrl, username, password);
             Statement stmt = conn.createStatement()) {

            // Проверяем, существует ли БД
            ResultSet rs = stmt.executeQuery(
                "SELECT 1 FROM pg_database WHERE datname = '" + dbName + "'"
            );

            if (!rs.next()) {
                // БД не существует — создаём
                stmt.execute("CREATE DATABASE \"" + dbName + "\"");
                System.out.println(">>> Database '" + dbName + "' created successfully.");
            } else {
                System.out.println(">>> Database '" + dbName + "' already exists, skipping creation.");
            }

        } catch (Exception e) {
            System.err.println(">>> WARNING: Could not auto-create database: " + e.getMessage());
            System.err.println(">>> Please ensure PostgreSQL is running and user '" + username + "' has CREATEDB privilege.");
        }

        return Boolean.TRUE;
    }
}
