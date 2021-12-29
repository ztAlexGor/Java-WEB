package filter.impl;

import entity.User;
import filter.AbstractFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter(filterName = "UserFilter", urlPatterns = { "/book", "/reservations" })
public class UserFilter extends AbstractFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        User user = getUser(servletRequest);
        if (user == null) {
            throw new ServletException("User is not authenticated");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
