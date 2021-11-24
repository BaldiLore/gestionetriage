package it.prova.gestionetriage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import it.prova.gestionetriage.model.Dottore;
import it.prova.gestionetriage.service.DottoreService;

@SpringBootApplication
public class GestionetriageApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionetriageApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner initDottori(DottoreService dottoreService) {
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

		};
	}

}
