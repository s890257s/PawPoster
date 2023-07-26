package tw.com.eeit.petforum.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.bean.Pet;

/**
 * 只要是跟Pet資料表有關的任何互動，都要寫在此DAO之中。 DAO內的所有方法都拋出錯誤，交給Service處理。
 */
public class PetDAO {

	private Connection conn;

	/**
	 * 沒有「無參數的建構子(無參建購子)」，代表使用new建立此物件時，一定要傳入參數conn。
	 * 
	 * @param conn 外部傳入的連線物件，DAO不實作連線
	 */
	public PetDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 根據寵物的ID取得指定寵物的所有資料。
	 * <p>
	 * 
	 * @param petID 寵物的ID。
	 * @return Pet 寵物的資料載體，裡面有寵物的所有資料； 若ID不存在則回傳null。
	 */
	public Pet findPetByID(int petID) throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Pet] WHERE [pID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, petID);
		ResultSet rs = preState.executeQuery();

		if (rs.next()) {
			Pet p = new Pet();
			p.setpID(rs.getInt("pID"));
			p.setpAge(rs.getInt("pAge"));
			p.setpName(rs.getString("pName"));
			p.setType(rs.getString("type"));
			p.setpPhoto(rs.getBytes("pPhoto"));
			return p;
		}

		preState.close();

		return null;
	}

	/**
	 * 在一次查詢中，取得所有Pet，與其擁有者Member的資訊。 <br>
	 * <p>
	 * 
	 * @return List<Pet> 所有寵物的集合，包含主人的資訊。
	 */
	public List<Pet> findAllPetWithMember() throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Pet] AS [p]" + " LEFT JOIN [PetForum].[dbo].[Member] AS [m]"
				+ " ON [p].[mID] = [m].[mID]";

		PreparedStatement preState = conn.prepareStatement(SQL);
		ResultSet rs = preState.executeQuery();

		List<Pet> pList = new ArrayList<Pet>();

		while (rs.next()) {
			Pet p = new Pet();
			p.setpID(rs.getInt("pID"));
			p.setpAge(rs.getInt("pAge"));
			p.setpName(rs.getString("pName"));
			p.setType(rs.getString("type"));
			p.setpPhoto(rs.getBytes("pPhoto"));

			Member m = new Member();
			m.setmID(rs.getInt("mID"));
			m.setEmail(rs.getString("email"));
			m.setPassword(rs.getString("password"));
			m.setEnabled(rs.getBoolean("enabled"));
			m.setLevel(rs.getString("level"));
			m.setmName(rs.getString("mName"));
			m.setmAge(rs.getInt("mAge"));
			m.setAddress(rs.getString("address"));
			m.setmPhoto(rs.getString("mPhoto"));

			p.setMember(m);

			pList.add(p);
		}

		rs.close();
		preState.close();

		return pList;

	}
}
