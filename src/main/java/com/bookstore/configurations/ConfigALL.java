package com.bookstore.configurations;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableWebMvc
@EnableSwagger2
public class ConfigALL {

    @Bean
    SimpleMailMessage simpleMailMessage(){
        return new SimpleMailMessage();
    }


    @Bean
    ModelMapper modelMapper (){
        return new ModelMapper();
    }
    @Bean
    public Docket userApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.bookstore.controller"))
                .paths(PathSelectors.regex("/storeUser/.*"))
                .build().apiInfo(UserInfo());
    }
    private ApiInfo UserInfo() {
        return new ApiInfoBuilder().title("bookstore/CreatedByArindamSaha").build();
    }
}
