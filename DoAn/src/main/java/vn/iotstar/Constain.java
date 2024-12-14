package vn.iotstar;

import java.text.Normalizer;

public class Constain {
	public static String generateSlug(String title) {
        title = Normalizer.normalize(title, Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
//        title = removeVietnameseAccents(title);
        title = title.toLowerCase().replaceAll("[^a-z0-9\\s]", "").replaceAll("\\s+", "-");
		return title;
	}
}
