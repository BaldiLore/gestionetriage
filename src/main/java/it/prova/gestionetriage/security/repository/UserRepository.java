package it.prova.gestionetriage.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import it.prova.gestionetriage.model.User;


public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User>{
	Optional<User> findByUsername(String username);

}