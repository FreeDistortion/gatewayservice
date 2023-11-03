package com.example.gatewayservice.filter;

import lombok.Data;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class OrderTestFilter extends AbstractGatewayFilterFactory<OrderTestFilter.Config> {

    public OrderTestFilter() {
        super(Config.class);
    }

    // 설정 내용을 정의 및 변경하려면, 이를 적용할 수 있도록 내부에 config를 추가할 inner class를 정의
    @Data
    public static class Config {

    }

    @Override
    public GatewayFilter apply(Config config) {
        GatewayFilter filter = new OrderedGatewayFilter(
                (exchange, chain) -> {
                    System.out.println("=======================OrderTest pre-filter=======================");
                    return chain.filter(exchange).then(
                            Mono.fromRunnable(
                                    () -> System.out.println("=======================OrderTest post-filter=======================")
                            )
                    );
                },
                // 우선순위 상수
                Ordered.LOWEST_PRECEDENCE
        );
        return filter;
    }
}


// filter를 통해 구현하고 싶은 기능을 정의하는 method
//    @Override
//    public GatewayFilter apply(Config config) {
//        return new GatewayFilter() {
//            @Override
//            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//
//                // GatewayFilterChain object의 filter() 호출 전에 정의된 내용이 pre-filter로 감.
//                // 요청이 service로 가기 전에 실행할 내용
//                ServerHttpRequest request = exchange.getRequest();
//                ServerHttpResponse response = exchange.getResponse();
//                HttpHeaders headers = request.getHeaders();
//
//                System.out.println(headers.containsKey("firstreq"));
//
//                // post-filter
//                // Mono object는 async로 filter를 정의할 때 단일 값을 전달하기 위해서 만들어짐.
//                // chain.filter()
//                // exchange는 또 다른 필터가 정의되어 있는 경우, 다른 필터가 정상 실행되도록 처리할 떄 사용.
//                /* 이 부분에서 .then을 하지 않으면 중간 필터의 역할 */
//                Mono<Void> filter = chain.filter(exchange);
//
//                // fromRunnable로 만드는 Mono는 async이므로 filter를 정의할 때 단일 값을 전달하기 위해 만들어짐
//                Mono<Object> then = filter.then(Mono.fromRunnable(
//
//                        // interface를 상속하는 class를 생성하지 않고 이름이 없는 하위 class를 생성한 후 override(람다랑 같은 식)
//                        new Runnable() {
//                            @Override
//                            public void run() {
//                                System.out.println("사용자정의 post filter::::: "+response.getStatusCode());
//                            }
//                        }
//
//                ));
//
//
//                return filter;
//            }
//        };
//    }
//}
