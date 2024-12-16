package tw.com.eeit.pawposter.init;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
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

import tw.com.eeit.pawposter.model.po.Member;
import tw.com.eeit.pawposter.model.po.MemberPetLike;
import tw.com.eeit.pawposter.model.po.Pet;
import tw.com.eeit.pawposter.service.MemberService;
import tw.com.eeit.pawposter.service.PetService;
import tw.com.eeit.pawposter.util.ConnectionFactory;

@WebListener
public class Initialize implements ServletContextListener {

	private String INITIALIZATION_DATA_PATH = "META-INF/initialization_data";
	private Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd").create();

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		// 初始化
		context.setAttribute("root", context.getContextPath());
		context.setAttribute("webName", "PawPoster");
		context.setAttribute("component", "/WEB-INF/view/component");

		// 以下為「在資料庫塞入預設值」的程式
		INITIALIZATION_DATA_PATH = context.getRealPath(INITIALIZATION_DATA_PATH) + "\\";

		try (Connection conn = ConnectionFactory.getConnection()) {

			createDB(conn); // 建立資料庫
			createTables(conn); // 建立表格

			insertDefaultMembers(); // 加入 member 表格預設值
			insertDefaultPets();// 加入 pet 表格預設值
			insertDefaultMemberPetLikes(); // 加入 member_pet_like 表格預設值
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

	/* 若表格不存在則建立 */
	private void createTables(Connection conn) throws SQLException {
		String SQL;

		/* === 建立 member 表格 === */
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

		/* === 建立 pet 表格 === */
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

		/* === 建立 member_pet_like 表格 === */
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

	}

	private void insertDefaultMembers() throws Exception {
		MemberService memberService = new MemberService();

		/* === 只要 member 表格有任一筆資料，終止程式 === */
		if (memberService.countMember() != 0) {
			return;
		}

		// 取得 members.json 檔案路徑
		Path membersFilePath = Paths.get(INITIALIZATION_DATA_PATH + "members.json");

		// 使用 Files API 直接讀取路徑為字串(json 格式)
		String jsonString = Files.readString(membersFilePath);

		// 解析 json 格式字串為 List<member> 物件
		List<Member> members = gson.fromJson(jsonString, new TypeToken<List<Member>>() {
		}.getType());

		// 逐條讀取 member 的 photo
		for (Member m : members) {
			Path photoPath = Paths.get(INITIALIZATION_DATA_PATH + "\\photo\\user\\" + m.getMemberPhoto());
			byte[] photoBody = Files.readAllBytes(photoPath);

			String base64Photo = Base64.getEncoder().encodeToString(photoBody);

			m.setMemberPhoto(base64Photo);
		}

		// 批次新增 member 資料
		memberService.insertMembers(members);
	}

	private void insertDefaultPets() throws Exception {
		PetService petService = new PetService();

		/* === 只要 pet 表格有任一筆資料，終止程式 === */
		if (petService.countPet() != 0) {
			return;
		}

		List<Pet> pets = gson.fromJson(Files.readString(Paths.get(INITIALIZATION_DATA_PATH + "pets.json")),
				new TypeToken<List<Pet>>() {
				}.getType());

		// 設定圖片
		for (Pet p : pets) {
			Path photoPath = Paths.get("%s\\photo\\%s\\%s-%s.jpg".formatted(INITIALIZATION_DATA_PATH, p.getPetType(),
					p.getPetType(), p.getPetName()));

			p.setPetPhoto(Files.readAllBytes(photoPath));
		}

		petService.insertPets(pets);
	}

	private void insertDefaultMemberPetLikes() throws IOException {
		MemberService memberService = new MemberService();

		/* === 只要 member_pet_like 表格有任一筆資料，終止程式 === */
		if (memberService.countMemberPetLike() != 0) {
			return;
		}

		List<MemberPetLike> memberPetLikes = gson.fromJson(
				Files.readString(Paths.get(INITIALIZATION_DATA_PATH + "likes.json")),
				new TypeToken<List<MemberPetLike>>() {
				}.getType());

		// 批次新增 member 資料
		memberService.insertLikes(memberPetLikes);

	}

}
