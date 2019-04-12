package ru.itpark.java;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import ru.itpark.app.Processor;

@Configuration
public class JavaConfig {
    @Bean
    public static PropertyPlaceholderConfigurer configurer() {
        var conf = new PropertyPlaceholderConfigurer();
        conf.setLocation(new ClassPathResource("connection.properties"));
        return conf;
    }

    @Bean
    public static Processor processor() {
        return new Processor();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }

    @Bean("javaClient")
    public JavaRequestClient javaRequestClient(@Value("${url}") String url) {
        var client = new JavaRequestClient();
        client.setUrl(url);
        client.setGson(gson());
        return client;
    }
}
