package tw.com.eeit.pawposter.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.pawposter.model.dao.LikesDAO;
import tw.com.eeit.pawposter.model.dao.MemberDAO;
import tw.com.eeit.pawposter.model.dao.PetDAO;
import tw.com.eeit.pawposter.service.MemberService;
import tw.com.eeit.pawposter.util.ConnectionFactory;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection conn = ConnectionFactory.getConnection()) {
			MemberDAO mDAO = new MemberDAO(conn);
			PetDAO pDAO = new PetDAO(conn);
			LikesDAO lDAO = new LikesDAO(conn);

			MemberService mService = new MemberService();
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
