package tw.com.eeit.petforum.controller.page.frontend;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.model.dao.PetDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;
import tw.com.eeit.petforum.util.PathConverter;

@WebServlet("/pets")
public class ToPets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDAO pDAO = new PetDAO(conn);

			List<Pet> pList = pDAO.findAllPetWithMember();

			request.setAttribute("pList", pList);

		} catch (Exception e) {
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
