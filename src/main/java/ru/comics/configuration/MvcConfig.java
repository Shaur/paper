package ru.comics.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String currentPath = String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath);

        registry.addResourceHandler("/img/**")
                .addResourceLocations("file:///" + currentPath +"/");
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");

    }
}
