package tw.com.eeit.pawposter.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.com.eeit.pawposter.model.bean.MemberPetLike;

public class MemberPetLikeDao {

	private Connection conn;

	/**
	 * 沒有「無參數的建構子(無參建購子)」，代表使用 new 建立此物件時，一定要傳入參數 conn。
	 * 
	 * @param conn 外部傳入的連線物件，DAO 不實作連線
	 */
	public MemberPetLikeDao(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 根據 member_id 尋找其所有按讚紀錄。
	 * 
	 * @param memberId 會員Id。
	 * @return List<MemberPetLike> 按讚紀錄的集合。
	 */
	public List<MemberPetLike> findAllMemberPetLikeByMemberId(int memberId) throws SQLException {
		final String SQL = "SELECT * FROM [paw_poster].[dbo].[member_pet_like] WHERE [member_id] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, memberId);

		ResultSet rs = preState.executeQuery();

		List<MemberPetLike> memberPetLikes = new ArrayList<>();
		while (rs.next()) {
			MemberPetLike memberPetLike = new MemberPetLike();
			memberPetLike.setLikeId(rs.getInt("like_id"));
			memberPetLike.setCreateDate(rs.getDate("create_date"));
			memberPetLike.setMember(rs.getInt("member_id"));
			memberPetLike.setPet(rs.getInt("pet_id"));

			memberPetLikes.add(memberPetLike);
		}

		rs.close();
		preState.close();
		return memberPetLikes;
	}

	/**
	 * 根據 member_id 與 pet_id 尋找會員的單筆按讚紀錄。
	 * 
	 * @param memberPetLike 按讚的物件。
	 * @return true: member 按了 pet 讚，false: member 沒按 pet 讚
	 */
	public Boolean isLikeExist(MemberPetLike memberPetLike) throws SQLException {
		final String SQL = "SELECT * FROM [paw_poster].[dbo].[member_pet_like] WHERE [member_id] = ? AND [pet_id] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, memberPetLike.getMember().getMemberId());
		preState.setInt(2, memberPetLike.getPet().getPetId());

		ResultSet rs = preState.executeQuery();

		boolean isLikeExist = rs.next();

		rs.close();
		preState.close();

		return isLikeExist;
	}

	/**
	 * 新增按讚記錄。
	 * 
	 * @param memberPetLike 按讚的物件。
	 */
	public void insertLike(MemberPetLike memberPetLike) throws SQLException {
		final String SQL = "INSERT INTO [paw_poster].[dbo].[member_pet_like]([create_date], [member_id], [pet_id]) VALUES(?, ?, ?)";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setDate(1, new Date(memberPetLike.getCreateDate().getTime()));
		preState.setInt(2, memberPetLike.getMember().getMemberId());
		preState.setInt(3, memberPetLike.getPet().getPetId());

		preState.execute();
	}

	/**
	 * 移除按讚記錄。
	 * 
	 * @param memberPetLike 按讚的物件。
	 */
	public void removeLike(MemberPetLike memberPetLike) throws SQLException {
		final String SQL = "DELETE FROM [paw_poster].[dbo].[member_pet_like] WHERE [member_id] = ? AND [pet_id] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, memberPetLike.getMember().getMemberId());
		preState.setInt(2, memberPetLike.getPet().getPetId());

		preState.execute();
	}
}
