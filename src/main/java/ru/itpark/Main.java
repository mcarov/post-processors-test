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
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
            XmlRequestClient bean = context.getBean("xmlClient", XmlRequestClient.class);
            System.out.println(bean.getPost(100));
            Thread.sleep(500);
            System.out.println(bean.getPost(90));
            Thread.sleep(500);
            System.out.println(bean.getPost(100));
            Thread.sleep(500);
        }
        // Java config
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(JavaConfig.class);
            JavaRequestClient bean = context.getBean("javaClient", JavaRequestClient.class);
            System.out.println(bean.getPost(80));
            Thread.sleep(500);
            System.out.println(bean.getPost(70));
            Thread.sleep(500);
            System.out.println(bean.getPost(80));
            Thread.sleep(500);
        }
        // Annotaion config
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.registerBean(Gson.class);   // от класса Gson нельзя наследоваться (помечен как final)
            context.scan("ru.itpark.annotation", "ru.itpark.app");
            context.refresh();
            AnnotationRequestClient bean = context.getBean("annotationClient", AnnotationRequestClient.class);
            System.out.println(bean.getPost(60));
            Thread.sleep(500);
            System.out.println(bean.getPost(50));
            Thread.sleep(500);
            System.out.println(bean.getPost(60));
            Thread.sleep(500);
        }
        //Program config
        {
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
            context.registerBean(PropertyPlaceholderConfigurer.class, () -> {
                PropertyPlaceholderConfigurer conf = new PropertyPlaceholderConfigurer();
                conf.setLocation(new ClassPathResource("connection.properties"));
                return conf;
            });
            context.registerBean(Processor.class);
            context.registerBean(Gson.class);
            context.registerBean("programClient", ProgramRequestClient.class);
            context.refresh();
            ProgramRequestClient bean = context.getBean("programClient", ProgramRequestClient.class);
            System.out.println(bean.getPost(40));
            Thread.sleep(500);
            System.out.println(bean.getPost(30));
            Thread.sleep(500);
            System.out.println(bean.getPost(30));
            Thread.sleep(500);
        }
        // Groovy config
        {
            GenericGroovyApplicationContext context = new GenericGroovyApplicationContext("beans.groovy");
            GroovyRequestClient bean = context.getBean("groovyClient", GroovyRequestClient.class);
            System.out.println(bean.getPost(20));
            Thread.sleep(500);
            System.out.println(bean.getPost(10));
            Thread.sleep(500);
            System.out.println(bean.getPost(10));
            Thread.sleep(500);
        }
    }
}
