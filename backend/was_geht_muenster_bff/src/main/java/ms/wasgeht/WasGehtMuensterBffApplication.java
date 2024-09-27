package ms.wasgeht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class WasGehtMuensterBffApplication {

	public static void main(String[] args) {
		SpringApplication.run(WasGehtMuensterBffApplication.class, args);
	}

}
