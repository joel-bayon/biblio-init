package biblio.configuration;

import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@PropertySource("spring/bibliotheque.properties")
@ImportResource("spring.aop.xml")
@EnableJpaRepositories(basePackages="biblio.repository")
public class BiblioConfiguration {}
