package pokedex;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;

//This class contains the main panel. This is where mouse inputs are handled. 
public class Panel extends JPanel implements MouseListener{
    
	//Properties
	private Game game;//main game object
    private static Dimension screenSize = new Dimension(512, 384);//Screen dimensions
    
    //Constructor
    public Panel(Game gameIn) {
        game = gameIn;
        setSize(screenSize);
        setBackground(Color.red);
        addMouseListener(this);
    }
    
    //Main paint method
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(game.animationHandler != null) //Makes sure that the animationHandler has been instantiated before it tries to draw anything
        	game.animationHandler.drawUI(g);
    }
   
    //Control handler
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
    	game.controls.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    	game.controls.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
