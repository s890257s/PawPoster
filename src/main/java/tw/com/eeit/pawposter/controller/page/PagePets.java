package tw.com.eeit.pawposter.controller.page;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tw.com.eeit.pawposter.model.dto.PetDto;
import tw.com.eeit.pawposter.model.po.Member;
import tw.com.eeit.pawposter.service.PetService;
import tw.com.eeit.pawposter.util.AuthTool;
import tw.com.eeit.pawposter.util.PathConverter;

@WebServlet("/page/pets")
public class PagePets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Member loggedInMember = AuthTool.getLoggedInMember(request);
		Integer memberId = Objects.isNull(loggedInMember) ? -1 : loggedInMember.getMemberId();

		PetService petService = new PetService();

		List<PetDto> pets = petService.getAllPetWithMemberAndLikeStatus(memberId);

		request.setAttribute("pets", pets);

		request.getRequestDispatcher(PathConverter.convertToWebInfPath(request.getServletPath())).forward(request,
				response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
