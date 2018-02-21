package com.omniesoft.commerce.owner.config;

import com.fasterxml.classmate.TypeResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.async.DeferredResult;
import springfox.documentation.builders.*;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.schema.WildcardType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newMapRule;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

/**
 * @author Vitalii Martynovskyi
 * @since 28.09.17
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String ERROR = "Error";

    private final TypeResolver typeResolver;

    @Autowired
    public SwaggerConfig(TypeResolver typeResolver) {

        this.typeResolver = typeResolver;
    }

    @Bean
    public Docket api() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("owner-api")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.omniesoft.commerce.owner.controller"))
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/")
                .genericModelSubstitutes(ResponseEntity.class)
                .directModelSubstitute(Map.class, Map.class)
                .directModelSubstitute(LocalDateTime.class, String.class)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ZonedDateTime.class, String.class)
                .alternateTypeRules(rules(), newMapRule(WildcardType.class, WildcardType.class))
                .alternateTypeRules()
                .globalResponseMessage(RequestMethod.GET, globalResponseMessages())
                .globalResponseMessage(RequestMethod.POST, globalResponseMessages())
                .globalOperationParameters(globalOperationParameters());
    }


    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("Omnie Owner Microservice")
                .version("1.0")
                .build();
    }

    private AlternateTypeRule rules() {
        return newRule(
                typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)),
                typeResolver.resolve(WildcardType.class));
    }

    private ArrayList<ResponseMessage> globalResponseMessages() {

        return newArrayList(
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not found")
                        .responseModel(new ModelRef(ERROR))
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("Bad authorization / Unauthorized")
                        .responseModel(new ModelRef(ERROR))
                        .build(),
                new ResponseMessageBuilder()
                        .code(301)
                        .message("301 message")
                        .responseModel(new ModelRef(ERROR))
                        .build()
        );
    }

    private ArrayList<Parameter> globalOperationParameters() {

        return newArrayList(
                new ParameterBuilder()
                        .hidden(true)
                        .name("Authorization")
                        .description("Bearer")
                        .defaultValue("Bearer ")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build(),
                new ParameterBuilder()
                        .hidden(true)
                        .name("Content-Type")
                        .description("Content type")
                        .defaultValue("application/json")
                        .modelRef(new ModelRef("string"))
                        .parameterType("header")
                        .required(false)
                        .build()
        );
    }

}

