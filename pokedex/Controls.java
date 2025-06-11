package pokedex;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//This class contains the logic needed to parse and act on inputs from the user, including keyboard and mouse inputs.
public class Controls implements KeyListener, MouseListener{
	
	//Properties
	private Game game;
	private ArrayList<MouseEvent> input = new ArrayList<>(); 
//To prevent inputs from being spanned, Inputs are passed to this array, acted upon, then removed.
//If there is an input already in the array, any other inputs won't be added, ensuring only one input is registered at a time.
	 
	
	//Constructor
	public Controls(Game game) {
		this.game = game;//Passes a direct reference to my main game class so that I can access its properties
	}

	//Method for generating sound on scroll
	private void playSound() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		File file = new File("Audio/Tab.wav");
		AudioInputStream as = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(as);
		clip.start();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP && game.animationHandler != null) {//If up arrow is pressed
			//System.out.println("up pressed");
           
			if(game.animationHandler.menuY >2) {//If the active pokemon isn't the first one being displayed
				game.animationHandler.menuY -= 50;
				game.animationHandler.selector -=1;
				try {
					playSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
			} else {//If the selected box is the first one being displayed, this code cycles through the array without moving it. 
				if(game.animationHandler.selector != 0) {//Runs as long as the active pokemon isnt the first one in the array. 
					game.animationHandler.minDisplay -=1;
					game.animationHandler.maxDisplay -=1;
					game.animationHandler.selector -=1;
					try {
						playSound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
				}
			}
			
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN && game.animationHandler != null) {//This does the same as the above code block, but in the opposite direction
			System.out.println("down pressed");
			if(game.animationHandler.menuY < 252 ) {
				game.animationHandler.menuY += 50;
				game.animationHandler.selector +=1;
				try {
					playSound();
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
			} else {
				if(game.animationHandler.selector < game.animationHandler.mons.size()-1) {
					try {
						playSound();
					} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
						e1.printStackTrace();
					}
					game.animationHandler.minDisplay +=1;
					game.animationHandler.maxDisplay +=1;
					game.animationHandler.selector +=1;
				}
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {}
	
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
    	if(input.size() < 1) {//Controlling inputs, and logic for the buttons - Just verifies coordiantes, and switches booleans
    		input.add(e);
    		System.out.println("X: "+e.getX()+" | Y: "+e.getY());
    		//Start Button
    		if(game.animationHandler.startBox.contains(e.getX(), e.getY())) 
    			game.animationHandler.StartBtnPressed = true;
    		//Select Button
    		else if(game.animationHandler.selectBox.contains(e.getX(), e.getY())) 
    			game.animationHandler.SelectBtnPressed = true;
    		//Arrow Buttons
    		else if(game.animationHandler.leftArrowBox.contains(e.getX(), e.getY())) 
    			game.animationHandler.leftArrowPressed = true;
    		else if(game.animationHandler.rightArrowBox.contains(e.getX(), e.getY())) 
    			game.animationHandler.rightArrowPressed = true;
    		//Check Box
    		else if(game.animationHandler.boxBox.contains(e.getX(), e.getY())) 
    			game.animationHandler.boxTicked = !game.animationHandler.boxTicked;
    		//X Button
			else if(game.animationHandler.xBox.contains(e.getX(), e.getY())) 
				game.animationHandler.xPressed = true;
    		//Return Button
    		else if(game.animationHandler.returnBox.contains(e.getX(), e.getY())) 
				game.animationHandler.rtnPressed = true;
			
    		}
    			
    	
    	input.remove(0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {//Resets the pressed button booleans when the user unclicks their mouse
    	if(input.size() < 1) {
    		input.add(e);
    		game.animationHandler.resetButtons();
    	}
    	input.remove(0);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}

    


