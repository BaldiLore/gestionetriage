package it.prova.gestionetriage.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import it.prova.gestionetriage.dto.DottoreRequestDTO;
import it.prova.gestionetriage.dto.DottoreResponseDTO;
import it.prova.gestionetriage.exceptions.AssegnazioneNonAvvenutaException;
import it.prova.gestionetriage.exceptions.DottoreNonDisponibileException;
import it.prova.gestionetriage.exceptions.DottoreNotFoundException;
import it.prova.gestionetriage.exceptions.PazienteNotFoundException;
import it.prova.gestionetriage.model.Dottore;
import it.prova.gestionetriage.model.Paziente;
import it.prova.gestionetriage.service.DottoreService;
import it.prova.gestionetriage.service.PazienteService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/assegnaPaziente", produces = { MediaType.APPLICATION_JSON_VALUE })
public class AssegnaPazienteController {

	@Autowired
	private PazienteService pazienteService;
	
	@Autowired
	private DottoreService dottoreService;
	
	@Autowired
	WebClient webClient;
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public void assegnaPaziente(@RequestParam String codicePaziente,
			@RequestParam String codiceDottore) {

		Paziente paziente = pazienteService.findByCodiceFiscale(codicePaziente);
		Dottore dottore = dottoreService.findByCodice(codiceDottore);
		
		if(dottore == null)
			throw new PazienteNotFoundException("Paziente non presente");
		
		if(paziente == null)
			throw new DottoreNotFoundException("Dottore non presente");
		
		ResponseEntity<DottoreResponseDTO> response = webClient.get()
				.uri(uriBuilder -> uriBuilder
					    .path("/verifica/{codiceDipendente}")
					    .build(codiceDottore))
				.retrieve().toEntity(DottoreResponseDTO.class).block();
		
		DottoreResponseDTO dottoreRicevuto = response.getBody();
		
		if(!dottoreRicevuto.isInServizio() || dottoreRicevuto.isInVisita())
			throw new DottoreNonDisponibileException("Il dottore non è disponibile");

		ResponseEntity<DottoreResponseDTO> responseImpostazione = webClient.post().uri("/impostaInVisita")
				.body(Mono.just(new DottoreRequestDTO(dottore.getCodiceDipendente())), DottoreRequestDTO.class)
				.retrieve().toEntity(DottoreResponseDTO.class).block();
		
		if(responseImpostazione.getStatusCode() != HttpStatus.OK)
			throw new AssegnazioneNonAvvenutaException("L'assegnazione non è avvenuta correttamente");
		
		paziente.setDottore(dottore);
		dottore.setPazienteAttualmenteInVisita(paziente);
		pazienteService.save(paziente);
		dottoreService.save(dottore);
		
		return;
	}
}
