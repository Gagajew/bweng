package at.technikum.springrestbackend;

import at.technikum.springrestbackend.security.jwt.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SpringRestBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestBackendApplication.class, args);
    }

}
