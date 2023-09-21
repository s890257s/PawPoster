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
import tw.com.eeit.petforum.model.dto.PetSearchCriteria;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebServlet("/QueryPet.do")
public class QueryPet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String type = request.getParameter("type");
		String minAge = request.getParameter("minAge");
		String maxAge = request.getParameter("maxAge");

		try (Connection conn = ConnectionFactory.getConnection()) {
			PetDAO pDAO = new PetDAO(conn);
			PetSearchCriteria psc = new PetSearchCriteria(minAge, maxAge, type);

			List<Pet> pList = pDAO.findPetWithMemberByCriteria(psc);
			request.setAttribute("pList", pList);

			request.getRequestDispatcher("WEB-INF/view/frontend/pets.jsp").forward(request, response);
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
