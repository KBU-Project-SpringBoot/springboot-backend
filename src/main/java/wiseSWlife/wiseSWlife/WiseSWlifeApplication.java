package wiseSWlife.wiseSWlife;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WiseSWlifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(WiseSWlifeApplication.class, args);
	}

}
