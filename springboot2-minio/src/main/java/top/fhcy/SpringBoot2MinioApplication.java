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
public class SpringBoot2MinioApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoot2MinioApplication.class, args);
    }

}
