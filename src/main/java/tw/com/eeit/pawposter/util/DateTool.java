package tw.com.eeit.pawposter.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTool {

	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public static Date getDefaultDate() {
		Date defaultDate = new Date();
		defaultDate.setTime(0);
		return defaultDate;
	}
}
