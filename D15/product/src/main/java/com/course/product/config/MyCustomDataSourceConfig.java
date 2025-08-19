//package com.course.product.config;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import javax.sql.DataSource;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//@Configuration
//public class MyCustomDataSourceConfig {
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:mem:testdb");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//        System.out.println("Using custom data source!");
//        return dataSource;
//    }
//}
