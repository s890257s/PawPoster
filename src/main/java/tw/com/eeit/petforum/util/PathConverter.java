package tw.com.eeit.petforum.util;

public class PathConverter {

	public static String convertToWebInfPath(String path) {
		final String PREFIX = "WEB-INF/view/";
		final String SUFFIX = ".jsp";

		return PREFIX + path + SUFFIX;
	}

}
