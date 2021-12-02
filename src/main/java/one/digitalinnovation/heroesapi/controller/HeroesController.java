package one.digitalinnovation.heroesapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import one.digitalinnovation.heroesapi.document.Heroes;
import one.digitalinnovation.heroesapi.repository.HeroesRepository;
import one.digitalinnovation.heroesapi.service.HeroesService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static one.digitalinnovation.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RestController
public class HeroesController {

	HeroesService heroesService;

	HeroesRepository heroesRepository;

	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HeroesController.class);

	public HeroesController(HeroesService heroesService, HeroesRepository heroesRepository) {
		this.heroesService = heroesService;
		this.heroesRepository = heroesRepository;
	}
	
	@GetMapping(HEROES_ENDPOINT_LOCAL)
	@ResponseStatus(HttpStatus.OK)
	public Flux<Heroes> getAllItems() {
		log.info("requesting the list off all heroes");
		return heroesService.findAll();
	}
	
	@GetMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
	public Mono<ResponseEntity<Heroes>> findByIdHero(@PathVariable String id) {
		log.info("Requesting the hero with id {}", id);
		return heroesService.findByIdHero(id)
			.map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
			.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(HEROES_ENDPOINT_LOCAL)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Heroes> createHero(@RequestBody Heroes heroes) {
		log.info("A new Hero was Created");
		return heroesService.save(heroes);
	}
	
	@PutMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<ResponseEntity<Heroes>> updateHero(@PathVariable String id, @RequestBody Heroes heroes) {
		log.info("A new Hero was Updated...");

		return heroesService.update(heroes, id)
				.map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@DeleteMapping(HEROES_ENDPOINT_LOCAL + "/{id}")
	public Mono<ResponseEntity<Void>> deletebyIDHero(@PathVariable String id) {
		Mono<Boolean> deleted = heroesService.deletebyIDHero(id);
		if (deleted.block()) {
			log.info("Deleting the hero with id {}", id);
			return Mono.just(new ResponseEntity<Void>(HttpStatus.OK));
		} else {
			return Mono.just(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
		}
	}
	
}
