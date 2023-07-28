package tw.com.eeit.petforum.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.model.dao.PetDAO;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebServlet("/DeletePet.do")
public class DeletePet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/plain;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String pID = request.getParameter("pID");
		Member m = (Member)		request.getSession().getAttribute("loggedInMember");

		if (pID == null || pID.equals("")) {
			out.write("fail");
		}

		int petID = Integer.valueOf(pID);
		
		try (Connection conn = ConnectionFactory.getConnection()) {

			PetDAO pDAO = new PetDAO(conn);
			Pet p = pDAO.findPetWithMemberByID(petID);
			
			if(!p.getMember().equals(m)) {
				out.write("fail");
				return;
			}

			pDAO.deletePetByID(petID);
			out.write("success");

		} catch (SQLException e) {
			out.write("fail");
			e.printStackTrace();
		}

		out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
