package com.zpp.springboot.filter;

import org.apache.commons.io.input.TeeInputStream;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.io.output.TeeOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mock.web.DelegatingServletInputStream;
import org.springframework.mock.web.DelegatingServletOutputStream;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Enumeration;
import java.util.stream.Collectors;

/**
 * @author pingpingZhong
 *         Date: 2018/6/7
 *         Time: 16:48
 */
public class HttpLogFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(HttpLogFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ByteArrayOutputStream requestCopy = new ByteArrayOutputStream();
        ByteArrayOutputStream responseCopy = new ByteArrayOutputStream();
        HttpServletRequest requestWrapper = new HttpServletRequestWrapper((HttpServletRequest) request) {
            @Override
            public ServletInputStream getInputStream() throws IOException {
                return new DelegatingServletInputStream(new TeeInputStream(request.getInputStream(), requestCopy));
            }
        };
        HttpServletResponse responseWrapper = new HttpServletResponseWrapper((HttpServletResponse) response) {
            @Override
            public ServletOutputStream getOutputStream() throws IOException {
                return new DelegatingServletOutputStream(new TeeOutputStream(response.getOutputStream(), responseCopy));
            }
        };

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;


            httpServletRequest.getAttributeNames();
            try {
                Enumeration<String> enumeration1 = requestWrapper.getHeaderNames();
                StringBuilder stringBuilder = new StringBuilder("[");
                while (enumeration1.hasMoreElements()) {
                    String name = enumeration1.nextElement();
                    stringBuilder.append(String.format("%s: [%s],", name, requestWrapper.getHeader(name)));
                }
                stringBuilder.append("]");
                logger.info(String.format("server request： %s %s %s %s", (httpServletRequest.getRequestURL()),
                        requestWrapper.getMethod(), stringBuilder, requestCopy.toString("UTF-8")
                ));

                logger.info(String.format(
                        "server response: %d %s %s",
                        responseWrapper.getStatus(),
                        responseWrapper.getHeaderNames().stream()
                                .map(name -> String.format("%s: %s", name, responseWrapper.getHeaders(name)))
                                .collect(Collectors.toList()),
                        responseCopy.toString("UTF-8")
                ));
            } catch (Exception e) {
                logger.error("log response error", e);
            }
        }
    }
}
