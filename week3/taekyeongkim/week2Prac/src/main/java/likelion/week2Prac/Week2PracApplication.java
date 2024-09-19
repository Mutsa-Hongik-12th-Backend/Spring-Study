package likelion.week2Prac;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Week2PracApplication {

	public static void main(String[] args) {
		SpringApplication.run(Week2PracApplication.class, args);
	}

}
