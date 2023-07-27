package tw.com.eeit.petforum.util;

public class PathConverter {

	public static String convertToWebInfPathForFrontend(String path) {
		StringBuffer s = new StringBuffer(path);
		s.insert(0, "WEB-INF/view/frontend/");
		s.append(".jsp");
		return s.toString();
	}

	public static String convertToWebInfPathForBackend(String path) {
		StringBuffer s = new StringBuffer(path);
		s.insert(0, "/WEB-INF/view");
		s.append(".jsp");
		return s.toString();
	}
}
