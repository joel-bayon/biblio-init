package configuration;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackageClasses={service.impl.BibliothequeSpringData.class})
@PropertySource("classpath:spring/bibliotheque.properties")
@ImportResource("classpath:spring/spring.aop.xml")
@EnableJpaRepositories(basePackages="repository.jpa")
@EnableTransactionManagement
public class BiblioConfigurationJpa {
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean sfb = new LocalContainerEntityManagerFactoryBean();
		sfb.setPersistenceUnitName("biblio");
		return sfb;
	}
	
	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
		return  new JpaTransactionManager(emf);
	}

}
