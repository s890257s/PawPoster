package tw.com.eeit.petforum.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.model.dto.PetSearchCriteria;

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
	 * @throws SQLException
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
	 * 在一次查詢中，取得Pet與其擁有者Member的資訊。 <br>
	 * <p>
	 * 
	 * @throws SQLException
	 * @return Pet 寵物的資訊，包含主人的資訊。
	 */
	public Pet findPetWithMemberByID(int petID) throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Pet] AS [p]" + " LEFT JOIN [PetForum].[dbo].[Member] AS [m]"
				+ " ON [p].[mID] = [m].[mID]" + " WHERE [p].[pID] = ?";

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

			return p;
		}

		rs.close();
		preState.close();

		return null;

	}

	/**
	 * 在一次查詢中，取得所有Pet，與其擁有者Member的資訊。 <br>
	 * <p>
	 * 
	 * @throws SQLException
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

	/**
	 * 新增寵物的方法，有兩種寫法，撰寫時需注意寫法的命名差異。<br>
	 * 此方法從傳入的Pet物件中取得Member物件，再取得memberID。
	 * 
	 * @throws SQLException
	 */
	public void insertPet(Pet p) throws SQLException {
		final String SQL = "INSERT INTO [PetForum].[dbo].[Pet]([pName], [type], [pAge], [pPhoto], [mID]) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setString(1, p.getpName());
		preState.setString(2, p.getType());
		preState.setInt(3, p.getpAge());
		preState.setBytes(4, p.getpPhoto());
		preState.setInt(5, p.getMember().getmID());

		preState.execute();
		preState.close();
	}

	/**
	 * 新增寵物的方法，有兩種寫法，撰寫時需注意寫法的命名差異。<br>
	 * 此方法呼叫時需傳入memberID與Pet物件。
	 * 
	 * @throws SQLException
	 */
	public void insertPetByMemberID(int memberID, Pet p) throws SQLException {
		final String SQL = "INSERT INTO [PetForum].[dbo].[Pet]([pName], [type], [pAge], [pPhoto], [mID]) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setString(1, p.getpName());
		preState.setString(2, p.getType());
		preState.setInt(3, p.getpAge());
		preState.setBytes(4, p.getpPhoto());
		preState.setInt(5, memberID);

		preState.execute();
		preState.close();
	}

	/**
	 * 根據petID刪除指定的寵物。
	 * 
	 * @throws SQLException
	 */
	public void deletePetByID(int petID) throws SQLException {
		final String SQL = "DELETE FROM [PetForum].[dbo].[Pet] WHERE [pID] = ?";

		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, petID);
		preState.execute();
		preState.close();
	}

	/**
	 * 命名與新增(insert)的風格類似。<br>
	 * 此方法無法修改petID，因更新的值會被封裝在Pet物件中，<br>
	 * 更新的ID == 目標ID，即會找到不存在的ID。(但主鍵基本上不會更新)
	 * 
	 * @throws SQLException
	 */
	public void updatePet(Pet p) throws SQLException {
		final String SQL = "UPDATE [PetForum].[dbo].[Pet] SET [pName] = ?, [type] = ?, [pAge] = ?, [pPhoto] = ? WHERE [pID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setString(1, p.getpName());
		preState.setString(2, p.getType());
		preState.setInt(3, p.getpAge());
		preState.setBytes(4, p.getpPhoto());
		preState.setInt(5, p.getpID());

		preState.executeUpdate();
		preState.close();
	}

	/**
	 * 命名與新增(insert)的風格類似。<br>
	 * 此方法可以修改petID，但要注意通常不會更新流水號ID(甚至不應該暴露給使用者)。
	 * 
	 * @throws SQLException
	 */
	public void updatePetByID(int pID, Pet p) throws SQLException {
		final String SQL = "UPDATE [PetForum].[dbo].[Pet] SET [pID] = ?,[pName] = ?, [type] = ?, [pAge] = ?, [pPhoto] = ? WHERE [pID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, p.getpID());
		preState.setString(2, p.getpName());
		preState.setString(3, p.getType());
		preState.setInt(4, p.getpAge());
		preState.setBytes(5, p.getpPhoto());
		preState.setInt(6, pID);

		preState.executeUpdate();
		preState.close();
	}

	/**
	 * 多條件查詢寵物，可接受最小年齡minAge、最大年齡maxAge、種類type。 <br>
	 * 將條件封裝成PetSearchCriteria，定義在model.dto中。<br>
	 * dto(data transfer object)為一種資料傳輸物件。<br>
	 * <p>
	 * 
	 * @throws SQLException
	 * @return List<Pet> 所有寵物的集合，包含主人的資訊。
	 */
	public List<Pet> findPetWithMemberByCriteria(PetSearchCriteria psc) throws SQLException {
		String SQL = "SELECT * FROM [PetForum].[dbo].[Pet] AS [p]" + " LEFT JOIN [PetForum].[dbo].[Member] AS [m]"
				+ " ON [p].[mID] = [m].[mID]" + " WHERE 1=1";

		String minAge = psc.getMinAge();
		String maxAge = psc.getMaxAge();
		String type = psc.getType();

		if (!"".equals(minAge)) {
			SQL += " AND [p].[pAge] >= ?";
		}

		if (!"".equals(maxAge)) {
			SQL += " AND [p].[pAge] <= ?";
		}

		if (!"".equals(type)) {
			SQL += " AND [p].[type] = ?";
		}

		int paramIndex = 1;
		PreparedStatement preState = conn.prepareStatement(SQL);

		if (!"".equals(minAge)) {
			preState.setString(paramIndex++, minAge);
		}

		if (!"".equals(maxAge)) {
			preState.setString(paramIndex++, maxAge);
		}

		if (!"".equals(type)) {
			preState.setString(paramIndex++, type);
		}

		ResultSet rs = preState.executeQuery();

		List<Pet> pList = new ArrayList<Pet>();

		while (rs.next()) {
			Pet pet = new Pet();
			pet.setpID(rs.getInt("pID"));
			pet.setpAge(rs.getInt("pAge"));
			pet.setpName(rs.getString("pName"));
			pet.setType(rs.getString("type"));
			pet.setpPhoto(rs.getBytes("pPhoto"));

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

			pet.setMember(m);
			pList.add(pet);
		}

		rs.close();

		preState.close();
		return pList;
	}
}
