package tw.com.eeit.pawposter.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.pawposter.model.dao.MemberDao;
import tw.com.eeit.pawposter.model.dao.MemberPetLikeDao;
import tw.com.eeit.pawposter.model.dao.PetDao;
import tw.com.eeit.pawposter.util.ConnectionFactory;

@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (Connection conn = ConnectionFactory.getConnection()) {
			MemberDao mDAO = new MemberDao(conn);
			PetDao pDAO = new PetDao(conn);
			MemberPetLikeDao lDAO = new MemberPetLikeDao(conn);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
