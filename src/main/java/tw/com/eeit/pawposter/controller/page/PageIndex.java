package tw.com.eeit.pawposter.controller.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.pawposter.util.PathConverter;

@WebServlet("/page/index")
public class PageIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String requestPath = request.getServletPath(); // page/index
		String convertToWebInfPath = PathConverter.convertToWebInfPath(requestPath); // WEB-INF/view/index.jsp
		request.getRequestDispatcher(convertToWebInfPath).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
