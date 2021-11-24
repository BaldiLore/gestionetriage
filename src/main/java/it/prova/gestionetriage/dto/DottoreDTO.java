package it.prova.gestionetriage.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.gestionetriage.model.Dottore;

public class DottoreDTO {

	private Long id;
	private String nome;
	private String cognome;
	private String codiceDipendente;
	@JsonIgnoreProperties(value = { "dottore" })
	private PazienteDTO pazienteAttualmenteInVisita;

	public DottoreDTO() {
		super();
	}

	public DottoreDTO(String nome, String cognome, String codiceDipendente, PazienteDTO pazienteAttualmenteInVisita) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceDipendente = codiceDipendente;
		this.pazienteAttualmenteInVisita = pazienteAttualmenteInVisita;
	}

	public DottoreDTO(Long id, String nome, String cognome, String codiceDipendente,
			PazienteDTO pazienteAttualmenteInVisita) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceDipendente = codiceDipendente;
		this.pazienteAttualmenteInVisita = pazienteAttualmenteInVisita;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCodiceDipendente() {
		return codiceDipendente;
	}

	public void setCodiceDipendente(String codiceDipendente) {
		this.codiceDipendente = codiceDipendente;
	}

	public PazienteDTO getPazienteAttualmenteInVisita() {
		return pazienteAttualmenteInVisita;
	}

	public void setPazienteAttualmenteInVisita(PazienteDTO pazienteAttualmenteInVisita) {
		this.pazienteAttualmenteInVisita = pazienteAttualmenteInVisita;
	}
	
	public Dottore buildDottoreModel() {
		
		if(this.pazienteAttualmenteInVisita == null)
			return new Dottore(this.id, this.nome, this.cognome, this.codiceDipendente,
					null);
		
		return new Dottore(this.id, this.nome, this.cognome, this.codiceDipendente,
				this.pazienteAttualmenteInVisita.buildPazienteModel());
	}

	public static DottoreDTO buildDottoreDTOFromModel(Dottore input) {
		
		if(input.getPazienteAttualmenteInVisita() == null)
			return new DottoreDTO(input.getId(), input.getNome(), input.getCognome(), input.getCodiceDipendente(),
					null);
		
		return new DottoreDTO(input.getId(), input.getNome(), input.getCognome(), input.getCodiceDipendente(),
				PazienteDTO.buildPazienteDTOFromModel(input.getPazienteAttualmenteInVisita()));
	}
}
