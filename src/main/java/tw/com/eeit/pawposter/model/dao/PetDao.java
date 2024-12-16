package tw.com.eeit.pawposter.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tw.com.eeit.pawposter.model.po.Member;
import tw.com.eeit.pawposter.model.po.Pet;

/**
 * 只要是跟Pet資料表有關的任何互動，都要寫在此 Dao 之中。Dao 內的所有方法都拋出錯誤，交給 Service 處理。
 */
public class PetDao {

	private Connection conn;

	/**
	 * 沒有「無參數的建構子(無參建購子)」，代表使用 new 建立此物件時，一定要傳入參數 conn。
	 * 
	 * @param conn 外部傳入的連線物件， Dao 不實作連線
	 */
	public PetDao(Connection conn) {
		this.conn = conn;
	}

	/* === Read === */

	/**
	 * 根據 pet id 取得指定 pet 的所有資料。
	 * <p>
	 * 
	 * @return Pet 的資料載體，裡面有 pet 的所有資料； 若 id 不存在則回傳無資料的 Pet 物件。
	 */
	public Pet findPetById(Integer petId) throws SQLException {
		final String SQL = "SELECT * FROM [paw_poster].[dbo].[pet] WHERE [pet_id] = ?";
		Pet pet = null;

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setInt(1, petId);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			pet = new Pet();
			pet.setPetId(rs.getInt("pet_id"));
			pet.setPetBirthDate(rs.getDate("pet_birth_date"));
			pet.setPetName(rs.getString("pet_name"));
			pet.setPetType(rs.getString("pet_type"));
			pet.setPetPhoto(rs.getBytes("pet_photo"));
		} else {
			throw new RuntimeException("目標 Pet 不存在。pet id: %s".formatted(petId));
		}

		rs.close();
		ps.close();

		return pet;
	}

	/**
	 * 在一次查詢中，取得 Pet 與其擁有者 Member 的資訊。 <br>
	 * <p>
	 * 
	 * @return Pet 的資訊，包含主人的資訊。
	 */
	public Pet findPetWithMemberById(Integer petId) throws SQLException {
		final String SQL = """
					SELECT * FROM [paw_poster].[dbo].[pet] AS [p]
					LEFT JOIN [paw_poster].[dbo].[member] AS [m] ON [p].[member_id] = [m].[member_id]
					WHERE [p].[pet_id] = ?
				""";
		Pet pet = null;

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setInt(1, petId);

		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			pet = new Pet();
			pet.setPetId(rs.getInt("pet_id"));
			pet.setPetBirthDate(rs.getDate("pet_birth_date"));
			pet.setPetName(rs.getString("pet_name"));
			pet.setPetType(rs.getString("pet_type"));
			pet.setPetPhoto(rs.getBytes("pet_photo"));

			Member member = pet.getMember();
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

		return pet;

	}

	/**
	 * 在一次查詢中，取得所有Pet，與其擁有者Member的資訊。 <br>
	 * <p>
	 * 
	 * @throws SQLException
	 * @return List<Pet> 所有 pet 的集合，包含主人的資訊。
	 */
	public List<Pet> findAllPetWithMember() throws SQLException {
		final String SQL = """
					SELECT * FROM [paw_poster].[dbo].[pet] AS [p]
					LEFT JOIN [paw_poster].[dbo].[member] AS [m] ON [p].[member_id] = [m].[member_id]
				""";
		List<Pet> pets = new ArrayList<Pet>();

		PreparedStatement ps = conn.prepareStatement(SQL);

		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Pet pet = new Pet();
			pet.setPetId(rs.getInt("pet_id"));
			pet.setPetBirthDate(rs.getDate("pet_birth_date"));
			pet.setPetName(rs.getString("pet_name"));
			pet.setPetType(rs.getString("pet_type"));
			pet.setPetPhoto(rs.getBytes("pet_photo"));

			Member member = pet.getMember();
			member.setMemberId(rs.getInt("member_id"));
			member.setEmail(rs.getString("email"));
			member.setPassword(rs.getString("password"));
			member.setEnabled(rs.getBoolean("enabled"));
			member.setMemberName(rs.getString("member_name"));
			member.setMemberBirthDate(rs.getDate("member_birth_date"));
			member.setMemberPhoto(rs.getString("member_photo"));

			pets.add(pet);
		}

		rs.close();
		ps.close();

		return pets;
	}

	/**
	 * 計算 pet 表格的資料數量
	 */
	public Integer countPet() throws SQLException {
		final String SQL = "SELECT COUNT(*) FROM [paw_poster].[dbo].[pet]";

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
	 * 新增 pet。
	 */
	public void insertPet(Pet pet) throws SQLException {
		final String SQL = "INSERT INTO [paw_poster].[dbo].[pet]([pet_name], [pet_type], [pet_birth_date], [pet_photo], [member_id]) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setString(1, pet.getPetName());
		ps.setString(2, pet.getPetType());
		ps.setDate(3, new Date(pet.getPetBirthDate().getTime()));
		ps.setBytes(4, pet.getPetPhoto());
		ps.setInt(5, pet.getMember().getMemberId());

		ps.execute();
		ps.close();
	}

	/**
	 * 新增 pets。
	 */
	public void insertPets(List<Pet> pets) throws SQLException {
		final String SQL = "INSERT INTO [paw_poster].[dbo].[pet]([pet_name], [pet_type], [pet_birth_date], [pet_photo], [member_id]) VALUES(?, ?, ?, ?, ?)";

		PreparedStatement ps = conn.prepareStatement(SQL);

		for (Pet pet : pets) {
			ps.setString(1, pet.getPetName());
			ps.setString(2, pet.getPetType());
			ps.setDate(3, new Date(pet.getPetBirthDate().getTime()));
			ps.setBytes(4, pet.getPetPhoto());
			ps.setInt(5, pet.getMember().getMemberId());
			ps.addBatch();
		}

		ps.executeBatch();
		ps.close();
	}

	/* === Update === */

	/**
	 * 修改指定 pet 資訊。
	 */
	public void updatePet(Integer petId, Pet pet) throws SQLException {
		final String SQL = "UPDATE [paw_poster].[dbo].[pet] SET [pet_name] = ?, [pet_type] = ?, [pet_birth_date] = ?, [pet_photo] = ? WHERE [pet_id] = ?";

		Pet currentPet = findPetById(petId);

		String updatePetName = Objects.isNull(pet.getPetName()) ? currentPet.getPetName() : pet.getPetName();
		String updatePetType = Objects.isNull(pet.getPetType()) ? currentPet.getPetType() : pet.getPetType();
		java.util.Date updatePetBirthDate = Objects.isNull(pet.getPetBirthDate()) ? currentPet.getPetBirthDate()
				: pet.getPetBirthDate();
		byte[] updatePetPhoto = Objects.isNull(pet.getPetPhoto()) ? currentPet.getPetPhoto() : pet.getPetPhoto();

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setString(1, updatePetName);
		ps.setString(2, updatePetType);
		ps.setDate(3, new Date(updatePetBirthDate.getTime()));
		ps.setBytes(4, updatePetPhoto);
		ps.setInt(5, petId);

		ps.executeUpdate();
		ps.close();
	}

	/* === Delete === */

	/**
	 * 根據 petId 刪除指定的 pet。
	 */
	public void deletePetById(Integer petId) throws SQLException {
		final String SQL = "DELETE FROM [paw_poster].[dbo].[pet] WHERE [pet_id] = ?";

		PreparedStatement ps = conn.prepareStatement(SQL);
		ps.setInt(1, petId);
		ps.execute();
		ps.close();
	}

}
