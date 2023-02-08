package com.showc.book.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;


@Component
public class CustomOPRFilter extends OncePerRequestFilter {
    private final TokenHandler tokenHandler;
    private final List<String> reqList = List.of(
            "/bookapp/v1/login",
            "/bookapp/v1/getallbook",
            "/bookapp/v1/getbook/**"
    );

    public CustomOPRFilter(TokenHandler tokenHandler) {
        this.tokenHandler = tokenHandler;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws  ServletException {
        String reqUrl = request.getRequestURI();
        AntPathMatcher matcher = new AntPathMatcher();
        return reqList.stream().anyMatch(
                p -> matcher.match(p, reqUrl)
        );
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        final String requestToken = request.getHeader("admintoken");
        String userName = tokenHandler.isTokenExists(requestToken);
        if(userName != null){
            System.out.println(userName + " succesful request on the following endpoint: " + request.getRequestURI());
        }
        filterChain.doFilter(request, response);
    }
}
