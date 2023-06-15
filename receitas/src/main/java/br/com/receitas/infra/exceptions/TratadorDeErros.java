package br.com.receitas.infra.exceptions;

import java.time.format.DateTimeParseException;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;


@RestControllerAdvice
public class TratadorDeErros {
	
	//Error 404 é tratado quando o notfoundexception que é lançada pelo jpa
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity tratarErro404() {
		
		return ResponseEntity.notFound().build();
	}
	
	//Erro pra quando tem algum campo invalido
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity tratarErro400(MethodArgumentNotValidException ex) {
		var erros = ex.getFieldErrors();
			
												//erros me da uma stream e mape-e cada objeto field erro para um objeto erro validacao  
		return ResponseEntity.badRequest().body(erros.stream().map(DadosErrosValidacao::new).toList());
	}
	
	
	@ExceptionHandler(ValidacaoException.class)
	public ResponseEntity tratarErroRegraDeNegocio(ValidacaoException ex) {
		return ResponseEntity.badRequest().body(ex.getMessage());
	}
	
	@ExceptionHandler(DateTimeParseException.class)
	public ResponseEntity tratarErroRegraDeNegocio() {
		return ResponseEntity.badRequest().body("Formatado de data Invalida. Exemplo: (yyyy-MM-dd)");
	}
	
	@ExceptionHandler(AutenticacaoException.class)
	public ResponseEntity tratarErroRegraDeNegocio(AutenticacaoException ex) {
		return ResponseEntity.status(403).body(ex.getMessage());
	}
	
	@ExceptionHandler(ChaveDuplicadaBDException.class)
	public ResponseEntity tratarErroDeDuplicidadeBD(ChaveDuplicadaBDException ex) {
		return ResponseEntity.status(400).body(ex.getMessage());
	}
	
	//Criamos essa DTO pq só vai ser usada aqui
	private record DadosErrosValidacao(String campo, String mensagem) {
		public DadosErrosValidacao(FieldError erro) {
			this(erro.getField(), erro.getDefaultMessage());
		}
	}

}
