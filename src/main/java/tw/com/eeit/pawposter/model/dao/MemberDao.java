package tw.com.eeit.pawposter.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tw.com.eeit.pawposter.model.bean.Member;
import tw.com.eeit.pawposter.model.bean.Pet;
import tw.com.eeit.pawposter.util.DateTool;

/**
 * 只要是跟 Member 資料表有關的任何互動，都要寫在此 Dao 之中。<br>
 * Dao 內的所有方法都拋出錯誤，交給 Service 處理。
 */
public class MemberDao {

	private Connection conn;

	/**
	 * 沒有「無參數的建構子(無參建購子)」，代表使用 new 建立此物件時，一定要傳入參數 conn。
	 * 
	 * @param conn 外部傳入的連線物件，Dao 不實作連線
	 */
	public MemberDao(Connection conn) {
		this.conn = conn;
	}

	/* === Read === */

	/**
	 * 根據 member Id 取得指定 member 的所有資料。
	 * <p>
	 * 
	 * @return Member 的資料載體，裡面有 member 的所有資料； 若 Id 不存在則拋錯。
	 */
	public Member findMemberById(Integer memberId) throws SQLException {
		final String SQL = "SELECT * FROM [paw_poster].[dbo].[member] WHERE [member_id] = ?";
		Member member = null;

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setInt(1, memberId);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			member = new Member();
			member.setMemberId(rs.getInt("member_id"));
			member.setEmail(rs.getString("email"));
			member.setPassword(rs.getString("password"));
			member.setEnabled(rs.getBoolean("enabled"));
			member.setMemberName(rs.getString("member_name"));
			member.setMemberBirthDate(rs.getDate("member_birth_date"));
			member.setMemberPhoto(rs.getString("member_photo"));
		}
		rs.close();
		ps.close();

		if (member == null) {
			throw new RuntimeException("找不到指定的 member。member_id: %s".formatted(memberId));
		}

