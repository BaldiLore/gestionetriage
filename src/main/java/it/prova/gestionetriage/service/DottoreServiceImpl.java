package it.prova.gestionetriage.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import it.prova.gestionetriage.exceptions.DottoreNotFoundException;
import it.prova.gestionetriage.model.Dottore;
import it.prova.gestionetriage.repository.DottoreRepository;

@Service
public class DottoreServiceImpl implements DottoreService {

	@Autowired
	private DottoreRepository dottoreRepository;

	@Override
	public List<Dottore> listAll() {
		return (List<Dottore>) dottoreRepository.findAll();
	}

	@SuppressWarnings("deprecation")
	@Override
	public Page<Dottore> searchAndPaginate(Dottore dottoreExample, Integer pageNo, Integer pageSize,
			String sortBy) {

		Specification<Dottore> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (!StringUtils.isEmpty(dottoreExample.getNome()))
				predicates.add(cb.like(cb.upper(root.get("nome")), "%" + dottoreExample.getNome().toUpperCase() + "%"));

			if (!StringUtils.isEmpty(dottoreExample.getCognome()))
				predicates.add(cb.like(cb.upper(root.get("cognome")), "%" + dottoreExample.getCognome().toUpperCase() + "%"));

			if (!StringUtils.isEmpty(dottoreExample.getCodiceDipendente()))
				predicates.add(cb.like(cb.upper(root.get("codiceDipendente")), "%" + dottoreExample.getCodiceDipendente().toUpperCase() + "%"));

			if(dottoreExample.getPazienteAttualmenteInVisita() != null && dottoreExample.getPazienteAttualmenteInVisita().getId() != null)
				predicates.add(cb.like(cb.upper(root.get("pazienteAttualmenteInVisita")), "%" + dottoreExample.getPazienteAttualmenteInVisita() + "%"));
			
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		return dottoreRepository.findAll(specificationCriteria, paging);
	}

	@Override
	public Dottore get(Long idInput) {
		return dottoreRepository.findById(idInput)
				.orElseThrow(() -> new DottoreNotFoundException("Element with id " + idInput + " not found."));
	}

	@Override
	public Dottore save(Dottore input) {
		return dottoreRepository.save(input);
	}

	@Override
	public void delete(Dottore input) {
		dottoreRepository.delete(input);
	}

	@Override
	public Dottore findByCodice(String codice) {
		return dottoreRepository.findByCodiceDipendente(codice);
	}
	
}
