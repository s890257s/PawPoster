package tw.com.eeit.petforum.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.bean.Member;

@WebFilter("/backend/*")
public class BackendFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = 1L;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Member m = (Member) httpRequest.getSession().getAttribute("loggedInMember");

		if (m == null || !m.getLevel().equals("管理員")) {
			httpResponse.setHeader("Refresh", "3;url=%s/index".formatted(httpRequest.getContextPath()));
			httpResponse.setContentType("text/html;charset=UTF-8");
			
			PrintWriter out = httpResponse.getWriter();
			out.write("<h1 style='position:relative;top:40%;left:25%;display:inline'>你沒有權限查看此頁面，三秒後導向首頁</h1>");
			out.close();

			return;
		}

		chain.doFilter(request, response);
	}

}
