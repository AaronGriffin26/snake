import static org.lwjgl.glfw.GLFW.*;

/**
 * Wrapper class for using GLFW to get keyboard input.
 */
public class Input {
	private static long window;
	private static boolean previousSpace = false;

	/**
	 * Sets the window context to monitor for keyboard input.
	 *
	 * @param id Window instance ID
	 */
	public static void setWindowID(long id) { window = id; }

	/**
	 * Tells if the Left Arrow key is currently pushed down.
	 *
	 * @return True if held down, False otherwise
	 */
	public static boolean leftIsHeld() { return (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS); }

	/**
	 * Tells if the Up Arrow key is currently pushed down.
	 *
	 * @return True if held down, False otherwise
	 */
	public static boolean upIsHeld() { return (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS); }

	/**
	 * Tells if the Right Arrow key is currently pushed down.
	 *
	 * @return True if held down, False otherwise
	 */
	public static boolean rightIsHeld() { return (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS); }

	/**
	 * Tells if the Down Arrow key is currently pushed down.
	 *
	 * @return True if held down, False otherwise
	 */
	public static boolean downIsHeld() { return (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS); }

	/**
	 * Tells if the Spacebar was just pushed down. This method returns false if it continues to be held.
	 *
	 * @return True if just pressed, False otherwise
	 */
	public static boolean spaceIsPressed() {
		boolean newPress = (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS);
		if(!previousSpace && newPress) {
			previousSpace = true;
			return true;
		}
		previousSpace = newPress;
		return false;
	}

	/**
	 * Tells if the Backspace key is currently pushed down.
	 *
	 * @return True if held down, False otherwise
	 */
	public static boolean backspaceIsHeld() { return (glfwGetKey(window, GLFW_KEY_BACKSPACE) == GLFW_PRESS); }
}