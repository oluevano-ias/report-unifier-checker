package com.unifier.config;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "customReportsOriginalEntityManagerFactory",
        transactionManagerRef = "customReportsOriginalTransactionManager",
        basePackages = {"com.unifier.original.dao"})
public class CustomReportsOriginalDataSourceConfiguration {

    @Bean(name = "customReportsOriginalDBProperties")
    @ConfigurationProperties("datasources.original.jdbc")
    public Properties dataSourceProperties() {
        return new Properties();
    }

    @Bean(name = "customReportsOriginalDataSource")
    public HikariDataSource dataSource() {
        HikariConfig config = new HikariConfig(dataSourceProperties());
        return new HikariDataSource(config);
    }

    @PersistenceContext(unitName = "customReportsOriginal")
    @Bean(name = "customReportsOriginalEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean customReportsEntityManagerFactory(@Qualifier("customReportsOriginalDataSource") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.unifier.original.models");
        em.setPersistenceUnitName("customReportsOriginal");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    @Bean(name = "customReportsOriginalTransactionManager")
    public PlatformTransactionManager customReportsTransactionManager(
            @Qualifier("customReportsOriginalEntityManagerFactory") EntityManagerFactory customReportsEntityManagerFactory) {
        return new JpaTransactionManager(customReportsEntityManagerFactory);
    }

    Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");
        return properties;
    }

}
