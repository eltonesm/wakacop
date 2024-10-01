package academy.wakanda.wakacop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@SpringBootApplication
public class WakacopApplication {

	@GetMapping
	public String getTest(){
		return "test ok!";
	}
	public static void main(String[] args) {
		SpringApplication.run(WakacopApplication.class, args);
	}

}
