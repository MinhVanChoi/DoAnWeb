package vn.iotstar.utils;

import java.text.Normalizer;

public class Constain {
	
	public static String generateSlug(String title) {
        title = Normalizer.normalize(title, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
//        title = removeVietnameseAccents(title);
        title = title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
		return title;
	}
	
	
	public static final String SESSION_USERNAME = "username";
	public static final String COOKIE_REMEMBER = "username";

	public static class Path {
		public static final String REGISTER = "/views/register.jsp";
		
	}
	 
	

	public static final String UPLOAD_DIRECTORY = "C:/Van/uploads";
	 public static final String DEFAULT_FILENAME = "default.file";
	 
	 
}
