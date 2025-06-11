package pokedex;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Main class, contains the main method as well as the background thread/code for the background music
public class Main {

	//Main method
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new Game();
		startMusic();
	}
	
	//Starts the background music. 
	public static void startMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File file = new File("../src/Audio/background.wav");
		AudioInputStream as = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(as);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
