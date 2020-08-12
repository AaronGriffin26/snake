package lwjglHelpers;

import org.lwjgl.BufferUtils;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.File;
import java.nio.ByteBuffer;

import static org.lwjgl.openal.AL10.*;

public class SoundBuffer {
	private final int bufferID;

	public SoundBuffer(String file) throws Exception {
		bufferID = alGenBuffers();
		AudioInputStream stream = AudioSystem.getAudioInputStream(new File(file));
		AudioFormat format = stream.getFormat();
		int openALFormat = switch(format.getChannels()) {
			case 1 -> switch(format.getSampleSizeInBits()) {
				case 8 -> AL_FORMAT_MONO8;
				case 16 -> AL_FORMAT_MONO16;
				default -> -1;
			};
			case 2 -> switch(format.getSampleSizeInBits()) {
				case 8 -> AL_FORMAT_STEREO8;
				case 16 -> AL_FORMAT_STEREO16;
				default -> -1;
			};
			default -> -1;
		};
		byte[] bytes = stream.readAllBytes();
		ByteBuffer data = BufferUtils.createByteBuffer(bytes.length).put(bytes);
		data.flip();
		alBufferData(bufferID, openALFormat, data, (int)format.getSampleRate());
	}

	public int getBufferID() {
		return bufferID;
	}

	public void cleanup() {
		alDeleteBuffers(bufferID);
	}
}
