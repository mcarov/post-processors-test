package ru.itpark;

import com.google.gson.Gson;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.GenericGroovyApplicationContext;
import org.springframework.core.io.ClassPathResource;
import ru.itpark.annotation.AnnotationRequestClient;
import ru.itpark.app.Processor;
import ru.itpark.groovy.GroovyRequestClient;
import ru.itpark.java.JavaRequestClient;
import ru.itpark.program.ProgramRequestClient;
import ru.itpark.xml.XmlRequestClient;
import ru.itpark.java.JavaConfig;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // XML config
        {
            var context = new ClassPathXmlApplicationContext("beans.xml");
            var bean = context.getBean("xmlClient", XmlRequestClient.class);
            for (int i = 0; i < 2; i++) {
                System.out.println(bean.getPost(100));
                Thread.sleep(500);
            }
        }
        // Java config
        {
            var context = new AnnotationConfigApplicationContext(JavaConfig.class);
            var bean = context.getBean("javaClient", JavaRequestClient.class);
            for (int i = 0; i < 2; i++) {
                System.out.println(bean.getPost(90));
                Thread.sleep(500);
            }
        }
        // Annotaion config
        {
            var context = new AnnotationConfigApplicationContext();
            context.registerBean(Gson.class);   // от класса Gson нельзя наследоваться (помечен как final)
            context.scan("ru.itpark.annotation", "ru.itpark.app");
            context.refresh();
            var bean = context.getBean("annotationClient", AnnotationRequestClient.class);
            for (int i = 0; i < 2; i++) {
                System.out.println(bean.getPost(80));
                Thread.sleep(500);
            }
        }
        //Program config
        {
            var context = new AnnotationConfigApplicationContext();
            context.registerBean(PropertyPlaceholderConfigurer.class, () -> {
                var conf = new PropertyPlaceholderConfigurer();
                conf.setLocation(new ClassPathResource("connection.properties"));
                return conf;
            });
            context.registerBean(Processor.class);
            context.registerBean(Gson.class);
            context.registerBean("programClient", ProgramRequestClient.class);
            context.refresh();
            var bean = context.getBean("programClient", ProgramRequestClient.class);
            for (int i = 0; i < 2; i++) {
                System.out.println(bean.getPost(70));
                Thread.sleep(500);
            }
        }
        // Groovy config
        {
            var context = new GenericGroovyApplicationContext("beans.groovy");
            var bean = context.getBean("groovyClient", GroovyRequestClient.class);
            for (int i = 0; i < 2; i++) {
                System.out.println(bean.getPost(60));
                Thread.sleep(500);
            }
        }
    }
}
