package mar21.resources;

import java.util.HashMap;
import java.util.Objects;

import javafx.scene.image.Image;

public class ResourceLoader {
	
	private static HashMap<String, Image> resourceMap = new HashMap<>();
	
	public static void load(String... paths) {
		for (String path : paths) {
			Image image = new Image(ClassLoader.class.getResourceAsStream(path));
			if (image.isError()) {
				System.err.println("Missing texture " + path);
			}
			
			resourceMap.put(strip(path), image);
		}
	}

	public static Image getTexture(String id) {
		return resourceMap.get(Objects.requireNonNull(id));
	}
	
	private static String strip(String path) {
		if (Objects.isNull(path)) {
			throw new NullPointerException();
		}
		
		if (path.contains(".")) {
			path = path.substring(0, path.lastIndexOf("."));
		}
		
		if (path.contains("/")) {
			path = path.substring(path.lastIndexOf("/") + 1);
		}
		
		return path;
	}
	
}
