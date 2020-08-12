package lwjglHelpers;

import static org.lwjgl.openal.AL11.*;

public class SoundSource {
	private final int sourceID;

	public SoundSource() {
		sourceID = alGenSources();
		alSourcef(sourceID, AL_PITCH, 1f);
		alSourcef(sourceID, AL_GAIN, 1f);
		alSource3f(sourceID, AL_POSITION, 0f, 0f, 0f);
		alSource3f(sourceID, AL_VELOCITY, 0f, 0f, 0f);
		alSourcei(sourceID, AL_LOOPING, AL_FALSE);
	}

	public void setBuffer(int bufferID) {
		stop();
		alSourcei(sourceID, AL_BUFFER, bufferID);
	}

	public void setGain(float gain) {
		alSourcef(sourceID, AL_GAIN, gain);
	}

	public void setProperty(int param, float value) {
		alSourcef(sourceID, param, value);
	}

	public void play() {
		alSourcePlay(sourceID);
	}

	public boolean isPlaying() {
		return alGetSourcei(sourceID, AL_SOURCE_STATE) == AL_PLAYING;
	}

	public void pause() {
		alSourcePause(sourceID);
	}

	public void stop() {
		alSourceStop(sourceID);
	}

	public void cleanup() {
		stop();
		alDeleteSources(sourceID);
	}
}
