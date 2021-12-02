package one.digitalinnovation.heroesapi.service;

import org.springframework.stereotype.Service;

import one.digitalinnovation.heroesapi.document.Heroes;
import one.digitalinnovation.heroesapi.repository.HeroesRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class HeroesService {
	
	private final HeroesRepository heroesRepository;

	public HeroesService(HeroesRepository heroesRepository) {
		this.heroesRepository = heroesRepository;
	}
	
	// Flux: representa um conjunto de informações.
	public Flux<Heroes> findAll() {
		return Flux.fromIterable(this.heroesRepository.findAll());
	}
	
	// Mono: representa um único dado.
	public  Mono<Heroes> findByIdHero(String id){
		return  Mono.justOrEmpty(this.heroesRepository.findById(id));
	}
	
	// Salvar heróis
	public Mono<Heroes> save(Heroes heroes){
		return  Mono.justOrEmpty(this.heroesRepository.save(heroes));
	}
	
	// Atualizar dados do herói
	public Mono<Heroes> update(Heroes heroes, String id) {
		Heroes heroeFound = findByIdHero(id).block();
		
		if (heroes.getFilms() > 0)
			heroeFound.setFilms(heroes.getFilms());
		
		if (!heroes.getName().isEmpty())
			heroeFound.setName(heroes.getName());
		
		if (!heroes.getUniverse().isEmpty())
			heroeFound.setUniverse(heroes.getUniverse());
		
		return Mono.justOrEmpty(this.heroesRepository.save(heroeFound));
	}

	// Excluir um registro de herói
	public Mono<Boolean> deletebyIDHero(String id) {
		if (!this.heroesRepository.findById(id).isEmpty()) {
			heroesRepository.deleteById(id);
			return Mono.just(true);
		} else {
			return Mono.just(false);
		}		
	}

}
