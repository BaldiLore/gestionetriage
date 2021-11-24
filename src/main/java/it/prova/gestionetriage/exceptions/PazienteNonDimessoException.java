package it.prova.gestionetriage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PazienteNonDimessoException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PazienteNonDimessoException(String message) {
		super(message);
	}

}
