package com.studydashboard.api.global.config.database;

import com.studydashboard.api.global.config.aws.SecretManagerConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class DatasourceConfig {

    private final SecretManagerConfig secretManagerConfig;


    @Bean
    public DataSource dataSource() {
        Map<String, String> secrets = secretManagerConfig.getSecret("dev/database/configures");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(secrets.get("DATABASE_URL"));
        config.setUsername(secrets.get("DATABASE_USERNAME"));
        config.setPassword(secrets.get("DATABASE_PASSWORD"));
        config.setDriverClassName("org.postgresql.Driver");

        return new HikariDataSource(config);
    }
}