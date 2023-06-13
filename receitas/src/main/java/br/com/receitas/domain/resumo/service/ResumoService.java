package br.com.receitas.domain.resumo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.receitas.domain.despesas.Despesas;
import br.com.receitas.domain.despesas.DespesasRepository;
import br.com.receitas.domain.despesas.categoria.CategoriaDespesas;
import br.com.receitas.domain.receitas.Receitas;
import br.com.receitas.domain.receitas.ReceitasRepository;
import br.com.receitas.domain.resumo.ResumoMes;
import br.com.receitas.domain.resumo.TotalGastoMesCategoriaDTO;

@Service
public class ResumoService {
	
	@Autowired
	private ReceitasRepository receitaRepository;
	@Autowired
	private DespesasRepository despesaRepository;
	
public ResumoMes resumoDoMes(int ano, int mes, Pageable page) {
		
		
		var listaDespesaMes = despesaRepository.findAllByAnoAndMes(ano, mes, page);
		
		var despesaMesCategoria = new TotalGastoMesCategoriaDTO();
		
		for (Despesas despesas : listaDespesaMes) {
				if (despesas.getCategoria() == CategoriaDespesas.Alimentacao) {
					var soma = despesaMesCategoria.getAlimentacao() + despesas.getValor();
					despesaMesCategoria.setAlimentacao(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Saude) {
					var soma = despesaMesCategoria.getSaude() + despesas.getValor();
					despesaMesCategoria.setSaude(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Moradia) {
					var soma = despesaMesCategoria.getMoradia() + despesas.getValor();
					despesaMesCategoria.setMoradia(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Transporte) {
					var soma = despesaMesCategoria.getTransporte() + despesas.getValor();
					despesaMesCategoria.setTransporte(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Educacao) {
					var soma = despesaMesCategoria.getEducacao() + despesas.getValor();
					despesaMesCategoria.setEducacao(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Lazer) {
					var soma = despesaMesCategoria.getLazer() + despesas.getValor();
					despesaMesCategoria.setLazer(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Imprevistos) {
					var soma = despesaMesCategoria.getImprevistos() + despesas.getValor();
					despesaMesCategoria.setImprevistos(soma);
				}
				else if (despesas.getCategoria() == CategoriaDespesas.Outras) {
					var soma = despesaMesCategoria.getOutros() + despesas.getValor();
					despesaMesCategoria.setOutros(soma);
				}
				
			}
		
		var listaReceitas= receitaRepository.findAllByAnoAndMes(ano, mes, page);
		Float totalReceitas = (float) 0;
		for (Receitas receitas : listaReceitas) {
			totalReceitas += receitas.getValor();
		}
		
		var listaDespesa = despesaRepository.findAllByAnoAndMes(ano, mes, page);
		Float totalDespesa = (float) 0;
		for (Despesas despesas : listaDespesa) {
			totalDespesa += despesas.getValor();
		}
		
		var totalFinalMes = totalReceitas - totalDespesa;
		
		//listaDespesaCategoriaPorMes.get(i) está com a mesma ordenação/numeração do enum categoriaDespesas
//		var despesaTotal = new TotalGastoMesCategoriaDTO(listaDespesaCategoriaPorMes.get(0), listaDespesaCategoriaPorMes.get(1), listaDespesaCategoriaPorMes.get(2), listaDespesaCategoriaPorMes.get(3), listaDespesaCategoriaPorMes.get(4),
//				listaDespesaCategoriaPorMes.get(5), listaDespesaCategoriaPorMes.get(6), listaDespesaCategoriaPorMes.get(7));
		ResumoMes test = new ResumoMes(totalReceitas, totalDespesa, totalFinalMes, despesaMesCategoria);
		
		return test;
		
		
	}
	
}