package community.gdsc.wanted;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"community.gdsc.wanted.*"})
public class WantedServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(WantedServerApplication.class, args);
    }
}
