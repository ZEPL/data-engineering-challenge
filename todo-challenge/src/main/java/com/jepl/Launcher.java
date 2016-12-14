package com.jepl;

import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.config.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainer;
import org.springframework.boot.context.embedded.jetty.JettyEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.inject.*;

@Configuration
@EnableAutoConfiguration
@ComponentScan(basePackages = {"com.jepl"})
public class Launcher extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext ctx = SpringApplication.run(Launcher.class, args);
//        System.getProperty("port")
//        System.out.println();

    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Launcher.class);
    }

    @Bean
    public ServletRegistrationBean jerseyServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, ApplicationConfiguration.class.getName());
        return registration;
    }

    @Bean
    public EmbeddedServletContainerFactory containerFactory() {
        final JettyEmbeddedServletContainerFactory jettyEmbeddedServletContainerFactory = new JettyEmbeddedServletContainerFactory() {
            @Override
            protected JettyEmbeddedServletContainer getJettyEmbeddedServletContainer(Server server) {
                return new JettyEmbeddedServletContainer(server);
            }
        };
        jettyEmbeddedServletContainerFactory.addServerCustomizers(new JettyConfigurer());
        return jettyEmbeddedServletContainerFactory;
    }
}
