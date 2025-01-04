package tw.com.eeit.pawposter.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.pawposter.model.entity.Member;
import tw.com.eeit.pawposter.service.MemberService;
import tw.com.eeit.pawposter.util.AuthTool;

@WebServlet("/ToggleLike.do")
public class ToggleLike extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String petId = request.getParameter("petId");
		Member loggedInMember = AuthTool.getLoggedInMember(request);

		MemberService memberService = new MemberService();
		String message = memberService.toggleLike(loggedInMember.getMemberId(), Integer.valueOf(petId));

		response.setContentType("text/plain;charset=UTF-8");
		response.getWriter().write(message);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
