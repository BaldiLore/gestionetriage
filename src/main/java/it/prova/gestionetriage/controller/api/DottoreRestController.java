package it.prova.gestionetriage.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.prova.gestionetriage.exceptions.DottoreNotFoundException;
import it.prova.gestionetriage.model.Dottore;
import it.prova.gestionetriage.service.DottoreService;

@RestController
@RequestMapping(value = "/dottore", produces = { MediaType.APPLICATION_JSON_VALUE })
public class DottoreRestController {

	@Autowired
	DottoreService dottoreService;
	
	@GetMapping("/{idInput}")
	public Dottore getDottore(@PathVariable(required = true) Long idInput) {
		return dottoreService.get(idInput);
	}

	@GetMapping
	public List<Dottore> getAll() {
		return dottoreService.listAll();
	}

	@PostMapping("/search")
	public ResponseEntity<Page<Dottore>> searchAndPagination(@RequestBody Dottore dottoreExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {

		Page<Dottore> results = dottoreService.searchAndPaginate(dottoreExample, pageNo, pageSize, sortBy);

		return new ResponseEntity<Page<Dottore>>(results, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public Dottore createNewDottore(@RequestBody Dottore dottoreInput) {
		return dottoreService.save(dottoreInput);
	}

	@PutMapping("/{id}")
	public Dottore updateDottore(@RequestBody Dottore dottoreInput, @PathVariable Long id) {
		
		Dottore dottoreToUpdate = dottoreService.get(id);
		
		if(dottoreToUpdate == null)
			throw new DottoreNotFoundException("Dottore non presente");
		
		dottoreToUpdate.setCodiceDipendente(dottoreInput.getCodiceDipendente());
		dottoreToUpdate.setNome(dottoreInput.getNome());
		dottoreToUpdate.setCognome(dottoreInput.getCognome());
		dottoreToUpdate.setPazienteAttualmenteInVisita(dottoreInput.getPazienteAttualmenteInVisita());
		return dottoreService.save(dottoreToUpdate);
	}

	@DeleteMapping("/{id}")
	public void deleteDottore(@PathVariable(required = true) Long id) {
		dottoreService.delete(dottoreService.get(id));
	}
}
