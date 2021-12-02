package one.digitalinnovation.heroesapi;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import one.digitalinnovation.heroesapi.document.Heroes;
import one.digitalinnovation.heroesapi.repository.HeroesRepository;
import reactor.core.publisher.Mono;

import static one.digitalinnovation.heroesapi.constants.HeroesConstant.HEROES_ENDPOINT_LOCAL;

@RunWith(SpringRunner.class)
@DirtiesContext
@AutoConfigureWebTestClient
@SpringBootTest
class HeroesapiApplicationTests {
	
	@Autowired
	WebTestClient webTestClient;
	
	@Autowired
	HeroesRepository heroesRepository;
	
	String heroId = "608ddc4f7ad10e0a0c1496e5";
	
	@Order(1)
	@Test
	public void getAllHeroes(){
		webTestClient.get()
			.uri(HEROES_ENDPOINT_LOCAL)
			.exchange()
			.expectStatus().isOk()
			.expectBody();
	}
	
	@Order(2)
	@Test
	public void getOneHeroById(){
		webTestClient.get()
			.uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),"608a19c785d36a24d245c4a8")
			.exchange()
			.expectStatus().isOk()
			.expectBody();
	}
	
	@Order(3)
	@Test
	public void getOneHeroNotFound(){
		webTestClient.get()
			.uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),"123")
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Order(4)
	@Test
	public void insertNewHero(){
		Heroes hero = new Heroes();
		hero.setId(heroId);
		hero.setName("Thor");
		hero.setUniverse("Marvel");
		hero.setFilms(4);
		
		webTestClient.post()
			.uri(HEROES_ENDPOINT_LOCAL)
			.contentType(MediaType.APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(hero), Heroes.class)
			.exchange()
			.expectStatus().isCreated()
			.expectBody()
			.jsonPath("$.name").isNotEmpty();
	}
	
	@Order(5)
	@Test
	public void deleteHero(){
		webTestClient.delete()
			.uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),heroId)
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Void.class);
	}
	
	@Order(6)
	@Test
	public void deleteHeroNotFound(){
		webTestClient.delete()
			.uri(HEROES_ENDPOINT_LOCAL.concat("/{id}"),"123")
			.accept(MediaType.APPLICATION_JSON)
			.exchange()
			.expectStatus().isNotFound()
			.expectBody(Void.class);
	}

}
