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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.prova.gestionetriage.dto.DottoreDTO;
import it.prova.gestionetriage.dto.DottoreRequestDTO;
import it.prova.gestionetriage.dto.DottoreResponseDTO;
import it.prova.gestionetriage.exceptions.DottoreNotFoundException;
import it.prova.gestionetriage.model.Dottore;
import it.prova.gestionetriage.service.DottoreService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/dottore", produces = { MediaType.APPLICATION_JSON_VALUE })
public class DottoreRestController {

	@Autowired
	DottoreService dottoreService;

	@Autowired
	WebClient webClient;

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
	@ResponseStatus(HttpStatus.CREATED)
	public DottoreDTO createNew(@RequestBody DottoreDTO dottoreInput) {

		if (dottoreInput.getId() != null)
			throw new RuntimeException("Non è ammesso fornire un id per la creazione");

		// prima di salvarlo devo verificare se la banca dati esterna lo censisce
		ResponseEntity<DottoreResponseDTO> response = webClient.post().uri("")
				.body(Mono.just(new DottoreRequestDTO(dottoreInput.getNome(), dottoreInput.getCognome(),
						dottoreInput.getCodiceDipendente())), DottoreRequestDTO.class)
				.retrieve().toEntity(DottoreResponseDTO.class).block();

		if (response.getStatusCode() != HttpStatus.CREATED)
			throw new RuntimeException("Errore nella creazione della nuova voce tramite api esterna!!!");

		Dottore dottoreInserito = dottoreService.inserisciNuovo(dottoreInput.buildDottoreModel());
		return DottoreDTO.buildDottoreDTOFromModel(dottoreInserito);
	}

	@PutMapping("/{id}")
	public Dottore updateDottore(@RequestBody Dottore dottoreInput, @PathVariable Long id) {

		Dottore dottoreToUpdate = dottoreService.get(id);

		if (dottoreToUpdate == null)
			throw new DottoreNotFoundException("Dottore non presente");

		ResponseEntity<DottoreResponseDTO> response = webClient.post().uri("")
				.body(Mono.just(new DottoreRequestDTO(dottoreInput.getId(), dottoreInput.getNome(),
						dottoreInput.getCognome(), dottoreInput.getCodiceDipendente())), DottoreRequestDTO.class)
				.retrieve().toEntity(DottoreResponseDTO.class).block();

		if (response.getStatusCode() != HttpStatus.OK)
			throw new RuntimeException("Errore nella modifica della voce tramite api esterna!!!");

		dottoreToUpdate.setCodiceDipendente(dottoreInput.getCodiceDipendente());
		dottoreToUpdate.setNome(dottoreInput.getNome());
		dottoreToUpdate.setCognome(dottoreInput.getCognome());
		dottoreToUpdate.setPazienteAttualmenteInVisita(dottoreInput.getPazienteAttualmenteInVisita());
		return dottoreService.save(dottoreToUpdate);
	}

	@DeleteMapping("/{id}")
	public void deleteDottore(@PathVariable(required = true) Long id) {

		ResponseEntity<DottoreResponseDTO> response = webClient.post().uri("")
				.body(Mono.just(new DottoreRequestDTO(id)), DottoreRequestDTO.class).retrieve()
				.toEntity(DottoreResponseDTO.class).block();

		if (response.getStatusCode() != HttpStatus.OK)
			throw new RuntimeException("Errore nella cancellazione della voce tramite api esterna!!!");

		dottoreService.delete(dottoreService.get(id));
	}
}
