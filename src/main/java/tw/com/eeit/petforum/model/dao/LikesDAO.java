package tw.com.eeit.petforum.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.com.eeit.petforum.model.bean.Likes;
import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.bean.Pet;

public class LikesDAO {

	private Connection conn;

	/**
	 * 沒有「無參數的建構子(無參建購子)」，代表使用new建立此物件時，一定要傳入參數conn。
	 * 
	 * @param conn 外部傳入的連線物件，DAO不實作連線
	 */
	public LikesDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 根據會員ID尋找其所有按讚紀錄。
	 * 
	 * @param memberID 會員ID。
	 * @return List<Likes> 按讚紀錄的集合。
	 */
	public List<Likes> findAllLikesByMemberID(int memberID) throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Likes] WHERE [mID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, memberID);

		ResultSet rs = preState.executeQuery();

		List<Likes> lList = new ArrayList<>();
		while (rs.next()) {
			Likes l = new Likes();
			l.setlID(rs.getInt("lID"));
			l.setTime(rs.getDate("time"));
			l.setMember(new Member(rs.getInt("mID")));
			l.setPet(new Pet(rs.getInt("pID")));

			lList.add(l);
		}

		rs.close();
		preState.close();
		return lList;
	}
}
