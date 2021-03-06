package com.omniesoft.commerce.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        for (HttpMessageConverter<?> converter : converters) {
            if (converter.getClass().equals(MappingJackson2HttpMessageConverter.class)) {
                ((MappingJackson2HttpMessageConverter) converter).getObjectMapper()
                        .registerModule(new Hibernate5Module());
            }
        }
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new Hibernate5Module());
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule(new Jdk8Module());

        converter.setObjectMapper(objectMapper);
        converter.setPrettyPrint(true);
        converter.setSupportedMediaTypes(supportedMediaTypes);
        converters.add(converter);

        super.extendMessageConverters(converters);
    }

    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String text, Locale locale) throws ParseException {
                return LocalDate.parse(text, DateTimeFormatter.ISO_DATE);
            }

            @Override
            public String print(LocalDate object, Locale locale) {
                return DateTimeFormatter.ISO_DATE.format(object);
            }
        };
    }

    @Bean
    public Formatter<LocalDateTime> localDateTimeFormatter() {
        return new Formatter<LocalDateTime>() {
            @Override
            public LocalDateTime parse(String text, Locale locale) throws ParseException {
                return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
            }

            @Override
            public String print(LocalDateTime object, Locale locale) {
                return DateTimeFormatter.ISO_DATE_TIME.format(object);
            }
        };
    }

}
