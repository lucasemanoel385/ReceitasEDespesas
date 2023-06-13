package br.com.receitas.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Iterator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import br.com.receitas.domain.despesas.Despesas;
import br.com.receitas.domain.despesas.DespesasRepository;
import br.com.receitas.domain.despesas.categoria.CategoriaDespesas;
import br.com.receitas.domain.receitas.ReceitasRepository;
import br.com.receitas.domain.resumo.ResumoMes;
import br.com.receitas.domain.resumo.service.ResumoService;

@SpringBootTest //Pra testar um controller no spring
@AutoConfigureMockMvc //Serve pra injetar o mockmvc na classe junto com o autowired
@AutoConfigureJsonTesters //Pro spring rodar o jacksonTesters
public class ResumoDoMesControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ResumoService service;
	
	@Autowired
	private JacksonTester<ResumoMes> dadosCadastroJson;
	
	@Autowired
	private JacksonTester<ResumoMes> dadosDetalhadosJson;
	
	@MockBean
	private ReceitasRepository receitasRepository;
	
	@MockBean
	private DespesasRepository despesasRepository;
	
	@Test
	@DisplayName("Devolve codigo http 200 caso encontre")
	@WithMockUser
	void resumoMesCategoriaCenario01() throws Exception {
		
		var response = mvc.perform(MockMvcRequestBuilders.get("/resumo/{ano}/{mes}", 2023, 10))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
	}
	
	
	


}
