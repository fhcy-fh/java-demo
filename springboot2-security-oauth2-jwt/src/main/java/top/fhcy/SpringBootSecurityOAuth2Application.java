package top.fhcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "top.fhcy")
public class SpringBootSecurityOAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootSecurityOAuth2Application.class, args);
    }

}
