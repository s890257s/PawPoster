package tw.com.eeit.petforum.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.model.dao.PetDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebServlet("/AddPet.do")
@MultipartConfig
public class AddPet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String petName = request.getParameter("petName");

		String petAge = request.getParameter("petAge");
		petAge = (petAge.equals("")) ? "-1" : petAge;

		String petType = request.getParameter("petType");

		Part petPhotoPart = request.getPart("petPhoto");
		BufferedInputStream bis = new BufferedInputStream(petPhotoPart.getInputStream());
		byte[] petPhoto = bis.readAllBytes();
		bis.close();

		Pet p = new Pet(petName, petType, Integer.valueOf(petAge), petPhoto,
				(Member) request.getSession().getAttribute("loggedInMember"));

		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDAO pDAO = new PetDAO(conn);
			pDAO.insertPet(p);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		response.sendRedirect("profile");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
