package tw.com.eeit.pawposter.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tw.com.eeit.pawposter.model.entity.Member;
import tw.com.eeit.pawposter.service.MemberService;

@WebServlet("/login.do")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		MemberService memberService = new MemberService();
		Member loggedInMember = memberService.login(email, password);

		if (loggedInMember == null) {
			session.setAttribute("message", "登入失敗，帳號或密碼錯誤");
			response.sendRedirect(request.getContextPath() + "/page/login");
			return;
		}

		if (!loggedInMember.getEnabled()) {
			session.setAttribute("message", "此帳號已被禁用，請聯繫系統管理員");
			response.sendRedirect(request.getContextPath() + "/page/login");
			return;
		}

		session.setAttribute("loggedInMember", loggedInMember);
		response.sendRedirect(request.getContextPath() + "/page/pets");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
