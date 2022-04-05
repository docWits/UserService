package com.romanyuta.UserService.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Value("${swagger.base-url}")
    private String baseUrl;


    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .addServersItem(new Server().url(baseUrl))
                .info(
                        new Info()
                                .title("UserService")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("dm.romanyuta@yandex.ru")
                                                .name("Romanyuta Dmitry")
                                )
                );
    }
}
