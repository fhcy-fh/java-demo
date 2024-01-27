package top.fhcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication(scanBasePackages = "top.fhcy")
public class Springboot2SecurityJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2SecurityJwtApplication.class, args);
    }

}
