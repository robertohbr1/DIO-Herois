package one.digitalinnovation.heroesapi.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import one.digitalinnovation.heroesapi.document.Heroes;


public interface HeroesRepository extends MongoRepository<Heroes, String> {

}
