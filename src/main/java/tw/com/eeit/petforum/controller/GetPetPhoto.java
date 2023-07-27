package tw.com.eeit.petforum.controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.model.dao.PetDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebServlet("/GetPetPhoto.do")
public class GetPetPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();

		String pID = request.getParameter("pID");
		if (pID == null) {
			request.getRequestDispatcher("assets/no_image.png").forward(request, response);
			return;
		}

		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDAO pDAO = new PetDAO(conn);

			Pet p = pDAO.findPetByID(Integer.valueOf(pID));

			if (p == null) {
				request.getRequestDispatcher("assets/no_image.png").forward(request, response);
				return;
			}

			out.write(p.getpPhoto());

			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
