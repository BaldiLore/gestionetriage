package it.prova.gestionetriage.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionetriage.model.Paziente;

public interface PazienteService {

	List<Paziente> listAll();

	Page<Paziente> searchAndPaginate(Paziente pazienteExample, Integer pageNo, Integer pageSize, String sortBy);

	Paziente get(Long idInput);

	Paziente save(Paziente input);

	void delete(Paziente input);
	
	Paziente findByCodiceFiscale(String codice);
}
