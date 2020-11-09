package com.yunmu.uof.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RepeatedlyReadRequestFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest && request.getMethod().equalsIgnoreCase("post") && StringUtils.isNotBlank(request.getContentType()) && request.getContentType().contains("application/json")) {
            requestWrapper = new RepeatedlyReadRequestWrapper(request);
        }
        if (null == requestWrapper) {
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(requestWrapper, response);
        }
    }
}
