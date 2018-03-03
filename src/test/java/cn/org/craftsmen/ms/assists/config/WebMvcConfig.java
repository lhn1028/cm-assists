package cn.org.craftsmen.ms.assists.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Profile("test")
@EnableWebMvc
@ComponentScan(basePackages= {"cn.org.craftsmen.ms.assists.web"})
@Configuration
public class WebMvcConfig {

}
