package com.ikonnikova.restrailway.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Конфигурационный класс для настройки JPA и управления транзакциями.
 */
@Configuration
@EnableTransactionManagement
@EntityScan("com.ikonnikova.restrailway.entity")
public class JpaConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    /**
     * Создает и настраивает источник данных для подключения к базе данных.
     *
     * @return Объект DataSource для подключения к базе данных.
     */
    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(60000);

        return new HikariDataSource(config);
    }

    /**
     * Создает и настраивает фабрику сущностей для работы с JPA.
     *
     * @param dataSource Источник данных для подключения к базе данных.
     * @return Объект LocalContainerEntityManagerFactoryBean для работы с JPA.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);

        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.ikonnikova.restrailway.entity");
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(additionalProperties());
        emf.afterPropertiesSet();

        return emf;
    }

    /**
     * Создает и настраивает менеджер транзакций для работы с JPA.
     *
     * @param entityManagerFactory Фабрика сущностей для работы с JPA.
     * @return Объект PlatformTransactionManager для управления транзакциями.
     */
    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    /**
     * Создает и настраивает объект ModelMapper для преобразования моделей данных.
     *
     * @return Объект ModelMapper для преобразования моделей данных.
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Создает и настраивает объект JdbcTemplate для выполнения SQL-запросов.
     *
     * @param dataSource Источник данных для подключения к базе данных.
     * @return Объект JdbcTemplate для выполнения SQL-запросов.
     */
    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    /**
     * Возвращает дополнительные свойства для настройки Hibernate.
     *
     * @return Объект Properties с дополнительными свойствами Hibernate.
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", "update");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        return properties;
    }
}

