package gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route(p -> p
            	.path("/autenticacao/**")
                .filters(f -> f.stripPrefix(1))
                .uri("https://apilivrariaauth20181002115700.azurewebsites.net"))
            .route(p -> p
                	.path("/usuarios/**")
                    .filters(f -> f.stripPrefix(1))
                    .uri("https://apilivrariaauth20181002115700.azurewebsites.net/api/v1/User"))
            .route(p -> p
                	.path("/pagamentos/**")
                    .filters(f -> f.stripPrefix(1))
                    .uri("http://ms-payment.herokuapp.com/v1/private/pagamentos"))
            .route(p -> p
                	.path("/cartoes/**")
                    .filters(f -> f.stripPrefix(1))
                    .uri("http://ms-payment.herokuapp.com/v1/private/cartoes"))
            .route(p -> p
                	.path("/auditoria/**")
                    .filters(f -> f.stripPrefix(1))
                    .uri("https://auditoria20181002095244.azurewebsites.net/api/v1/Auditoria"))
            .build();
    }
    
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}
