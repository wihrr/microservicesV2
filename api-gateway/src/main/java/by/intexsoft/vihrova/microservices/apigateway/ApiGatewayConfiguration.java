package by.intexsoft.vihrova.microservices.apigateway;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ApiGatewayConfiguration {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/get")
                        .filters(f -> f
                                .addRequestHeader("MyHeader", "MyURI")
                                .addRequestParameter("Param", "MyParam"))
                        .uri("http://httpbin.org:80"))
                .route(p -> p.path("/currency-exchange/**")        //http://localhost:8765/currency-exchange/from/USD/to/INR
                        .uri("lb://currency-exchange-service"))
                .route(p -> p.path("/currency-converter/**")       //http://localhost:8765/currency-converter/from/USD/to/INR/quantity/10
                        .uri("lb://currency-conversion-service"))
                .route(p -> p.path("/currency-converter-feign/**") //http://localhost:8765/currency-converter-feign/from/USD/to/INR/quantity/10
                        .uri("lb://currency-conversion-service"))
                .route(p -> p.path("/currency-converter-new/**")   //http://localhost:8765/currency-converter-new/from/USD/to/INR/quantity/10
                        .filters(f -> f.rewritePath(
                                "/currency-converter-new/(?<segment>.*)",
                                "/currency-converter-feign/${segment}"))
                        .uri("lb://currency-conversion-service"))
                .build();
    }
}
