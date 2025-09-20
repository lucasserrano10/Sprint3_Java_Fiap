package com.example.smartclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * Classe de conexão com o Banco de Dados (credenciais embutidas no código conforme requisito).
 */
@Configuration
public class DatabaseConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.h2.Driver");
        ds.setUrl("jdbc:h2:mem:smartclinicdb;DB_CLOSE_DELAY=-1;MODE=MYSQL");
        ds.setUsername("smartuser");
        ds.setPassword("smartpass");
        return ds;
    }
}
