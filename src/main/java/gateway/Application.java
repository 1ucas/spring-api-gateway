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
    
    @RequestMapping("/")
    public Mono<String> home() {
        return Mono.just("<body>\r\n" + 
        		"    <h1>Spring Cloud Gateway</h1>\r\n" + 
        		"	<h2>Exemplo de uso do Spring Gateway para o Trabalho do curso de Arquitetura de Backend - Puc Minas 10/2018</h2>\r\n" + 
        		"	<h4>Integrantes:</h4>\r\n" + 
        		"	<ul>\r\n" + 
        		"        <li>Lucas Maciel</li>\r\n" + 
        		"        <li>Pedro Eustáquio</li>\r\n" + 
        		"        <li>Raphael Fernandes</li>\r\n" + 
        		"        <li>Wiliam Walder</li>\r\n" + 
        		"	</ul>\r\n" + 
        		"    <h4>Objetivo:</h4>\r\n" + 
        		"    <div>O objetivo deste gateway é prover o roteamento das APIs de Livraria, autenticação, auditoria e pagamento (mock)</div>\r\n" + 
        		"	<h4>Onde está o código:</h4>\r\n" + 
        		"    <div>O código para este gateway pode ser encontrado no branch <strong>master</strong> do repositório do Github: https://github.com/1ucas/spring-api-gateway</div>\r\n" + 
        		"</body>");
    }
    
    @RequestMapping("/fallback")
    public Mono<String> fallback() {
        return Mono.just("fallback");
    }

}
