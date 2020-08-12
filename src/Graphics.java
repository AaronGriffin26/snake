import lwjglHelpers.graphic.Renderer;
import lwjglHelpers.graphic.Texture;

import java.util.HashMap;

/**
 * Wrapper class for using OpenGL to render images and text to the screen.
 */
@SuppressWarnings("IntegerDivisionInFloatingPointContext")
public class Graphics {
	private static Renderer renderer;
	private static HashMap<String, Texture> textures = new HashMap<>();

	/**
	 * Must be called before rendering anything.
	 */
	public static void setup() {
		renderer = new Renderer();
		renderer.init();
	}

	public static void cleanup() {
		for(Texture t : textures.values()) {
			t.delete();
		}
	}

	/**
	 * Floods the screen with a solid black color.
	 */
	public static void clear() {
		renderer.clear();
	}

	/**
	 * Renders text center-aligned at a point in the window.
	 *
	 * @param x    Horizontal pixel distance from the left of the window
	 * @param y    Vertical pixel distance from the bottom of the window
	 * @param text Text to render
	 */
	public static void drawText(int x, int y, String text) {
		int width = renderer.getTextWidth(text);
		int height = renderer.getTextHeight(text);
		renderer.drawText(text, x - width / 2, y - height / 2);
	}

	/**
	 * Renders text toward the right of a point in the window.
	 *
	 * @param x    Horizontal pixel distance from the left of the window
	 * @param y    Vertical pixel distance from the bottom of the window
	 * @param text Text to render
	 */
	public static void drawTextLeftAligned(int x, int y, String text) {
		int height = renderer.getTextHeight(text);
		renderer.drawText(text, x, y - height / 2);
	}

	/**
	 * Renders an image center-aligned at a point in the window.
	 *
	 * @param point     Pixel coordinates from bottom-left of the window
	 * @param imagePath File path for image
	 */
	public static void drawTexture(GameCoordinates point, String imagePath) {
		drawTexture(point.getX(), point.getY(), imagePath);
	}

	/**
	 * Renders an image center-aligned at a point in the window.
	 *
	 * @param x         Horizontal pixel distance from the left of the window
	 * @param y         Vertical pixel distance from the bottom of the window
	 * @param imagePath File path for image
	 */
	public static void drawTexture(int x, int y, String imagePath) {
		if(!textures.containsKey(imagePath))
			textures.put(imagePath, Texture.loadTexture(imagePath));
		Texture texture = textures.get(imagePath);
		renderer.begin();
		texture.bind();
		renderer.drawTexture(texture, x - texture.getWidth() / 2f, y - texture.getHeight() / 2f);
		renderer.end();
	}
}
