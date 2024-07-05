package com.example.spending.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class represents the application configuration for the project.
 *
 * <p>The configuration is defined using the {@code @Configuration} annotation, indicating
 * that this class provides bean definitions. The {@code ApplicationConfig} class is responsible for
 * defining the bean {@code mapper}, which is an instance of {@link ModelMapper}.
 *
 * <p>{@code ModelMapper} is a library that maps one object to another by automatically
 * determining how the properties of the source object should be mapped to the target object. The
 * {@code ModelMapper} bean will be used for this purpose throughout the application.
 *
 * <p>To use the {@code ApplicationConfig} class, create an instance of it and use it
 * to access the defined beans. For example:
 *
 * <pre>{@code
 * ApplicationConfig config = new ApplicationConfig();
 * ModelMapper mapper = config.mapper();
 * }</pre>
 */
@Configuration
public class ApplicationConfig {

  @Bean
  public ModelMapper mapper() {
    return new ModelMapper();
  }
}
