package br.com.receitas.domain.despesas;

import java.time.LocalDate;

import br.com.receitas.domain.despesas.categoria.CategoriaDespesas;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "despesas")
@Entity(name = "Despesas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Despesas {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String descricao;
	private Float valor;
	private LocalDate data;
	@Enumerated(EnumType.STRING)
	private CategoriaDespesas categoria;
	
	
	public Despesas(DadosCadastroEAtualizarDespesas dados) {
		this.descricao = dados.descricao();
		this.valor = dados.valor();
		this.data = dados.data();
		this.categoria = dados.categoria();
	}
	
	

}
