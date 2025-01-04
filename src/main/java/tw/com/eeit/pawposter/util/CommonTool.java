package tw.com.eeit.pawposter.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.util.Date;
import java.util.logging.Logger;

public class CommonTool {

	private static final Logger log = Logger.getLogger("CommonTool");

	public static Date getDefaultDate() {
		Date defaultDate = new Date();
		defaultDate.setTime(0);
		return defaultDate;
	}

	public static java.sql.Date convertUtilToSqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	public static String getMimeType(byte[] imageBytes) {

		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);

			String mimeType = URLConnection.guessContentTypeFromStream(bais);

			bais.close();

			return mimeType;
		} catch (IOException e) {
			log.warning("讀取圖片 mime-type 出錯，使用預設 image/png");
			return "image/png";
		}

	}
}