		return member;
	}

	/**
	 * 根據 member 的 Id 取得指定 member 的所有資料，包含所有寵物資訊。
	 * <p>
	 * 
	 * @return Member 的資料載體，裡面有 member 的所有資料(含 pet 資訊)； 若 Id 不存在則回傳無資料 Member 物件。
	 */
	public Member findMemberWithPetById(Integer memberId) throws SQLException {
		final String SQL = """
					SELECT * FROM [paw_poster].[dbo].[member] AS [m]
					LEFT JOIN [paw_poster].[dbo].[pet] AS [p] ON [m].[member_id] = [p].[member_id]
					WHERE [m].[member_id] = ?
				""";
		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setInt(1, memberId);
		ResultSet rs = ps.executeQuery();

		Member member = null;

		while (rs.next()) {
			if (member == null) {
				member = new Member();
				member.setMemberId(rs.getInt("member_id"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setEnabled(rs.getBoolean("enabled"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberBirthDate(rs.getDate("member_birth_date"));
				member.setMemberPhoto(rs.getString("member_photo"));
			}

			Pet pet = new Pet();
			pet.setPetId(rs.getInt("pet_id"));
			pet.setPetBirthDate(rs.getDate("pet_birth_date"));
			pet.setPetName(rs.getString("pet_name"));
			pet.setPetType(rs.getString("pet_type"));
			pet.setPetPhoto(rs.getBytes("pet_photo"));
			member.getPets().add(pet);
		}

		rs.close();
		ps.close();

		if (member == null) {
			throw new RuntimeException("找不到指定的 member。member_id: %s".formatted(memberId));
		}

		return member;

	}

	/**
	 * 在一次查詢中，取得所有member，與其的所有pet資訊。 <br>
	 * <p>
	 * 
	 * @return List<Member> 所有 member 的集合，包含 pet 資訊。
	 */
	public List<Member> findAllMemberWithPet() throws SQLException {
		final String SQL = """
					SELECT * FROM [paw_poster].[dbo].[member] AS [m]
					LEFT JOIN [paw_poster].[dbo].[pet] AS [p]" + " ON [m].[member_id] = [p].[member_id]
				""";

		PreparedStatement ps = conn.prepareStatement(SQL);

		ResultSet rs = ps.executeQuery();

		List<Member> members = new ArrayList<>();
		List<Pet> pets = null;

		Member member = null;
		Integer previousMemberId = -1;

		// 所有資料抓出來做迴圈
		while (rs.next()) {

			// 取得當下那筆資料的 memberId
			Integer currentMemberId = rs.getInt("memberId");

			// 若與上筆 memberId 不相等，則表示是新的 member，以下會建立一個新的 member
			if (previousMemberId != currentMemberId) {

				// 迴圈跑到這，member 還是舊狀態(上一筆member)，只要舊狀態不為 null，就將上一筆 member 加入 members
				if (member != null) {
					member.setPets(pets);
					members.add(member);
				}

				// 建立當下 member
				member = new Member();
				member.setMemberId(rs.getInt("member_id"));
				member.setEmail(rs.getString("email"));
				member.setPassword(rs.getString("password"));
				member.setEnabled(rs.getBoolean("enabled"));
				member.setMemberName(rs.getString("member_name"));
				member.setMemberBirthDate(rs.getDate("member_birth_date"));
				member.setMemberPhoto(rs.getString("member_photo"));
				pets = new ArrayList<>();
			}

			// 不論如何都要抓到 pet 資訊
			Pet pet = new Pet();
			pet.setPetId(rs.getInt("pet_id"));
			pet.setPetBirthDate(rs.getDate("pet_birth_date"));
			pet.setPetName(rs.getString("pet_name"));
			pet.setPetType(rs.getString("pet_type"));
			pet.setPetPhoto(rs.getBytes("pet_photo"));

			pets.add(pet);

			// 更新 member id
			previousMemberId = currentMemberId;
		}

		rs.close();
		ps.close();
		return members;
	}

	/**
	 * 根據 email 與 password 尋找 member ，若尋找到會回傳 Member 物件，若沒找到則回傳 null。
	 * <p>
	 * Dao 之中不應該撰寫「邏輯」，僅該單純撰寫「CRUD」，<br>
	 * 故不會出現如「login」、「clearShoppingCart」等方法。<br>
	 * (應該以 findByAccountAndPassword、removeAllByMemberId 取代)
	 * <p>
	 * 【 COLLATE Latin1_General_CS_AS 】 語法可指定MSSQL的欄位定序，改變定序能使MSSQL偵測大小寫。<br>
	 * 【 SQL_Latin1_General_CP1_CI_AS 】 => (預設值) 不區分大小寫、區分重音、不區分假名、不區分寬度 <br>
	 * 【 Latin1_General_CS_AS 】 => 區分大小寫、區分重音、不區分假名、不區分寬度<br>
	 * 其中CS表示區分大小寫，AS表示區分腔調("a" 不等於 "ấ")；CI表示不區分大小寫，AI表示不區分腔調。<br>
	 * 詳情可搜尋「SQL Server 定序和 Unicode 支援」。
	 * <p>
	 * 
	 * @return Member 的資料載體，裡面有 member 的所有資料
	 */
	public Member findMemberByEmailAndPassword(String email, String password) throws SQLException {
		final String SQL = "SELECT * FROM [paw_poster].[dbo].[member] WHERE [email] COLLATE Latin1_General_CS_AS = ? AND [password] COLLATE Latin1_General_CS_AS = ?";
		Member member = null;

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setString(1, email);
		ps.setString(2, password);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			member = new Member();
			member.setMemberId(rs.getInt("member_id"));
			member.setEmail(rs.getString("email"));
			member.setPassword(rs.getString("password"));
			member.setEnabled(rs.getBoolean("enabled"));
			member.setMemberName(rs.getString("member_name"));
			member.setMemberBirthDate(rs.getDate("member_birth_date"));
			member.setMemberPhoto(rs.getString("member_photo"));
		}
		rs.close();
		ps.close();

		return member;
	}

	/**
	 * 計算 member 表格的資料數量
	 */
	public Integer countMember() throws SQLException {
		final String SQL = "SELECT COUNT(*) FROM [paw_poster].[dbo].[member]";

		PreparedStatement ps = conn.prepareStatement(SQL);
		ResultSet rs = ps.executeQuery();
		rs.next(); // 此查詢必定會有回傳結果

		int count = rs.getInt(1);

		rs.close();
		ps.close();

		return count;
	}

	/* === Create === */

	/**
	 * 新增 member
	 */
	public void insertMember(Member member) throws SQLException {
		final String SQL = """
				INSERT INTO [paw_poster].[dbo].[member]
						([email],
						 [password],
						 [enabled],
						 [member_name],
						 [member_birth_date],
						 [member_photo])
					   VALUES(?, ?, ?, ?, ?, ?) """;

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setString(1, member.getEmail());
		ps.setString(2, member.getPassword());
		ps.setBoolean(3, member.getEnabled());
		ps.setString(4, member.getMemberName());
		ps.setDate(5, DateTool.convertUtilToSqlDate(member.getMemberBirthDate()));
		ps.setString(6, member.getMemberPhoto());

		ps.execute();
		ps.close();
	}

	/**
	 * 新增 members
	 */
	public void insertMembers(List<Member> members) throws SQLException {
		final String SQL = """
				INSERT INTO [paw_poster].[dbo].[member]
						([email],
						 [password],
						 [enabled],
						 [member_name],
						 [member_birth_date],
						 [member_photo])
					   VALUES(?, ?, ?, ?, ?, ?) """;
		PreparedStatement ps = conn.prepareStatement(SQL);

		for (Member member : members) {
			ps.setString(1, member.getEmail());
			ps.setString(2, member.getPassword());
			ps.setBoolean(3, member.getEnabled());
			ps.setString(4, member.getMemberName());
			ps.setDate(5, DateTool.convertUtilToSqlDate(member.getMemberBirthDate()));
			ps.setString(6, member.getMemberPhoto());
			ps.addBatch();
		}

		ps.executeBatch();
		ps.close();
	}

	/* === Update === */

	/**
	 * 修改指定 memberId 的 status 狀態。
	 */
	public void updateEnabledById(Integer memberId, Boolean status) throws SQLException {
		final String SQL = "UPDATE [paw_poster].[dbo].[member] SET [enabled] = ? WHERE [member_id] = ?";

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setBoolean(1, status);
		ps.setInt(2, memberId);

		ps.executeUpdate();
		ps.close();
	}

}
