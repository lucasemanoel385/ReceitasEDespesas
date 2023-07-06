package br.com.receitas.infra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
	
	public void addCorsMappings(CorsRegistry resgistry) {
		resgistry.addMapping("/**")
								.allowedOriginPatterns("*")
								.allowedHeaders("*")
				                .allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS");;
	}

}
