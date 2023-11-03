package com.example.gatewayservice.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class GatewayFilterConfig {

    // RouteLocatorBuilder를 이용해서 route 정보와 filter 정보를 담고 있는 RouteLocator onject를 만들어서 return 하도록 작업.
    // @Bean으로 등록해야 Spring이 관리는 obejct가 됨. 그냥 생성하면 spring 인식 x.
//    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
//                .route(new Function<PredicateSpec, Buildable<Route>>() {
//                    @Override
//                    public Buildable<Route> apply(PredicateSpec predicateSpec) {
//                        return null;
//                    }
//                })
                .route(predicateSpec
                        -> predicateSpec.path("/first/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.addRequestHeader("firstreq","firstrequest")
                                        .addResponseHeader("firstres","firstresponse")
                        )
                        .uri("http://localhost:9101/")
                )

                .route(predicateSpec
                        -> predicateSpec.path("/second/**")
                        .filters(gatewayFilterSpec ->
                                gatewayFilterSpec.addRequestHeader("secondreq","secondrequest")
                                        .addResponseHeader("secondres","secondresponse")
                        )
                        .uri("http://localhost:9102/")
                )
                .build();
    }
}
