package gateway.filter;

import gateway.Util.JwtUtil;
import gateway.exception.MissingAuthorizationHeaderException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            try {
                if (routeValidator.isSecured.test(exchange.getRequest())) {
                    //header contains token or not
                    if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        throw new MissingAuthorizationHeaderException("missing authorization header");
                    }

                    String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        authHeader = authHeader.substring(7);
                    }

                    jwtUtil.validateToken(authHeader);
                }
                return chain.filter(exchange);
            } catch (MissingAuthorizationHeaderException e) {
                return handleAuthorizationException(exchange, e);
            } catch (ExpiredJwtException e) {
                return handleAuthorizationException(exchange, e);
            } catch (JwtException e) {
                return handleAuthorizationException(exchange, e);
            }
        });
    }

    private Mono<Void> handleAuthorizationException(ServerWebExchange exchange, Exception exception) {
        // Create a custom response
        String errorMessage = exception.getMessage();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        byte[] responseBytes = errorMessage.getBytes(StandardCharsets.UTF_8);

        // Set the response status and body
        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders().setContentLength(responseBytes.length);
        exchange.getResponse().getHeaders().setContentType(MediaType.TEXT_PLAIN);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(responseBytes);

        // Return the response
        return exchange.getResponse().writeWith(Mono.just(buffer));
    }

    public static class Config {

    }
}
