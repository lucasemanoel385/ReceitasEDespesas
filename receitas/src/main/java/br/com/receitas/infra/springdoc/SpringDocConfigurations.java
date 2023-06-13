package br.com.receitas.infra.springdoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfigurations {
	
	//Usamos customOpenAPI pra adicionar esquema de segurança por bearer key no formato JWT e informações, feito isso precisamos colocar uma anotação de segurança em cima das classes (ou dos metodos/url) que só pode ser acessadas depois de logado SecurityRequirement(name = "bearer-key")
	//também vai precisar alterar o método securityFilterChain, na classe SecurityConfigurations, para liberar acesso aos endpoints disponibilizados pelo SpringDoc: .requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()
	@Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                       .components(new Components()
                           .addSecuritySchemes("bearer-key",
                           new SecurityScheme().type(SecurityScheme.Type.HTTP)
                           .scheme("bearer").bearerFormat("JWT")))
                       .info(new Info()
                               .title("Receitas Despesas API")
                               .description("API Rest da aplicação ReceitasDespesas, contendo as funcionalidades de CRUD de Receitas e Despesas")
                               .contact(new Contact()
                                       .name("Time Backend")
                                       .email("backend@receitasdespesas.pic"))
                       .license(new License()
                               .name("Apache 2.0")
                               .url("http://ReceitasDespesas.pic/api/licenca")));
   }

}
