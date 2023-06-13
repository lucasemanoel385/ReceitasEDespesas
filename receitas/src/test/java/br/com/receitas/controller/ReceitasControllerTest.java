package br.com.receitas.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.receitas.domain.receitas.DadosCadastroEAtualizarReceita;
import br.com.receitas.domain.receitas.DadosDetalhamentoReceitas;
import br.com.receitas.domain.receitas.ListarReceitas;
import br.com.receitas.domain.receitas.Receitas;
import br.com.receitas.domain.receitas.service.ReceitasService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
public class ReceitasControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private ReceitasService service;
	
	@Autowired
	private JacksonTester<DadosCadastroEAtualizarReceita> dadosCadastroJson;
	
	@Autowired
	private JacksonTester<DadosDetalhamentoReceitas> dadosDetalhadosJson;
	
	@Autowired
	private JacksonTester<ListarReceitas> listarReceitasJson;
	
	@Test
	@DisplayName("Deve devolver codigo http 400 quando informacoes estão invalidas")
	@WithMockUser
	void cadastrar_cenario1() throws Exception {
		
		var response = mvc.perform(MockMvcRequestBuilders.post("/receitas"))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
	}
	
	@Test
	@DisplayName("Cadastra uma receita")
	@WithMockUser
	void cadastrar_cenario2() throws Exception {
		
		Float valor = (float) 10.23;
		LocalDate data = LocalDate.of(2023, 11, 5);
		
		var receita = new DadosCadastroEAtualizarReceita("tes", valor, data);
		
		var dadosDetalhamentoReceita = new DadosDetalhamentoReceitas(null,
				receita.descricao(),
				receita.valor(),
				receita.data());
		
		when(service.cadastrar(any())).thenReturn(dadosDetalhamentoReceita);
		
		var response = mvc.perform(
				MockMvcRequestBuilders.post("/receitas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosCadastroJson.write(receita).getJson()))
				.andReturn().getResponse();
		
		
		
		var jsonEsperado = dadosDetalhadosJson.write(dadosDetalhamentoReceita).getJson();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
		
		assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
		
	}
	
	@Test
	@DisplayName("Campo invalido, retorna erro 400")
	@WithMockUser
	void cadastrar_cenario3() throws Exception {
		
		Float valor = (float) 10.23;
		LocalDate data = LocalDate.of(2023, 11, 5);
		
		
		var dadosDetalhamentoReceita = new DadosDetalhamentoReceitas(null ,
				null,
				valor,
				data);
		
		when(service.cadastrar(any())).thenReturn(dadosDetalhamentoReceita);
		
		var response = mvc.perform(
				MockMvcRequestBuilders.post("/receitas")
				.contentType(MediaType.APPLICATION_JSON)
				.content(dadosCadastroJson.write(
						new DadosCadastroEAtualizarReceita(null, valor, data)).getJson()))
				.andReturn().getResponse();
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
		
	
		
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 ok")
	@WithMockUser
	void listar_cenario1() throws Exception {
		
		var response = mvc.perform(MockMvcRequestBuilders.get("/receitas"))
				.andReturn().getResponse();
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
	}
	
	@Test
	@DisplayName("Deve devolver codigo http 200 ok")
	@WithMockUser
	void atualizarReceitasPeloId() throws Exception {

		var receita = mockarReceita();
		
		var listarReceita = new ListarReceitas(1l,
				receita.getDescricao(),
				receita.getValor(),
				receita.getData());
		
		when(service.atualizarReceita(any(),any())).thenReturn(listarReceita);
		
		var response = mvc.perform(MockMvcRequestBuilders.put("/receitas/{id}", 1l)
				.contentType(MediaType.APPLICATION_JSON)
				.content(listarReceitasJson.write(new ListarReceitas(receita.getId() ,
						receita.getDescricao(), receita.getValor(),receita.getData())).getJson()))
				.andReturn().getResponse();
		
		
		//assertThat(response.getStatus()).isEqualTo(jsonEsperado);
		
		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
		
		
	}
	
	
	private Receitas mockarReceita() {
		Float valor = (float) 10.23;
		LocalDate data = LocalDate.of(2023, 11, 5);
		return new Receitas(new DadosCadastroEAtualizarReceita("teste", valor, data));
	}
	
	private String toJson(Object obj) throws JsonProcessingException {
		ObjectMapper object = new ObjectMapper();
		return object.writeValueAsString(obj);
	}
	
}
