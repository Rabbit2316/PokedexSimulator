package pokedex;

//This is the central game class, contains the game loop. This repaints the Panel object ever X nanoseconds, allowing for changes made to display. 
public class Game implements Runnable {

	//Properties
	AnimationHandler animationHandler;
	Controls controls;
	Frame frame;
	private Thread gameThread;
	private final int FPS_SET = 60;//This int determines how many times the Panel repaints per second. 
	
	//Constructor
	public Game() {
		controls = new Controls(this);
		frame = new Frame(this);
		animationHandler = new AnimationHandler(this);
		startGameLoop(); //switch to start the game loop
	}

	//Assigns this class to the gameThread thread and starts it
	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	//Run method for the Thread this class is running on
	@Override
	public void run() {
		double timePerFrame = 1000000000.0 / FPS_SET;//One second in nanotime divided by how many frames we want per second, to give us nanoseconds per frame. 
		

		long previousTime = System.nanoTime();//Logging the time from the last iteration of the loop. 
		long currentTime = System.nanoTime();//Logging the current time to compare against the previous time to determine if it's time to display a new frame
		double deltaF = 0;//We're using this to store the difference between the previous and current time, divided by frames per second.

		while (true) {
			currentTime = System.nanoTime();//Updating the current time
			deltaF += (currentTime - previousTime) / timePerFrame;//Gets how long its been since the last iteration, and adds it to deltaF. If that time is over 1 when divided by time per frame, then at least the time we've set for each frame has passed. 													  
			previousTime = currentTime;//Setting the previous time to the current time
			
			if (deltaF >= 1) {//By running the game loop this way, it prevents the calculations from being messed up from a nanosecond or two slipping through the cracks. It catches up with itself. 
				frame.mainPanel.repaint();//Actually repainting the panel to display changes/animations
				deltaF--;//Removing one from deltaF, keeping any leftover time we may have for the next iteration. 		
			}		
		}
	}
}
