package com.zentra.zentra_flow.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.Filter;

@Component
public class CorrelationIdFilter implements Filter {

    private static final String TRACE_ID_KEY = "traceId";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        try {

            String traceId = httpRequest.getHeader("X-Trace-Id");

            if (traceId == null || traceId.isEmpty()) {
                traceId = UUID.randomUUID().toString().substring(0, 8);
            }

            MDC.put(TRACE_ID_KEY, traceId);

            chain.doFilter(request, response);

        } finally {

            MDC.clear();
        }
    }
}
