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
		final String SQL = "SELECT * FROM [PawPoster].[dbo].[Likes] WHERE [mID] = ?";
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

	/**
	 * 根據會員ID與寵物ID尋找會員的單筆按讚紀錄。
	 * 
	 * @param likes 按讚的物件。
	 * @return Likes 單筆按讚記錄，若無則回傳null。
	 */
	public Likes findLikeByMemberIDAndPetID(Likes likes) throws SQLException {
		final String SQL = "SELECT * FROM [PawPoster].[dbo].[Likes] WHERE [mID] = ? AND [pID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, likes.getMember().getmID());
		preState.setInt(2, likes.getPet().getpID());

		ResultSet rs = preState.executeQuery();
		Likes l = null;
		if (rs.next()) {
			l = new Likes();
			l.setlID(rs.getInt("lID"));
			l.setTime(rs.getDate("time"));
			l.setMember(new Member(rs.getInt("mID")));
			l.setPet(new Pet(rs.getInt("pID")));
		}

		rs.close();
		preState.close();
		return l;
	}

	/**
	 * 新增按讚記錄。
	 * 
	 * @param likes 按讚的物件。
	 */
	public void addLike(Likes likes) throws SQLException {
		final String SQL = "INSERT INTO [PawPoster].[dbo].[Likes]([time], [mID], [pID]) VALUES(?, ?, ?)";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setDate(1, new java.sql.Date(likes.getTime().getTime()));
		preState.setInt(2, likes.getMember().getmID());
		preState.setInt(3, likes.getPet().getpID());

		preState.execute();
	}

	/**
	 * 移除按讚記錄。
	 * 
	 * @param likes 按讚的物件。
	 */
	public void removeLike(Likes likes) throws SQLException {
		final String SQL = "DELETE FROM [PawPoster].[dbo].[Likes] WHERE [mID] = ? AND [pID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, likes.getMember().getmID());
		preState.setInt(2, likes.getPet().getpID());

		preState.execute();
	}
}
