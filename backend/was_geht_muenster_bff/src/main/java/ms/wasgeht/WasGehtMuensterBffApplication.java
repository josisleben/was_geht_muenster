package ms.wasgeht;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.io.InputStream;
import java.util.Base64;

@SpringBootApplication
@EnableMongoRepositories
public class WasGehtMuensterBffApplication {

	public static Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@Getter
	private static byte[] defaultAvatar;
	@Getter
	private static byte[] defaultActivity;

	static {
			try (final InputStream inputStream = WasGehtMuensterBffApplication.class.getClassLoader()
					.getResourceAsStream("images/DefaultUser.png")) {
				defaultAvatar = inputStream.readAllBytes();
			} catch (Exception e) {
				defaultAvatar = new byte[0];
			}

			try (final InputStream inputStream = WasGehtMuensterBffApplication.class.getClassLoader()
					.getResourceAsStream("images/DefaultActivity.png")) {
				defaultActivity = inputStream.readAllBytes();
			} catch (Exception e) {
				defaultActivity = new byte[0];
			}
	}


	public static void main(String[] args) {
		SpringApplication.run(WasGehtMuensterBffApplication.class, args);
	}

}
