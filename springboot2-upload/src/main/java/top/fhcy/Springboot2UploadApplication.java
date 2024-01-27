package top.fhcy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author fh
 * @date 2024/1/19
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = "top.fhcy")
public class Springboot2UploadApplication {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2UploadApplication.class, args);
    }

}
