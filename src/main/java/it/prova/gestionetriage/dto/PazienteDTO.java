package it.prova.gestionetriage.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import it.prova.gestionetriage.model.Paziente;
import it.prova.gestionetriage.model.StatoPaziente;

public class PazienteDTO {

	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataRegistrazione;
	private StatoPaziente statoPaziente;
	@JsonIgnoreProperties(value = { "pazienteAttualmenteInVisita" })
	private DottoreDTO dottore;

	public PazienteDTO() {
		super();
	}

	public PazienteDTO(String nome, String cognome, String codiceFiscale, Date dataRegistrazione,
			StatoPaziente statoPaziente, DottoreDTO dottore) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataRegistrazione = dataRegistrazione;
		this.statoPaziente = statoPaziente;
		this.dottore = dottore;
	}

	public PazienteDTO(Long id, String nome, String cognome, String codiceFiscale, Date dataRegistrazione,
			StatoPaziente statoPaziente, DottoreDTO dottore) {
		super();
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataRegistrazione = dataRegistrazione;
		this.statoPaziente = statoPaziente;
		this.dottore = dottore;
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

	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public Date getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(Date dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}

	public StatoPaziente getStatoPaziente() {
		return statoPaziente;
	}

	public void setStatoPaziente(StatoPaziente statoPaziente) {
		this.statoPaziente = statoPaziente;
	}

	public DottoreDTO getDottore() {
		return dottore;
	}

	public void setDottore(DottoreDTO dottore) {
		this.dottore = dottore;
	}

	@Override
	public String toString() {
		return "PazienteDTO [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", dataRegistrazione=" + dataRegistrazione + ", statoPaziente=" + statoPaziente + "]";
	}

	public Paziente buildPazienteModel() {
		
		if(this.dottore.buildDottoreModel() == null)
			return new Paziente(this.id, this.nome, this.cognome, this.codiceFiscale, this.dataRegistrazione,
					this.statoPaziente, null);
		
		return new Paziente(this.id, this.nome, this.cognome, this.codiceFiscale, this.dataRegistrazione,
				this.statoPaziente, this.dottore.buildDottoreModel());
	}

	public static PazienteDTO buildPazienteDTOFromModel(Paziente input) {
		
		if(input.getDottore() == null)
			return new PazienteDTO(input.getId(), input.getNome(), input.getCognome(), input.getCodiceFiscale(),
					input.getDataRegistrazione(), input.getStatoPaziente(),
					null);
		
		return new PazienteDTO(input.getId(), input.getNome(), input.getCognome(), input.getCodiceFiscale(),
				input.getDataRegistrazione(), input.getStatoPaziente(),
				DottoreDTO.buildDottoreDTOFromModel(input.getDottore()));
	}

}
