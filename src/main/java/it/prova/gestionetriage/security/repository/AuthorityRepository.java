package it.prova.gestionetriage.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.prova.gestionetriage.model.Authority;
import it.prova.gestionetriage.model.AuthorityName;


public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(AuthorityName name);

}