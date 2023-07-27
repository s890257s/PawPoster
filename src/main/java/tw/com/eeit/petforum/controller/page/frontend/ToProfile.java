package tw.com.eeit.petforum.controller.page.frontend;

import java.io.IOException;
import java.sql.Connection;

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
		Member m = null;

		// 若mID為空，則表示是登入的會員點擊「個人資訊」
		if (mID == null) {
			HttpSession session = request.getSession();
			m = (Member) session.getAttribute("loggedInMember");

			// 若m為空，則表示沒有登入，但使用URL輸入網址，此操作是被禁止的
			if (m == null) {
				response.setStatus(404);
				return;
			}
		}

		// 若mID不為空，則接下的畫面要呈現指定的mID資料
		if (mID != null) {
			try (Connection conn = ConnectionFactory.getConnection()) {
				MemberDAO mDAO = new MemberDAO(conn);
				m = mDAO.findMemberWithPetByID(Integer.valueOf(mID));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		request.setAttribute("m", m);

		request.getRequestDispatcher(PathConverter.convertToWebInfPathForFrontend(request.getServletPath()))
				.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
