package ru.itpark.annotation;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class AnnotationConfigurer extends PropertyPlaceholderConfigurer {
    public AnnotationConfigurer() {
        setLocation(new ClassPathResource("connection.properties"));
    }
}
