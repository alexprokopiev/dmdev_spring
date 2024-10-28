package org.alexprokopiev.spring.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "db")
public class DatabaseProperties {
}
