import lwjglHelpers.SoundBuffer;
import lwjglHelpers.SoundManager;
import lwjglHelpers.SoundSource;

/**
 * Wrapper class for using OpenAL to play simple sound effects.
 */
public class Audio {
	private static SoundManager soundManager = new SoundManager();

	/**
	 * Must be called before playing sounds.
	 */
	public static void setup() {
		try {
			soundManager.init();

			SoundBuffer bufferEat = new SoundBuffer("eat.wav");
			soundManager.addSoundBuffer(bufferEat);
			SoundSource sourceEat = new SoundSource();
			sourceEat.setBuffer(bufferEat.getBufferID());
			soundManager.addSoundSource(Sounds.EAT.toString(), sourceEat);

			SoundBuffer bufferCollideSelf = new SoundBuffer("hit0.wav");
			soundManager.addSoundBuffer(bufferCollideSelf);
			SoundSource sourceCollideSelf = new SoundSource();
			sourceCollideSelf.setBuffer(bufferCollideSelf.getBufferID());
			soundManager.addSoundSource(Sounds.COLLIDE_SELF.toString(), sourceCollideSelf);

			SoundBuffer bufferCollideWall = new SoundBuffer("hit1.wav");
			soundManager.addSoundBuffer(bufferCollideWall);
			SoundSource sourceCollideWall = new SoundSource();
			sourceCollideWall.setBuffer(bufferCollideWall.getBufferID());
			soundManager.addSoundSource(Sounds.COLLIDE_WALL.toString(), sourceCollideWall);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Plays one of the loaded sounds reserved for the game to use.
	 *
	 * @param sound Sound constant
	 */
	public static void playSound(Sounds sound) {
		soundManager.playSoundSource(sound.toString());
	}

	public static void cleanup() {
		soundManager.cleanup();
	}
}
