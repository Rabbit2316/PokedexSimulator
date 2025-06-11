package pokedex;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

//This class contains the frame for the GUI. Key inputs are handled here, mouse inputs are handled in the Panel class
public class Frame extends JFrame implements KeyListener {

	//Properties
	private Game game;
	protected Panel mainPanel;
	private static Dimension screenSize = new Dimension(512, 384);
	
    //Constructor
	public Frame(Game game) {
		//Initialisation
		this.game = game;
		addKeyListener(this);//Adding key listener
            
        //setting up the JPanel
        mainPanel = new Panel(this.game);

        //setting up this frame
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(512, 384));
        setResizable(false);
        setSize(screenSize);
        getContentPane().setLayout(null);
        setVisible(true);
        
        //adding components
        add(mainPanel);  
	}  

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {//Passes key input to the Controls class - specifically the instance of it in the Game class / Object
		game.controls.keyPressed(e);
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
