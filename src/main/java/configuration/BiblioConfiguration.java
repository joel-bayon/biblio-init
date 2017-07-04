package configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan({"dao.hibernate" , "service.impl"})
@PropertySource("classpath:spring/bibliotheque.properties")
@ImportResource("classpath:spring/spring.aop.xml")
//@EnableAspectJAutoProxy
public class BiblioConfiguration {}
