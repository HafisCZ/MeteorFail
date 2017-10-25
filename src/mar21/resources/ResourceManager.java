package mar21.resources;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourceManager {

	/**
	 * 
	 */
	private static ResourceManager resourceManager;
	
	/**
	 * 
	 * @return
	 */
	public static ResourceManager requestInstance() {
		if (resourceManager == null) {
			resourceManager = new ResourceManager();
		}
		
		return resourceManager;
	}
	
	/**
	 * 
	 */
	private HashMap<String, Image> resources = new HashMap<String, Image>();
	
	/**
	 * 
	 * @param paths
	 */
	public void loadFiles(String... paths) {
		for (String path : paths) {
			resources.put(strip(path), new Image(ClassLoader.class.getResourceAsStream(path)));
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param width
	 * @param height
	 * @return
	 */
	public ImageView buildImageView(String name, double width, double height) {
		return new ImageView() {
			{
				setImage(getResource(name));
				setFitWidth(width);
				setFitHeight(height);
			}
		};
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public Image getResource(String name) {
		return resources.get(name);
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	private static String strip(String path) {
		if (path.contains(".")) {
			path = path.substring(0, path.lastIndexOf("."));
		}
		
		if (path.contains("/")) {
			path = path.substring(path.lastIndexOf("/") + 1);
		}
		
		return path;
	}
}
