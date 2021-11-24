package it.prova.gestionetriage.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class AssegnazioneNonAvvenutaException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AssegnazioneNonAvvenutaException(String message) {
		super(message);
	}

}
