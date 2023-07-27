package tw.com.eeit.petforum.controller.page.frontend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.dao.MemberDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;
import tw.com.eeit.petforum.util.PathConverter;

@WebServlet("/profile")
public class ToProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mID = request.getParameter("mID");
		Member m = (Member) request.getSession().getAttribute("loggedInMember");

		int memberID = (mID == null) ? m.getmID() : Integer.valueOf(mID);

		try (Connection conn = ConnectionFactory.getConnection()) {
			MemberDAO mDAO = new MemberDAO(conn);

			m = mDAO.findMemberWithPetByID(memberID);

			request.setAttribute("m", m);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		request.getRequestDispatcher(PathConverter.convertToWebInfPathForFrontend(request.getServletPath()))
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
