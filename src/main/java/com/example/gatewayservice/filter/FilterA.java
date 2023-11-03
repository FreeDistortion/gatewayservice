package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class FilterA extends AbstractGatewayFilterFactory<FilterA.Config> {

    public static class Config{}
    public FilterA() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
//            System.out.println(headers.containsKey("data"));
            System.out.println("PRE-AAAAAAAAAAAAAAAAAAAAAAAA");
            return chain.filter(exchange).then(Mono.fromRunnable(
                    () -> System.out.println("POST-AAAAAAAAAAAAAAAAAAAAAAAA")
            ));
        };
    }
}
