package one.digitalinnovation.heroesapi.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Document(collection = "tb_heroes")
public class Heroes {
	
	@Id
	private String id;
	
	private String name;
	
	private String universe;
	
	private int films;

	public Heroes(String id, String name, String universe, int films) {
		this.id = id;
		this.name = name;
		this.universe = universe;
		this.films = films;
	}

}
