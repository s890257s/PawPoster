package tw.com.eeit.petforum.init;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import tw.com.eeit.petforum.model.bean.Likes;
import tw.com.eeit.petforum.model.bean.Member;
import tw.com.eeit.petforum.model.bean.Pet;
import tw.com.eeit.petforum.util.ConnectionFactory;

@WebListener
public class Initialize implements ServletContextListener {

	private String INITIALIZATION_DATA_PATH = "META-INF/initialization_data/";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();

		// 初始化
		context.setAttribute("root", context.getContextPath());
		context.setAttribute("webName", "寵物論壇");
		context.setAttribute("view", "/WEB-INF/view");

		// 以下為「在資料庫塞入預設值」的程式
		INITIALIZATION_DATA_PATH = context.getRealPath(INITIALIZATION_DATA_PATH);

		try (Connection conn = ConnectionFactory.getConnection()) {

			createDB(conn); // 建立資料庫
			createTableAndInsertData(conn); // 建立Member資料表並加入預設值

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void createDB(Connection conn) throws SQLException {
		// 如果PetForum資料庫不存在，則建立PetForum資料庫
		String SQL = "IF DB_ID('PetForum') IS NULL CREATE DATABASE PetForum";

		Statement state = conn.createStatement();
		state.execute(SQL);
		state.close();
	}

	private void createTableAndInsertData(Connection conn) throws Exception {
		// 如果Member資料表不存在，則建立Member資料表
		String SQL = "IF OBJECT_ID('[PetForum].[dbo].[Member]') IS NULL " + "CREATE TABLE [PetForum].[dbo].[member]("
				+ " [mID] [int] IDENTITY(1,1) PRIMARY KEY NOT NULL," + " [email] [nvarchar](50) NOT NULL,"
				+ " [password] [nvarchar](50) NOT NULL," + " [enabled] [bit] NOT NULL,"
				+ " [level] [nvarchar](10) NOT NULL," + " [mName] [nvarchar](10)," + " [mAge] [int],"
				+ " [address] [nvarchar](50)," + " [mPhoto] [nvarchar](max)" + ")";
		Statement state = conn.createStatement();
		state.execute(SQL);
		state.close();

		// 如果Pet資料表不存在，則建立Pet資料表
		SQL = "IF OBJECT_ID('[PetForum].[dbo].[Pet]') IS NULL " + "CREATE TABLE [PetForum].[dbo].[Pet]("
				+ " [pID] [int] IDENTITY(1,1) PRIMARY KEY NOT NULL," + " [type] [nvarchar](50) NOT NULL,"
				+ " [pName] [nvarchar](50)," + " [pAge] [int]," + " [pPhoto] [varbinary](max),"
				+ " [mID] [int] FOREIGN KEY REFERENCES [PetForum].[dbo].[Member]([mID])" + ")";
		state = conn.createStatement();
		state.execute(SQL);
		state.close();

		// 如果Likes資料表不存在，則建立Likes資料表
		SQL = "IF OBJECT_ID('[PetForum].[dbo].[Likes]') IS NULL " + "CREATE TABLE [PetForum].[dbo].[Likes]("
				+ " [lID] [int] IDENTITY(1,1) PRIMARY KEY NOT NULL," + " [time] [datetime] NOT NULL,"
				+ " [mID] [int] FOREIGN KEY REFERENCES [PetForum].[dbo].[Member]([mID]),"
				+ " [pID] [int] FOREIGN KEY REFERENCES [PetForum].[dbo].[Pet]([pID])" + ")";
		state = conn.createStatement();
		state.execute(SQL);
		state.close();

		// 如果Member資料表中沒有任何一筆資料，則新增。
		if (!conn.createStatement().executeQuery("SELECT [mID] FROM [PetForum].[dbo].[Member]").next()) {
			// 使用Gson、JavaIO，讀取webapp/META-INF/initialization_data/Member.json的資料
			BufferedReader br = new BufferedReader(new FileReader(INITIALIZATION_DATA_PATH + "Member.json"));
			List<Member> mList = new Gson().fromJson(br, new TypeToken<List<Member>>() {
			}.getType());
			br.close();

			SQL = "INSERT INTO [PetForum].[dbo].[Member] ([email], [password], [enabled], [level], [mName], [mAge], [address], [mPhoto]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			// 新增資料到資料表
			PreparedStatement preState = conn.prepareStatement(SQL);
			for (Member m : mList) {
				preState.setString(1, m.getEmail());
				preState.setString(2, m.getPassword());
				preState.setBoolean(3, m.getEnabled());
				preState.setString(4, m.getLevel());
				preState.setString(5, m.getmName());
				preState.setInt(6, m.getmAge());
				preState.setString(7, m.getAddress());

				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(INITIALIZATION_DATA_PATH + "/photo/user/" + m.getmPhoto() + ".png"));
				String base64Photo = "data:image/png;base64," + Base64.getEncoder().encodeToString(bis.readAllBytes());
				bis.close();

				preState.setString(8, base64Photo);
				preState.addBatch();
			}
			preState.executeBatch();
			preState.close();
		}

		// 如果Pet資料表中沒有任何一筆資料，則新增。
		if (!conn.createStatement().executeQuery("SELECT [pID] FROM [PetForum].[dbo].[Pet]").next()) {
			// 使用Gson、JavaIO，讀取webapp/META-INF/initialization_data/Member.json的資料(內含Pet資訊)
			BufferedReader br = new BufferedReader(new FileReader(INITIALIZATION_DATA_PATH + "Member.json"));
			List<Member> mList = new Gson().fromJson(br, new TypeToken<List<Member>>() {
			}.getType());
			br.close();

			SQL = "INSERT INTO [PetForum].[dbo].[Pet] ([type], [pName], [pAge], [pPhoto], [mID]) VALUES (?, ?, ?, ?, ?)";

			PreparedStatement preState = conn.prepareStatement(SQL);

			// 第一個迴圈讀取所有使用者，第二個迴圈讀取使用者的寵物
			for (Member m : mList) {
				for (Pet p : m.getPets()) {
					preState.setString(1, p.getType());
					preState.setString(2, p.getpName());
					preState.setInt(3, p.getpAge());

					BufferedInputStream bis = new BufferedInputStream(new FileInputStream(INITIALIZATION_DATA_PATH
							+ "/photo/" + p.getType() + "/" + p.getType() + "-" + p.getpName() + ".jpg"));
					byte[] b = bis.readAllBytes();
					bis.close();
					preState.setBytes(4, b);

					preState.setInt(5, m.getmID());
					preState.addBatch();
				}

			}
			preState.executeBatch();
			preState.close();
		}

		// 如果Likes資料表中沒有任何一筆資料，則新增。
		if (!conn.createStatement().executeQuery("SELECT [pID] FROM [PetForum].[dbo].[Likes]").next()) {
			Gson gson = new GsonBuilder().setDateFormat("yyyy/MM/dd HH:mm").create();

			// 使用Gson、JavaIO，讀取webapp/META-INF/initialization_data/Likes.json的資料
			BufferedReader br = new BufferedReader(new FileReader(INITIALIZATION_DATA_PATH + "Likes.json"));
			List<Likes> lList = gson.fromJson(br, new TypeToken<List<Likes>>() {
			}.getType());
			br.close();

			SQL = "INSERT INTO [PetForum].[dbo].[Likes] ([time], [mID], [pID]) VALUES (?, ?, ?)";

			PreparedStatement preState = conn.prepareStatement(SQL);

			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm");
			for (Likes l : lList) {
				preState.setString(1, dateFormat.format(l.getTime()));
				preState.setInt(2, l.getMember().getmID());
				preState.setInt(3, l.getPet().getpID());
				preState.addBatch();
			}
			preState.executeBatch();
			preState.close();
		}
	}

}
