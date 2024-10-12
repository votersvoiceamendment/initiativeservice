package com.vva.initiativeservice.logging;


import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

public class RequestLoggingFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        chain.doFilter(wrappedRequest, wrappedResponse);

        // Log the request body after it's cached
        byte[] requestArray = wrappedRequest.getContentAsByteArray();
        if (requestArray.length > 0) {
            String requestBody = new String(requestArray, wrappedRequest.getCharacterEncoding());
//            System.out.println("Request Body: " + requestBody);
        }

        // Log the response if needed
        wrappedResponse.copyBodyToResponse();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Optional initialization
    }

    @Override
    public void destroy() {
        // Optional cleanup
    }
}
