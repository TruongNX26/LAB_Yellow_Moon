package edu.fpt.yellowmoon.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.invoke.MethodHandles;

@WebFilter("/*")
public class MainFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass().getSimpleName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        setEncoding(request, response);

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String uri = httpRequest.getRequestURI();
        if(checkResourcePath(uri)) {
            chain.doFilter(request, response);
            return;
        }

        String path = uri.substring(httpRequest.getContextPath().length());

        if(path.startsWith("/actions") || path.startsWith("/pages")) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Page or Action not found");
        }
    }

    private void setEncoding(ServletRequest request, ServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("Unsupported encoding", e);
        }

        response.setCharacterEncoding("UTF-8");
    }

    /**
     * @return this path is css or js path
     */
    private boolean checkResourcePath(String uri) {
        return (uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg"));
    }
}