//package com.ikonnikova.restrailway.config;
//
//import oracle.jdbc.pool.OracleDataSource;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.Properties;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = "com.ikonnikova.restrailway.repository")
//@EntityScan("com.ikonnikova.restrailway.entity")
//public class OracleJpaConfig {
//
//    @Value("${spring.datasource.oracle.url}")
//    private String oracleUrl;
//
//    @Value("${spring.datasource.oracle.username}")
//    private String oracleUsername;
//
//    @Value("${spring.datasource.oracle.password}")
//    private String oraclePassword;
//
//    @Value("${spring.datasource.oracle.internalLogon}")
//    private String internalLogonMode;
//
//    @Bean(name = "oracleDataSource")
//    public DataSource oracleDataSource() throws SQLException {
//        OracleDataSource dataSource = new OracleDataSource();
//        dataSource.setURL(oracleUrl);
//        dataSource.setUser(oracleUsername);
//        dataSource.setPassword(oraclePassword);
//
//        Properties connectionProperties = new Properties();
//        connectionProperties.setProperty("internal_logon", internalLogonMode);
//
//        dataSource.setConnectionProperties(connectionProperties);
//
//        return dataSource;
//    }
//
//    @Bean(name = "oracleEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(@Qualifier("oracleDataSource") DataSource dataSource) {
//        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        vendorAdapter.setGenerateDdl(true);
//
//        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(dataSource);
//        emf.setPackagesToScan("com.ikonnikova.restrailway.entity");
//        emf.setJpaVendorAdapter(vendorAdapter);
//        emf.setJpaProperties(oracleAdditionalProperties());
//        emf.afterPropertiesSet();
//
//        return emf;
//    }
//
//    @Bean(name = "transactionManager")
//    public PlatformTransactionManager transactionManager(@Qualifier("oracleEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//
//    @Bean
//    public ModelMapper modelMapper() {
//        return new ModelMapper();
//    }
//
//    @Bean(name = "oracleJdbcTemplate")
//    public JdbcTemplate oracleJdbcTemplate(@Qualifier("oracleDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//
//    private Properties oracleAdditionalProperties() {
//        Properties properties = new Properties();
//        properties.setProperty("hibernate.hbm2ddl.auto", "update");
//        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle12cDialect");
//        return properties;
//    }
//}
