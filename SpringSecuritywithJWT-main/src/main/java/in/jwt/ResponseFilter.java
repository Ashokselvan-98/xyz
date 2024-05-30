package in.jwt;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResponseFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ResponseHolder.set((HttpServletResponse) response);
        try {
            chain.doFilter(request, response);
        } finally {
            ResponseHolder.reset();
        }
    }
}
