package tw.com.eeit.petforum.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.dao.LikesDAO;
import tw.com.eeit.petforum.model.dao.MemberDAO;
import tw.com.eeit.petforum.model.dao.PetDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection conn = ConnectionFactory.getConnection()) {
			MemberDAO mDAO = new MemberDAO(conn);
			PetDAO pDAO = new PetDAO(conn);
			LikesDAO lDAO = new LikesDAO(conn);

			System.out.println(lDAO.findAllLikesByMemberID(1));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
