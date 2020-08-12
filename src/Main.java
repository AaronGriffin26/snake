import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;

public class Main {
	/**
	 * Determines how many display refreshes it waits before the game continues.
	 * Set to 0 to disable.
	 */
	private static final int VSYNC_INTERVAL = 1;
	/**
	 * Limits the frames per second of the game. Set to a non-positive number to disable.
	 */
	private static final int FPS_LIMIT = 0;
	/**
	 * Maximum amount of time that can pass between frames without freezing the game.
	 * Ignored when vsync is enabled.
	 */
	private static final double MAX_DELTA_TIME = 0.125;

	public static void main(String[] args) {
		glfwSetErrorCallback(GLFWErrorCallback.createPrint(System.err));
		if(!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW\n");
		String title = "Snake";

		int m_width = 640; // width of the window
		int m_height = 640; // height of the window

		glfwDefaultWindowHints(); // Loads GLFW's default window settings
		glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3); // Sets OpenGL requirement to 3.2
		glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

		long window = glfwCreateWindow(m_width, m_height, title, 0, 0); // Does the actual window creation
		if(window == 0) {
			glfwTerminate();
			throw new RuntimeException("Failed to create window");
		}
		Input.setWindowID(window);

		GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		assert vidmode != null;
		glfwSetWindowPos(window,
				(vidmode.width() - 640) / 2,
				(vidmode.height() - 640) / 2
		);
		glfwMakeContextCurrent(window); // glfwSwapInterval needs a context on the calling thread, otherwise will cause NO_CURRENT_CONTEXT error
		GL.createCapabilities(); // Will let lwjgl know we want to use this context as the context to draw with

		glfwSwapInterval(VSYNC_INTERVAL); // How many draws to swap the buffer
		glfwShowWindow(window); // Shows the window

		glfwPollEvents();
		Graphics.setup();
		Audio.setup();

		Game game = new Game(m_width, m_height); // game object

		boolean windowClosed = false;
		double targetTime = glfwGetTime();
		while(!windowClosed) {
			glfwPollEvents();
			if(Input.spaceIsPressed())
				game = new Game(m_width, m_height);
			game.update();
			Graphics.clear();
			game.render();
			glfwSwapBuffers(window);
			if(FPS_LIMIT > 0) {
				targetTime += 1.0 / FPS_LIMIT;
				double newTime = glfwGetTime();
				if(newTime > targetTime + MAX_DELTA_TIME)
					targetTime = newTime;
				while(glfwGetTime() < targetTime) {
					Thread.yield();
					try {
						Thread.sleep(0, 100);
					}
					catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			}
			else
				targetTime = glfwGetTime();
			windowClosed = glfwWindowShouldClose(window);
			if(Input.backspaceIsHeld())
				windowClosed = true;
		}
		Graphics.cleanup();
		Audio.cleanup();
	}
}