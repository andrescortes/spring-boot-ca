package co.com.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.util.List;

@Slf4j
@SpringBootApplication
@ConfigurationPropertiesScan
public class MainApplication implements CommandLineRunner {

    @Value("${cors.allowed-origins}")
    private List<String> allowOrigins;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("args = {}", (Object) args);
        log.info("allowOrigins = {}", allowOrigins);
    }
}
