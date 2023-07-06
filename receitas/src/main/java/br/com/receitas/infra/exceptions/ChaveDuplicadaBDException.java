package br.com.receitas.infra.exceptions;

public class ChaveDuplicadaBDException extends RuntimeException{
	
	public ChaveDuplicadaBDException(String msg) {
		super(msg + ChaveDuplicadaBDException.class);
	}

}
