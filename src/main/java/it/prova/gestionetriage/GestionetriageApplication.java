package it.prova.gestionetriage;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.prova.gestionetriage.model.Dottore;
import it.prova.gestionetriage.model.Paziente;
import it.prova.gestionetriage.model.StatoPaziente;
import it.prova.gestionetriage.model.StatoUtente;
import it.prova.gestionetriage.service.DottoreService;
import it.prova.gestionetriage.service.PazienteService;
import it.prova.gestionetriage.security.repository.AuthorityRepository;
import it.prova.gestionetriage.security.repository.UserRepository;
import it.prova.gestionetriage.model.Authority;
import it.prova.gestionetriage.model.AuthorityName;
import it.prova.gestionetriage.model.User;

@SpringBootApplication
public class GestionetriageApplication {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	AuthorityRepository authorityRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(GestionetriageApplication.class, args);
	}

	@Bean
	public CommandLineRunner initDottori(DottoreService dottoreService, PazienteService pazienteService) {
		return (args) -> {

			// inizializzo il Db
			dottoreService.save(new Dottore("Paolo", "Brosio", "PB53628"));
			dottoreService.save(new Dottore("Nicola", "Golia", "NG93925"));
			dottoreService.save(new Dottore("Ajeje", "Brazorf", "AB67293"));
			dottoreService.save(new Dottore("Mario", "Rossi", "MR64873"));
			dottoreService.save(new Dottore("Maria", "Bianchi", "MB67721"));

			// verifico inserimento
			System.out.println("Elenco Dottori");
			dottoreService.listAll().forEach(item -> {
				System.out.println(item);
			});
			
			
			pazienteService.save(new Paziente("Pippo", "Baudo", "PPPBBB09F23F023H", new Date(), StatoPaziente.IN_ATTESA_VISITA));
			pazienteService.save(new Paziente("Gigi", "Proietti", "PHMGBB09F23F023H", new Date(), StatoPaziente.IN_ATTESA_VISITA));
			pazienteService.save(new Paziente("Ugo", "Fantozzi", "PPKHJBBB09F23F023H", new Date(), StatoPaziente.IN_ATTESA_VISITA));
			pazienteService.save(new Paziente("Giancarlo", "Rossi", "PEPBBB09F23F023H", new Date(), StatoPaziente.IN_ATTESA_VISITA));
			

			// Ora la parte di sicurezza
			User user = userRepository.findByUsername("admin").orElse(null);

			if (user == null) {

				/**
				 * Inizializzo i dati del mio test
				 */

				Authority authorityAdmin = new Authority();
				authorityAdmin.setName(AuthorityName.ROLE_ADMIN);
				authorityAdmin = authorityRepository.save(authorityAdmin);

				Authority authorityUser = new Authority();
				authorityUser.setName(AuthorityName.ROLE_SUB_OPERATOR);
				authorityUser = authorityRepository.save(authorityUser);

				List<Authority> authorities = Arrays.asList(new Authority[] { authorityAdmin, authorityUser });

				user = new User();
				user.setAuthorities(authorities);
				user.setEnabled(true);
				user.setUsername("admin");
				user.setPassword(passwordEncoder.encode("admin"));
				user.setEmail("admin@example.com");
				user.setStatoUtente(StatoUtente.ATTIVO);

				user = userRepository.save(user);

			}

			User commonUser = userRepository.findByUsername("commonUser").orElse(null);

			if (commonUser == null) {

				/**
				 * Inizializzo i dati del mio test
				 */

				Authority authorityUser = authorityRepository.findByName(AuthorityName.ROLE_SUB_OPERATOR);
				if (authorityUser == null) {
					authorityUser = new Authority();
					authorityUser.setName(AuthorityName.ROLE_SUB_OPERATOR);
					authorityUser = authorityRepository.save(authorityUser);
				}

				List<Authority> authorities = Arrays.asList(new Authority[] { authorityUser });

				commonUser = new User();
				commonUser.setAuthorities(authorities);
				commonUser.setEnabled(true);
				commonUser.setUsername("operator");
				commonUser.setPassword(passwordEncoder.encode("operator"));
				commonUser.setEmail("operatore@example.com");
				commonUser.setStatoUtente(StatoUtente.ATTIVO);

				commonUser = userRepository.save(commonUser);

			}

		};
	}

}
