package tw.com.eeit.pawposter.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 產生連線的靜態工具類別。
 */
public class ConnectionFactory {

	private static final Logger log = Logger.getLogger(ConnectionFactory.class.getName());

	/**
	 * 使用 JNDI 尋找 DataSource，取得連線物件後回傳
	 * 
	 * @return Connection 連線物件
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			InitialContext context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/MSSQL");
			conn = ds.getConnection();
		} catch (NamingException e) {
			log.severe("找不到 JNDI 資源，請確保 context.lookup 內的資源名稱與 META-INF/context.xml 中的 <Resource> 名稱一致。");
			e.printStackTrace();
		} catch (SQLException e) {
			log.severe("MSSQL 連線失敗，請檢查 META-INF/context.xml 中的 <Resource> 設定值。");
			e.printStackTrace();
		}
		return conn;
	}

}
