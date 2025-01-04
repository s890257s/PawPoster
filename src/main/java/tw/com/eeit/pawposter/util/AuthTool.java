package tw.com.eeit.pawposter.util;

import javax.servlet.http.HttpServletRequest;

import tw.com.eeit.pawposter.model.entity.Member;

public class AuthTool {

	public static Member getLoggedInMember(HttpServletRequest request) {

		Object loggedInMember = request.getSession().getAttribute("loggedInMember");

		if (loggedInMember == null) {
			return null;
		}

		return (Member) loggedInMember;
	};
}
