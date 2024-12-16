package tw.com.eeit.pawposter.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import tw.com.eeit.pawposter.model.dao.MemberDao;
import tw.com.eeit.pawposter.model.dao.MemberPetLikeDao;
import tw.com.eeit.pawposter.model.po.Member;
import tw.com.eeit.pawposter.model.po.MemberPetLike;
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
			memberDao.insertMember(member);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertMembers(List<Member> members) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberDao memberDao = new MemberDao(conn);
			memberDao.insertMembers(members);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertLike(MemberPetLike memberPetLike) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);
			memberPetLikeDao.insertLike(memberPetLike);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insertLikes(List<MemberPetLike> memberPetLikes) {
		try (Connection conn = ConnectionFactory.getConnection()) {

			MemberPetLikeDao memberPetLikeDao = new MemberPetLikeDao(conn);
			memberPetLikeDao.insertLikes(memberPetLikes);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
