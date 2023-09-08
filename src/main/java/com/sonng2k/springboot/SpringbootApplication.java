package com.sonng2k.springboot;

// Create an Application class

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

// @SpringBootApplication is a convenience annotation that adds all of the following:
// - @Configuration: Tags the class as a source of bean definitions for the application context.
// - @EnableAutoConfiguration: Tells Spring Boot to start adding beans based on classpath settings, other
// beans, and various property settings. For example, if spring-webmvc is on the classpath, this annotation
// flags the application as a web application and activates key behaviors, such as setting up a DispatcherServlet.
// - @ComponentScan: Tells Spring to look for other components, configurations, and services in the
// com/sonng2k package, letting it find the controllers.

// The main() method uses Spring Boot’s SpringApplication.run() method to launch an application. Did you
// notice that there was not a single line of XML? There is no web.xml file, either. This web application is
// 100% pure Java and you did not have to deal with configuring any plumbing or infrastructure.

// There is also a CommandLineRunner method marked as a @Bean, and this runs on start up. It retrieves all
// the beans that were created by your application or that were automatically added by Spring Boot. It sorts
// them and prints them out.

@SpringBootApplication
public class SpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

}
