package tw.com.eeit.pawposter.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tw.com.eeit.pawposter.model.dao.MemberDao;
import tw.com.eeit.pawposter.model.dao.MemberPetLikeDao;
import tw.com.eeit.pawposter.model.entity.Member;
import tw.com.eeit.pawposter.model.entity.MemberPetLike;
import tw.com.eeit.pawposter.util.ConnectionFactory;

public class MemberService {

	/* === Read === */

	public Integer countMember() {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberDao memberDao = new MemberDao(conn);
			return memberDao.countMember();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Integer countMemberPetLike() {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);
			return memberPetLikeDao.countMemberPetLike();

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/* === Create === */

	public void insertMember(Member member) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberDao memberDao = new MemberDao(conn);
			memberDao.createMember(member);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertMembers(List<Member> members) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberDao memberDao = new MemberDao(conn);
			memberDao.createMembers(members);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertLike(MemberPetLike memberPetLike) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);
			memberPetLikeDao.createLike(memberPetLike);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertLikes(List<MemberPetLike> memberPetLikes) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);
			memberPetLikeDao.createLikes(memberPetLikes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* === Update === */
	public String toggleLike(Integer memberId, Integer petId) {
		try (Connection conn = ConnectionFactory.getConnection()) {
			String message = "";

			MemberPetLike memberPetLike = new MemberPetLike(memberId, petId);

			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);
			Boolean isLiked = memberPetLikeDao.isLikeExist(memberPetLike);

			if (isLiked) {
				memberPetLikeDao.deleteLike(memberPetLike);
				message = "不讚";
			} else {
				memberPetLikeDao.createLike(memberPetLike);
				message = "讚";
			}

			return message;
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	};

	/* === Other === */
	public Member login(String email, String password) {

		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberDao memberDao = new MemberDao(conn);
			Member member = memberDao.findMemberByEmailAndPassword(email, password);

			return member;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}
}
