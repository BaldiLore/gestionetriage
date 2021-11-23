package it.prova.gestionetriage.service;

import java.util.List;

import org.springframework.data.domain.Page;

import it.prova.gestionetriage.model.Dottore;


public interface DottoreService {

	List<Dottore> listAll();

	Page<Dottore> searchAndPaginate(Dottore dottoreExample, Integer pageNo, Integer pageSize, String sortBy);

	Dottore get(Long idInput);

	Dottore save(Dottore input);

	void delete(Dottore input);
	
	Dottore findByCodice(String codice);

}
