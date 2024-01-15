package pizzaapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableJpaAuditing

@OpenAPIDefinition(
        info = @Info(
                title = "Pizza api project",
                version = "1.0.0",
                description = "this project is only for learning",
                termsOfService = "https://github.com/danigaru",
                contact = @Contact(
                        name = "Danilo García",
                        email = "https://github.com/danigaru"
                ),
                license = @License(
                        name = "Licence",
                        url = "https://github.com/danigaru"
                )
        )
)

public class PizzaApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(PizzaApiApplication.class, args);
    }

}
