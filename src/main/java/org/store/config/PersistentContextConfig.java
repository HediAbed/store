package org.store.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("org.store")
@EnableJpaRepositories("org.store.repository")
public class PersistentContextConfig {
}
