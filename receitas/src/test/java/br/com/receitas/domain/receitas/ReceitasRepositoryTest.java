package br.com.receitas.domain.receitas;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

import br.com.receitas.infra.exceptions.ValidacaoException;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ActiveProfiles("test")
public class ReceitasRepositoryTest {
	
	@Autowired
	private ReceitasRepository repository;
	
	@Autowired
	private TestEntityManager em;
	
	@Test
	@DisplayName("Deve devolver receitas da data 2023-10-05")
	@WithMockUser
	void validarCadastroIgualDaMesmaReceitaNoMesCenario01() {
		
		var receita = cadastroReceita("teste", 22.15f, LocalDate.of(2023, 10, 05));
		repository.save(receita);
		var page = Pageable.ofSize(10);
		var listaAnoMes = repository.findAllByAnoAndMes(2023, 10, page);
		for (Receitas receitas : listaAnoMes) {
			assertThat(receitas.getData()).isEqualTo(receita.getData());
		}
	}
	
	@Test
	@DisplayName("Deve devolver erro caso não encontre a data informada")
	@WithMockUser
	void validarCadastroIgualDaMesmaReceitaNoMesCenario02() {
		
		var receita = cadastroReceita("teste", 22.15f, LocalDate.of(2023, 10, 05));
		repository.save(receita);
		var page = Pageable.ofSize(10);
		var listaAnoMes = repository.findAllByAnoAndMes(2023, 9, page);
		for (Receitas receitas : listaAnoMes) {
			assertThat(receitas.getData()).isEqualTo(receita.getData());
		}
	}
	
	@Test
	@DisplayName("Devolve true se é a mesma descrição no mesmo mes")
	@WithMockUser
	void validarDescricaoMesmoMesCenario01() {
		//var receita = cadastroReceita("teste", 10.25f, LocalDate.of(2023, 10, 05));
		var receita1 = cadastroReceita("teste", 10.25f, LocalDate.of(2023, 05, 05));
		var test = repository.findAll();
		
		for (Receitas receitas : test) {
			boolean treta = receitas.getDescricao().equals(receita1.getDescricao()) && receitas.getData().getMonth().equals(receita1.getData().getMonth());
			
			System.out.println(treta);
			assertThat(treta).isEqualTo(true);
			}
		
	}
	
	@Test
	@DisplayName("Devolve false se a descrição for diferente no mesmo mês")
	@WithMockUser
	void validarDescricaoMesmoMesCenario02() {
		var receita = cadastroReceita("teste", 10.25f, LocalDate.of(2023, 10, 05));
		var receita1 = new Receitas(null ,"testee", 10.25f, LocalDate.of(2023, 10, 05));
		var test = repository.findAll();
		
		for (Receitas receitas : test) {
			boolean treta = receitas.getDescricao().equals(receita1.getDescricao()) && receitas.getData().getMonth().equals(receita1.getData().getMonth());
				System.out.println(treta);
				assertThat(treta).isEqualTo(false);
			}
		
	}
	
	@Test
	@DisplayName("Devolve false se a descrição for a mesma no proximo mês")
	@WithMockUser
	void validarDescricaoMesmoMesCenario03() {
		var receita = cadastroReceita("teste", 10.25f, LocalDate.of(2023, 10, 05));
		var receita1 = new Receitas(null ,"teste", 10.25f, LocalDate.of(2023, 11, 05));
		var test = repository.findAll();

		for (Receitas receitas : test) {
			boolean treta = receitas.getDescricao().equals(receita1.getDescricao()) && receitas.getData().getMonth().equals(receita1.getData().getMonth());
				System.out.println(treta);
				assertThat(treta).isEqualTo(false);
			}
		
	}

	
	private Receitas cadastroReceita(String descricao, Float valor, LocalDate data) {
		
		var receita = new Receitas(dadosReceita(descricao, valor, data));
		em.persist(receita);
		return receita;
	}
	
	private DadosCadastroEAtualizarReceita dadosReceita(String descricao, Float valor, LocalDate data) {
		return new DadosCadastroEAtualizarReceita(descricao, valor, data);
	}

}
