package tw.com.eeit.petforum.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.bean.Member;

@WebFilter("/*")
public class FrontendFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Member m = (Member) httpRequest.getSession().getAttribute("loggedInMember");

		String requestTarget = httpRequest.getServletPath();

		List<String> protectedPaths = Arrays.asList(new String[] { "/add_pet" });
		List<String> guestOnlyPaths = Arrays.asList(new String[] { "/login" });

		if (protectedPaths.contains(requestTarget) && m == null) {
			request.setAttribute("message", "請先登入才可瀏覽" + requestTarget.replace("/", ""));
			request.getRequestDispatcher("login").forward(httpRequest, response);
			return;
		}

		if (guestOnlyPaths.contains(requestTarget) && m != null) {
			httpResponse.sendRedirect("index");
			return;
		}

		chain.doFilter(request, response);
	}

}
