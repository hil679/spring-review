package com.example.springintermediate.filter;

import com.example.springintermediate.entity.User;
import com.example.springintermediate.jwt.JwtUtil;
import com.example.springintermediate.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@Component
@Order(2)
public class AuthFilter implements Filter {
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    AuthFilter(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        String uri = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(uri) &&
                (uri.startsWith("/api/create-jwt") || uri.startsWith("/css") || uri.startsWith("/js"))) {
            chain.doFilter(req, res);
        } else {
            String tokenValue = jwtUtil.getTokenFromRequest(httpServletRequest);

            if (StringUtils.hasText(tokenValue)) {
                String token = jwtUtil.substringToken(tokenValue);
                if (!jwtUtil.validateToken(token)) {
                    throw new IllegalArgumentException("Token Error");
                }
                Claims info = jwtUtil.getUserInfoFromToken(token);
                User user = userRepository.findByUsername(info.getSubject()).orElseThrow(
                        () -> new NullPointerException("Not Found User")
                );

                req.setAttribute("user", user);
                chain.doFilter(req, res); //다음 Filter로 이동
            }
            else throw new IllegalArgumentException("Not Found Token");
        }

    }
}
