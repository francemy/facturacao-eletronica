package ao.okayulatech.erp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class OkayulatechErpApplication {

	public static void main(String[] args) {
		SpringApplication.run(OkayulatechErpApplication.class, args);
	}

}
