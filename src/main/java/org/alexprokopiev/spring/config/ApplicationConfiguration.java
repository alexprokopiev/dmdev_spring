package org.alexprokopiev.spring.config;

import org.alexprokopiev.web.config.WebConfiguration;
import org.springframework.context.annotation.*;

@Configuration
@Import(WebConfiguration.class)
public class ApplicationConfiguration {
}
