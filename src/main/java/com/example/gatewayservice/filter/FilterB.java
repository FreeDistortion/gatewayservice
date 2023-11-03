package com.example.gatewayservice.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class FilterB extends AbstractGatewayFilterFactory<FilterB.Config> {

    public static class Config {
    }

    public FilterB() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String headerValue = headers.getFirst("data");
            System.out.println("PRE-BBBBBBBBBBBBBBBBBBBBB");
            if (headerValue.equals("mydata")) {
//            if (headers.containsKey("data") &
//                    headers.get("data").stream().
//                            filter(e -> e.contains("mydata")).count() > 0L) {

                return chain.filter(exchange)
                        .then(

                        );
            } else {
                return chain.filter(exchange);
            }
        };
    }
}
