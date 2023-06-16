package com.ikonnikova.restrailway.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки Swagger.
 * http://localhost:8084/swagger-ui/index.html
 *
 * Обязательно необходимы эти зависимости:
 *         <dependency>
 *             <groupId>com.fasterxml.jackson.core</groupId>
 *             <artifactId>jackson-annotations</artifactId>
 *             <version>${jackson.version}</version>
 *         </dependency>
 *         <dependency>
 *             <groupId>com.fasterxml.jackson.core</groupId>
 *             <artifactId>jackson-core</artifactId>
 *             <version>${jackson.version}</version>
 *         </dependency>
 */
@Configuration
public class SwaggerConfig {

    /**
     * Создает и настраивает экземпляр OpenAPI для документирования API.
     *
     * @return Объект OpenAPI для документации API.
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("rest-railway")
                        .version("1.0")
                        .description("API для учета операций с вагонами на предприятии"));
    }
}



