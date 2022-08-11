package kong.tues;

import kong.tues.commons.session.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication
public class TuesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TuesApplication.class, args);
	}
}
