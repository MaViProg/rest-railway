package com.ikonnikova.restrailway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.ikonnikova.restrailway.repository",
        repositoryBaseClass = SimpleJpaRepository.class)
@EnableConfigurationProperties
@ComponentScan(basePackages = {"com.ikonnikova.restrailway", "com.ikonnikova.restrailway.controller"})
@EntityScan("com.ikonnikova.restrailway.entity")
@EnableWebMvc
public class RestRailwaySecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(RestRailwaySecurityApplication.class, args);
    }

//    @Bean
//    public OpenAPI customOpenAPI(@Value("${info.application.version:none}") String appVersion) {
//        return new OpenAPI().info(new Info()
//                .title("WAGON - API")
//                .version(appVersion)
//                .description("This is a rest service created using springdocs - " +
//                        "a library for OpenAPI 3 with spring boot.")
//                .termsOfService("../")
//                .license(new License().name("Mavi license")
//                        .url("http://uralsib.ru")));
//    }

}

