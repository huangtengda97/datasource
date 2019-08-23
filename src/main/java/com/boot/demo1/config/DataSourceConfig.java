package com.boot.demo1.config;

import com.boot.demo1.datasource.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    @Bean(name = "masterDateSource")
    @ConfigurationProperties(prefix = "spring.master.datasource")
    public HikariDataSource masterDataSource() {
        return new HikariDataSource();
    }
    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "spring.slave1.datasource")
    public HikariDataSource slave1DataSource() {
        return new HikariDataSource();
    }

    @Bean(name = "slave2DataSource")
    @ConfigurationProperties(prefix = "spring.slave2.datasource")
    public HikariDataSource slave2DataSource() {
        return new HikariDataSource();
    }

//    @Bean(name = "dynamicDataSource")
//    @DependsOn({"masterDateSource", "slave1DataSource", "slave2DataSource"})
//    @Primary
//    public DataSource getDataSource() {
//        MyDynamicDataSource dataSource = new MyDynamicDataSource(2);
//        dataSource.setTargetDataSources(targetDataSources());
//        return dataSource;
//    }

    private Map<Object, Object> targetDataSources() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataSourceType.MASTER.getType(), masterDataSource());
        targetDataSources.put(DataSourceType.SLAVE.getType(), slave1DataSource());
        targetDataSources.put(DataSourceType.SLAVE.getType(), slave2DataSource());
        return targetDataSources;
    }
}
