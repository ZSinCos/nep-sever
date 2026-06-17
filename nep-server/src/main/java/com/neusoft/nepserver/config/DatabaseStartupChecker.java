package com.neusoft.nepserver.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.Connection;

@Configuration
public class DatabaseStartupChecker implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DatabaseStartupChecker.class);

    private final DataSource dataSource;

    public DatabaseStartupChecker(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(String... args) {
        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            String catalog = connection.getCatalog();
            log.info("Database connection successful - {} ({})", catalog, url);
        } catch (Exception e) {
            log.error("Database connection failed: {}", e.getMessage());
        }
    }
}
