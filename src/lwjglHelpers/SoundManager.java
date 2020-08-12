package lwjglHelpers;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.openal.ALC10.*;

public class SoundManager {
	private long device;
	private long context;
	private final List<SoundBuffer> soundBufferList;
	private final Map<String, SoundSource> soundSourceMap;

	public SoundManager() {
		soundBufferList = new ArrayList<>();
		soundSourceMap = new HashMap<>();
	}

	public void init() throws Exception {
		//		device = alcOpenDevice("OpenAL Soft");
		device = alcOpenDevice((ByteBuffer)null);
		if(device == 0)
			throw new IllegalStateException("Failed to open the default OpenAL device.");
		ALCCapabilities deviceCaps = ALC.createCapabilities(device);
		context = alcCreateContext(device, (IntBuffer)null);
		if(context == 0)
			throw new IllegalStateException("Failed to create OpenAL context.");
		if(!alcMakeContextCurrent(context))
			throw new IllegalStateException("Failed to create OpenAL context.");
		AL.createCapabilities(deviceCaps);

		//		alListener3f(AL_POSITION, 0f, 0f, 1f);
		//		alListener3f(AL_VELOCITY, 0f, 0f, 0f);
		//		alListenerfv(AL_ORIENTATION, new float[] { 0f, 0f, 1f, 0f, 1f, 0f });
	}

	public void addSoundSource(String name, SoundSource soundSource) {
		soundSourceMap.put(name, soundSource);
	}

	public void playSoundSource(String name) {
		SoundSource soundSource = soundSourceMap.get(name);
		if(soundSource != null) {
			soundSource.stop();
			soundSource.play();
		}
	}

	public void addSoundBuffer(SoundBuffer soundBuffer) {
		soundBufferList.add(soundBuffer);
	}

	public void cleanup() {
		for(SoundSource s : soundSourceMap.values()) {
			s.cleanup();
		}
		soundSourceMap.clear();
		for(SoundBuffer b : soundBufferList) {
			b.cleanup();
		}
		soundBufferList.clear();
		if(context != 0)
			alcDestroyContext(context);
		if(device != 0)
			alcCloseDevice(device);
	}
}
