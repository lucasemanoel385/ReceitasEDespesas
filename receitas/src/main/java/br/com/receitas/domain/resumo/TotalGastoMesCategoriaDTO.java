package br.com.receitas.domain.resumo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TotalGastoMesCategoriaDTO {
	
	private Float alimentacao = (float) 0;
	private Float saude = (float) 0;
	private Float moradia = (float) 0;
	private Float transporte = (float) 0;
	private Float educacao = (float) 0;
	private Float lazer = (float) 0;
	private Float imprevistos = (float) 0;
	private Float outros = (float) 0;
	
	public TotalGastoMesCategoriaDTO(Float alimentacao, Float saude, Float moradia, Float transporte, Float educacao,
			Float lazer, Float imprevistos, Float outros) {
		this.alimentacao = alimentacao;
		this.saude = saude;
		this.moradia = moradia;
		this.transporte = transporte;
		this.educacao = educacao;
		this.lazer = lazer;
		this.imprevistos = imprevistos;
		this.outros = outros;
	}

	public TotalGastoMesCategoriaDTO(TotalGastoMesCategoriaDTO despesa) {
		this.alimentacao = despesa.alimentacao;
		this.saude = despesa.saude;
		this.moradia = despesa.moradia;
		this.transporte = despesa.transporte;
		this.educacao = despesa.educacao;
		this.lazer = despesa.lazer;
		this.imprevistos = despesa.imprevistos;
		this.outros = despesa.outros;
	}



}
