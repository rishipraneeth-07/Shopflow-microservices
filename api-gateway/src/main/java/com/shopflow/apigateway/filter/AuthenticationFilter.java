package com.shopflow.apigateway.filter;

import com.shopflow.apigateway.security.JwtService;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class AuthenticationFilter implements GlobalFilter, Ordered {

    private final JwtService jwtService;

    public AuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getPath().value();

        if (path.equals("/users/login") ||
                path.equals("/users/register")) {

            return chain.filter(exchange);
        }

        String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ") ) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authorizationHeader.substring(7);
        if(token.isBlank()){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        if(!jwtService.validateToken(token)){
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String role = jwtService.extractRole(token);

        if (path.startsWith("/products") &&
                exchange.getRequest().getMethod().name().equals("POST") &&
                !role.equals("ADMIN")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }

        if (path.startsWith("/users/") &&
                !path.equals("/users/login") &&
                !path.equals("/users/register") &&
                !role.equals("ADMIN")) {

            exchange.getResponse()
                    .setStatusCode(HttpStatus.FORBIDDEN);

            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }



    @Override
    public int getOrder() {
        return -1;
    }
}
