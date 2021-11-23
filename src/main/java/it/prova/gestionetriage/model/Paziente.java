package it.prova.gestionetriage.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Paziente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String cognome;
	private String codiceFiscale;
	private Date dataRegistrazione;
	private StatoPaziente statoPaziente;
	private Dottore dottore;

	public Paziente() {
		super();
	}

	public Paziente(String nome, String cognome, String codiceFiscale, Date dataRegistrazione,
			StatoPaziente statoPaziente) {
		super();
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.dataRegistrazione = dataRegistrazione;
		this.statoPaziente = statoPaziente;
	}

	public Paziente(Long id, String nome, String cognome, String codiceFiscale, Date dataRegistrazione,
			StatoPaziente statoPaziente, Dottore dottore) {
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

	public Dottore getDottore() {
		return dottore;
	}

	public void setDottore(Dottore dottore) {
		this.dottore = dottore;
	}

	@Override
	public String toString() {
		return "Paziente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", codiceFiscale=" + codiceFiscale
				+ ", dataRegistrazione=" + dataRegistrazione + "]";
	}

}
