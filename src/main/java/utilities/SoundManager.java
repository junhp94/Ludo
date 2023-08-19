package utilities;
import java.io.*;
import javax.sound.sampled.*;
import java.util.*;

public class SoundManager {
    private static Clip currentClip;
    private static FloatControl volumeControl;
    public static void playSoundEffect(String soundEffectFilePath, float volume){
        try{
            File soundEffectFile = new File(soundEffectFilePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundEffectFile);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.open(audioStream);
            audioClip.start();

            currentClip = audioClip;

            volumeControl = (FloatControl) currentClip.getControl(FloatControl.Type.MASTER_GAIN);

            setVolume(volume);
        } catch(UnsupportedAudioFileException | IOException | LineUnavailableException e){
            e.printStackTrace();
        }
    }
    public static void stopSound(){
        if(currentClip!=null&&currentClip.isRunning()){
            currentClip.stop();
        }
    }

    public static void setVolume(float volume) {
        if (currentClip != null) {
            if (volume < 0.0f) {
                volume = 0.0f;
            } else if (volume > 1.0f) {
                volume = 1.0f;
            }

            if (volumeControl == null) {
                System.err.println("Volume control not initialized.");
                return;
            }

            float minVolume = volumeControl.getMinimum();
            float maxVolume = volumeControl.getMaximum();
            float range = maxVolume - minVolume;
            float scaledVolume = (range * volume) + minVolume;
            volumeControl.setValue(scaledVolume);
        }
    }

    public static float getVolume() {
        if (volumeControl != null) {
            float minVolume = volumeControl.getMinimum();
            float maxVolume = volumeControl.getMaximum();
            float currentVolume = volumeControl.getValue();
            return (currentVolume - minVolume) / (maxVolume - minVolume);
        }
        return 0.5f; // Default to 0.5 (50% volume) if the volume control is not initialized
    }
}
