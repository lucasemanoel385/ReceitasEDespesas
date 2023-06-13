package br.com.receitas.domain.resumo;

import lombok.Data;

@Data
public class ResumoMes {
	
	private Float totalReceitaMes;
	private Float totalDespesaMes;
	private Float totalFinalMes;
	private TotalGastoMesCategoriaDTO valorTotalGastoPorCategoria;
	
	public ResumoMes(Float totalReceitaMes, Float totalDespesaMes, Float totalFinalMes, TotalGastoMesCategoriaDTO despesa) {
		this.totalReceitaMes = totalReceitaMes;
		this.totalDespesaMes = totalDespesaMes;
		this.totalFinalMes = totalFinalMes;
		this.valorTotalGastoPorCategoria = new TotalGastoMesCategoriaDTO(despesa);
	}
	
	

}
