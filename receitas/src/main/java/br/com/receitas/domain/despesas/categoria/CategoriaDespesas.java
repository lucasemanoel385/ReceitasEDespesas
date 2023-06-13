package br.com.receitas.domain.despesas.categoria;

public enum CategoriaDespesas {
	
	Alimentacao(0),
	Saude(1),
	Moradia(2),
	Transporte(3),
	Educacao(4),
	Lazer(5),
	Imprevistos(6),
	Outras(7); 
	
	private final int valor;
	CategoriaDespesas(int valorOpcao){
	valor = valorOpcao;
	}
	public int getValor(){
	return valor;
	}
}

