package com.omniesoft.commerce.gateway;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.DefaultRateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.RateLimitKeyGenerator;
import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.properties.RateLimitProperties;
import io.undertow.util.Headers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.util.StringJoiner;

@Configuration
public class RateLimiterConfig {
    @Bean
    public RateLimitKeyGenerator rateLimitKeyGenerator(final RateLimitProperties properties) {
        return new OverrideRateLimitRateLimitKeyGenerator(properties);
    }

    @Slf4j
    static class OverrideRateLimitRateLimitKeyGenerator extends DefaultRateLimitKeyGenerator {

        private final String defaultUser = "OMNIE_USER";
        private final String salt = BCrypt.gensalt();

        public OverrideRateLimitRateLimitKeyGenerator(RateLimitProperties properties) {
            super(properties);
        }

        @Override
        public String key(HttpServletRequest request, Route route, RateLimitProperties.Policy policy) {

            final StringJoiner joiner = new StringJoiner("_AND_");
            joiner.add(extractPrincipalName(request));
            joiner.add(extractRouteId(route));
            joiner.add(extractURI(request));
            joiner.add(extractRemoteHost(request));
            return joiner.toString();
        }

        private String extractPrincipalName(HttpServletRequest request) {
            String header = request.getHeader(Headers.AUTHORIZATION.toString());
            if (header != null && !header.isEmpty() && header.length() > 6) {
//                Map<String, String[]> parameterMap = request.getParameterMap();
//                if (parameterMap != null) {
//                    String[] usernames = parameterMap.get("grant_type");
//                    String username = null;
//                    if (usernames != null && usernames.length >= 1)
//                        username = usernames[0];
//                    if (username != null && !username.isEmpty() && username.equalsIgnoreCase("client_credentials")) {
//                        return RandomStringUtils.randomAlphabetic(1);
//                    }
//                }
                return defaultUser.concat(header.substring(6, header.length() > 15 ? 15 : header.length()));
            }
            return "ANONYMOUS";
        }

        private String extractURI(HttpServletRequest request) {
            return request.getRequestURI();
        }

        private String extractRouteId(Route route) {
            return route.getId();
        }

        private String extractRemoteHost(HttpServletRequest request) {

            return request.getRemoteHost();
        }
    }
}
