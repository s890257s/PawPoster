package tw.com.eeit.petforum.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;

@WebFilter("/*")
public class PageFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestTarget = httpRequest.getServletPath();

		String[] protectedPaths = { "/profile", "/add_pet", "/" };
		String[] guestOnlyPaths = { "/login" };

		// 執行過濾
		chain.doFilter(request, response);
	}

}
