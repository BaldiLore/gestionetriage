package it.prova.gestionetriage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class DottoreNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DottoreNotFoundException(String message) {
		super(message);
	}

}
