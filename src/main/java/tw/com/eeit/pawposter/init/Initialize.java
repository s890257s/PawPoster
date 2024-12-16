package tw.com.eeit.pawposter.init;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tw.com.eeit.pawposter.model.bean.Member;
import tw.com.eeit.pawposter.model.bean.MemberPetLike;
import tw.com.eeit.pawposter.model.bean.Pet;
import tw.com.eeit.pawposter.util.ConnectionFactory;

@WebListener
public class Initialize implements ServletContextListener {

	private String INITIALIZATION_DATA_PATH = "META-INF/initialization_data/";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		// 初始化
		context.setAttribute("root", context.getContextPath());
		context.setAttribute("webName", "PawPoster");
		context.setAttribute("component", "/WEB-INF/view/component");

		// 以下為「在資料庫塞入預設值」的程式
		INITIALIZATION_DATA_PATH = context.getRealPath(INITIALIZATION_DATA_PATH);

		try (Connection conn = ConnectionFactory.getConnection()) {

			createDB(conn); // 建立資料庫
			createTableAndInsertData(conn); // 建立Member資料表並加入預設值

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/* === 若 paw_poster 資料庫不存在則建立 === */
	private void createDB(Connection conn) throws SQLException {

		String SQL = "IF DB_ID('paw_poster') IS NULL CREATE DATABASE paw_poster";

		Statement state = conn.createStatement();
		state.execute(SQL);
		state.close();
	}

	private void createTableAndInsertData(Connection conn) throws Exception {
		String SQL;
		Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();

		/* === 若 member 表格不存在則建立 === */
		SQL = """
				IF OBJECT_ID('[paw_poster].[dbo].[member]') IS NULL
				CREATE TABLE [paw_poster].[dbo].[member] (
					[member_id] [int] IDENTITY(1, 1) PRIMARY KEY NOT NULL,
					[email] [nvarchar](50) NOT NULL,
					[password] [nvarchar](50) NOT NULL,
					[enabled] [bit] NOT NULL,
					[member_name] [nvarchar](10),
					[member_birth_date] [date],
					[member_photo] [nvarchar](max));
				""";

		Statement state = conn.createStatement();
		state.execute(SQL);
		state.close();

		/* === 若 pet 表格不存在則建立 === */
		SQL = """
				IF OBJECT_ID('[paw_poster].[dbo].[pet]') IS NULL
				CREATE TABLE [paw_poster].[dbo].[pet] (
					[pet_id] [int] IDENTITY(1, 1) PRIMARY KEY NOT NULL,
					[pet_type] [nvarchar](50) NOT NULL,
					[pet_name] [nvarchar](50),
					[pet_birth_date] [date],
					[pet_photo] [varbinary](max),
					[member_id] [int] FOREIGN KEY REFERENCES [paw_poster].[dbo].[member]([member_id]));
				""";
		state = conn.createStatement();
		state.execute(SQL);
		state.close();

		/* === 若 member_pet_like 表格不存在則建立 === */
		SQL = """
				IF OBJECT_ID('[paw_poster].[dbo].[member_pet_like]') IS NULL
				CREATE TABLE [paw_poster].[dbo].[member_pet_like] (
					[like_id] [int] IDENTITY(1, 1) PRIMARY KEY NOT NULL,
					[create_date] [date] NOT NULL,
					[member_id] [int] FOREIGN KEY REFERENCES [paw_poster].[dbo].[member]([member_id]),
					[pet_id] [int] FOREIGN KEY REFERENCES [paw_poster].[dbo].[pet]([pet_id]));
				""";
		state = conn.createStatement();
		state.execute(SQL);
		state.close();

		/* === 若 member 表格中沒有任何一筆資料則新增 === */
		if (!conn.createStatement().executeQuery("SELECT [member_id] FROM [paw_poster].[dbo].[member]").next()) {
			// 使用 Gson、JavaIO，讀取 webapp/META-INF/initialization_data/members.json 的資料
			BufferedReader br = new BufferedReader(new FileReader(INITIALIZATION_DATA_PATH + "members.json"));
			List<Member> members = gson.fromJson(br, new TypeToken<List<Member>>() {
			}.getType());
			br.close();

			SQL = "INSERT INTO [paw_poster].[dbo].[member] ([email], [password], [enabled], [member_name], [member_birth_date], [member_photo]) VALUES (?, ?, ?, ?, ?, ?)";

			// 新增資料到資料表
			PreparedStatement ps = conn.prepareStatement(SQL);
			for (Member m : members) {
				ps.setString(1, m.getEmail());
				ps.setString(2, m.getPassword());
				ps.setBoolean(3, m.getEnabled());
				ps.setString(4, m.getMemberName());
				ps.setDate(5, new Date(m.getMemberBirthDate().getTime()));

				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(INITIALIZATION_DATA_PATH + "/photo/user/" + m.getMemberPhoto() + ".png"));
				String base64Photo = "data:image/png;base64," + Base64.getEncoder().encodeToString(bis.readAllBytes());
				bis.close();

				ps.setString(6, base64Photo);
				ps.addBatch();
			}
			ps.executeBatch();
			ps.close();
		}

		/* === 若 pet 表格中沒有任何一筆資料則新增 === */
		if (!conn.createStatement().executeQuery("SELECT [pet_id] FROM [paw_poster].[dbo].[pet]").next()) {
			// 使用 Gson，讀取 webapp/META-INF/initialization_data/members.json 的資料(內含 pet 資訊)
			BufferedReader br = new BufferedReader(new FileReader(INITIALIZATION_DATA_PATH + "members.json"));
			List<Member> members = gson.fromJson(br, new TypeToken<List<Member>>() {
			}.getType());
			br.close();

			SQL = "INSERT INTO [paw_poster].[dbo].[pet] ([pet_type], [pet_name], [pet_birth_date], [pet_photo], [member_id]) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(SQL);

			// 第一個迴圈讀取所有使用者，第二個迴圈讀取使用者的寵物
			for (Member m : members) {
				for (Pet p : m.getPets()) {
					ps.setString(1, p.getPetType());
					ps.setString(2, p.getPetName());
					ps.setDate(3, new Date(p.getPetBirthDate().getTime()));

					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(INITIALIZATION_DATA_PATH
							+ "/photo/" + p.getPetType() + "/" + p.getPetType() + "-" + p.getPetName() + ".jpg"));
					byte[] b = bis.readAllBytes();
					bis.close();
					ps.setBytes(4, b);

					ps.setInt(5, m.getMemberId());
					ps.addBatch();
				}

			}
			ps.executeBatch();
			ps.close();
		}

		/* === 若 member_pet_like 表格中沒有任何一筆資料則新增 === */
		if (!conn.createStatement().executeQuery("SELECT [like_id] FROM [paw_poster].[dbo].[member_pet_like]").next()) {

			// 使用 Gson、JavaIO，讀取 webapp/META-INF/initialization_data/likes.json 的資料
			BufferedReader br = new BufferedReader(new FileReader(INITIALIZATION_DATA_PATH + "likes.json"));
			List<MemberPetLike> memberPetLikes = gson.fromJson(br, new TypeToken<List<MemberPetLike>>() {
			}.getType());
			br.close();

			SQL = "INSERT INTO [paw_poster].[dbo].[member_pet_like] ([create_date], [member_id], [pet_id]) VALUES (?, ?, ?)";

			PreparedStatement ps = conn.prepareStatement(SQL);

			for (MemberPetLike memberPetLike : memberPetLikes) {
				ps.setDate(1, new Date(memberPetLike.getCreateDate().getTime()));
				ps.setInt(2, memberPetLike.getMember().getMemberId());
				ps.setInt(3, memberPetLike.getPet().getPetId());
				ps.addBatch();
			}

			ps.executeBatch();
			ps.close();
		}
	}

}
