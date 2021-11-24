package it.prova.gestionetriage.security.controller;

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

import it.prova.gestionetriage.exceptions.UserNotFoundException;
import it.prova.gestionetriage.model.StatoUtente;
import it.prova.gestionetriage.model.User;
import it.prova.gestionetriage.service.UserService;

@RestController
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE })
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping("/{idInput}")
	public User getUser(@PathVariable(required = true) Long idInput) {
		return userService.get(idInput);
	}

	@GetMapping
	public List<User> getAll() {
		return userService.listAll();
	}

	@PostMapping("/search")
	public ResponseEntity<Page<User>> searchAndPagination(@RequestBody User userExample,
			@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "id") String sortBy) {

		Page<User> results = userService.searchAndPaginate(userExample, pageNo, pageSize, sortBy);

		return new ResponseEntity<Page<User>>(results, new HttpHeaders(), HttpStatus.OK);
	}

	@PostMapping
	public User createNewUser(@RequestBody User userInput) {
		userInput.setStatoUtente(StatoUtente.CREATO);
		return userService.save(userInput);
	}

	@PutMapping("/{id}")
	public User updateUser(@RequestBody User userInput, @PathVariable Long id) {

		User userToUpdate = userService.get(id);

		if (userToUpdate == null)
			throw new UserNotFoundException("User non presente");

		userToUpdate.setEmail(userInput.getEmail());
		userToUpdate.setStatoUtente(userInput.getStatoUtente());
		userToUpdate.setEnabled(userInput.getEnabled());
		userToUpdate.setUsername(userInput.getUsername());
		userToUpdate.setAuthorities(userInput.getAuthorities());
		return userService.save(userToUpdate);
	}

	@DeleteMapping("/{id}")
	public void deleteUser(@PathVariable(required = true) Long id) {

		User user = userService.get(id);
		user.setStatoUtente(StatoUtente.DISABILITATO);
		userService.save(user);
	}

}
