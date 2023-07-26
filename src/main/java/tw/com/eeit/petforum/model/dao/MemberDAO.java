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
 * 只要是跟Member資料表有關的任何互動，都要寫在此DAO之中。<br>
 * DAO內的所有方法都拋出錯誤，交給Service處理。
 */
public class MemberDAO {

	private Connection conn;

	/**
	 * 沒有「無參數的建構子(無參建購子)」，代表使用new建立此物件時，一定要傳入參數conn。
	 * 
	 * @param conn 外部傳入的連線物件，DAO不實作連線
	 */
	public MemberDAO(Connection conn) {
		this.conn = conn;
	}

	/**
	 * 根據會員的ID取得指定會員的所有資料。
	 * <p>
	 * 
	 * @param MemberID 會員的ID。
	 * @return Member 會員的資料載體，裡面有會員的所有資料； 若ID不存在則回傳null。
	 */
	public Member findMemberByID(int memberID) throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Member] WHERE mID = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, memberID);
		ResultSet rs = preState.executeQuery();

		if (rs.next()) {
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
			return m;
		}

		rs.close();
		preState.close();

		return null;
	}

	/**
	 * 根據會員的ID取得指定會員的所有資料，包含所有寵物資訊。
	 * <p>
	 * 
	 * @param MemberID 會員的ID。
	 * @return Member 會員的資料載體，裡面有會員的所有資料(含寵物資訊)； 若ID不存在則回傳null。
	 */
	public Member findMemberWithPetByID(int memberID) throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Member] AS [m]" + " LEFT JOIN [PetForum].[dbo].[Pet] AS [p]"
				+ " ON [m].[mID] = [p].[mID]" + " WHERE [m].[mID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setInt(1, memberID);
		ResultSet rs = preState.executeQuery();

		Member m = null;

		while (rs.next()) {
			if (m == null) {
				m = new Member();
				m.setmID(rs.getInt("mID"));
				m.setEmail(rs.getString("email"));
				m.setPassword(rs.getString("password"));
				m.setEnabled(rs.getBoolean("enabled"));
				m.setLevel(rs.getString("level"));
				m.setmName(rs.getString("mName"));
				m.setmAge(rs.getInt("mAge"));
				m.setAddress(rs.getString("address"));
				m.setmPhoto(rs.getString("mPhoto"));
				m.setPets(new ArrayList<Pet>());
			}

			Pet p = new Pet();
			p.setpID(rs.getInt("pID"));
			p.setpAge(rs.getInt("pAge"));
			p.setpName(rs.getString("pName"));
			p.setType(rs.getString("type"));
			p.setpPhoto(rs.getBytes("pPhoto"));
			m.getPets().add(p);
		}

		rs.close();
		preState.close();

		return m;

	}

	/**
	 * 在一次查詢中，取得所有member，與其的所有pet資訊。 <br>
	 * 此方法邏輯稍微複雜，不一定要看懂。
	 * <p>
	 * 
	 * @return List<Member> 所有會員的集合，包含寵物資訊。
	 */
	public List<Member> findAllMemberWithPet() throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Member] AS [m]" + " LEFT JOIN [PetForum].[dbo].[Pet] AS [p]"
				+ " ON [m].[mID] = [p].[mID]";
		PreparedStatement preState = conn.prepareStatement(SQL);

		ResultSet rs = preState.executeQuery();

		List<Member> mList = new ArrayList<>();
		List<Pet> pList = null;

		Member m = null;
		int prevMemberID = -1;

		// 所有資料抓出來做迴圈
		while (rs.next()) {

			// 取得當下那筆資料的mID
			int currentMemberID = rs.getInt("mID");

			// 若與上筆mID不相等，則表示是新的member，以下會建立一個新的member
			if (prevMemberID != currentMemberID) {

				// 迴圈跑到這，m還是舊狀態(上一筆member)，只要舊狀態不為null，就將上一筆member加入mList
				if (m != null) {
					m.setPets(pList);
					mList.add(m);
				}

				// 建立當下member
				m = new Member();
				m.setmID(rs.getInt("mID"));
				m.setEmail(rs.getString("email"));
				m.setPassword(rs.getString("password"));
				m.setEnabled(rs.getBoolean("enabled"));
				m.setLevel(rs.getString("level"));
				m.setmName(rs.getString("mName"));
				m.setmAge(rs.getInt("mAge"));
				m.setAddress(rs.getString("address"));
				m.setmPhoto(rs.getString("mPhoto"));
				pList = new ArrayList<>();
			}

			// 不論如何都要抓到pet資訊
			Pet p = new Pet();
			p.setpID(rs.getInt("pID"));
			p.setpAge(rs.getInt("pAge"));
			p.setpName(rs.getString("pName"));
			p.setType(rs.getString("type"));
			p.setpPhoto(rs.getBytes("pPhoto"));

			pList.add(p);

			// 更新mID
			prevMemberID = currentMemberID;
		}

		rs.close();

		preState.close();
		return mList;
	}

	/**
	 * 根據email與password尋找會員，若尋找到會回傳Member物件，若沒找到則回傳null。
	 * <p>
	 * DAO之中不應該撰寫「邏輯」，僅該單純撰寫「CRUD」，<br>
	 * 故不會出現如「login」、「clearShoppingCart」等方法。<br>
	 * (應該以findByAccountAndPassword、removeAllByMemberID取代)
	 * <p>
	 * 【 COLLATE Latin1_General_CS_AS 】 語法可指定MSSQL的欄位定序，改變定序能使MSSQL偵測大小寫。<br>
	 * 【 SQL_Latin1_General_CP1_CI_AS 】 => (預設值) 不區分大小寫、區分重音、不區分假名、不區分寬度 <br>
	 * 【 Latin1_General_CS_AS 】 => 區分大小寫、區分重音、不區分假名、不區分寬度<br>
	 * 其中CS表示區分大小寫，AS表示區分腔調("a" 不等於 "ấ")；CI表示不區分大小寫，AI表示不區分腔調。<br>
	 * 詳情可搜尋「SQL Server 定序和 Unicode 支援」。
	 * <p>
	 * 
	 * @return Member 會員的資料載體，裡面有會員的所有資料
	 */
	public Member findMemberByEmailAndPassword(String email, String password) throws SQLException {
		final String SQL = "SELECT * FROM [PetForum].[dbo].[Member] WHERE [email] COLLATE Latin1_General_CS_AS = ? AND [password] COLLATE Latin1_General_CS_AS = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setString(1, email);
		preState.setString(2, password);

		ResultSet rs = preState.executeQuery();

		if (rs.next()) {
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

			return m;
		}

		rs.close();
		preState.close();
		return null;

	}

	public void updateEnabledByID(int memberID, boolean status) throws SQLException {
		final String SQL = "UPDATE [PetForum].[dbo].[Member] SET [enabled] = ? WHERE [mID] = ?";
		PreparedStatement preState = conn.prepareStatement(SQL);
		preState.setBoolean(1, status);
		preState.setInt(2, memberID);

		preState.executeUpdate();
		preState.close();
	}

}
