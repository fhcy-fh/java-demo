package top.fhcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author fh
 * @date 2024/1/20
 */
@SpringBootApplication(scanBasePackages = "top.fhcy")
public class SpringBoot2SecurityOAuth2Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2SecurityOAuth2Application.class, args);
    }

}
