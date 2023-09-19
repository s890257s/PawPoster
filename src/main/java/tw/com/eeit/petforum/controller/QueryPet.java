package tw.com.eeit.petforum.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.model.dao.PetDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebServlet("/QueryPet.do")
public class QueryPet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		Integer age = request.getParameter("age") == null ? null : Integer.valueOf(request.getParameter("age"));

		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDAO pDAO = new PetDAO(conn);
			Pet p = new Pet(type, age);

			List<Pet> pList = pDAO.findPetWithMemberByAgeOrType(p);
			System.out.println(pList);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
