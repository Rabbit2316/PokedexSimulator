package pokedex;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import Pokemon.*;

//This class handles all of the logic for the animations of the program. 
public class AnimationHandler {
	
	//Properties
	Game game;
    public int menuY = 2; //This is the Y coordinate of the menu item the user is currently selecting
    public int selector = 0; //This is a pointer that indicates the index of the pokemon currently selected
    public ArrayList<PokeParent> mons = new ArrayList<>();//Array list of all pokemon
    public int minDisplay = 0;//The index of the first pokemon displayed on the menu currently
    public int maxDisplay = 5;//The index of the last pokemon displayed on the menu currently
	
	//Booleans for button pressed - dictates which version of each button is drawn
	public boolean StartBtnPressed = false;
	public boolean SelectBtnPressed = false;
	public boolean leftArrowPressed = false;
	public boolean rightArrowPressed = false;
	public boolean boxTicked = false;
	public boolean xPressed = false;
	public boolean rtnPressed = false;
	
	//Hitboxes
    public Rectangle2D startBox = new Rectangle(2, 303, 131, 40);
    public Rectangle2D selectBox = new Rectangle(140, 303,131,40);
    public Rectangle2D leftArrowBox = new Rectangle(280, 308,34,28);
    public Rectangle2D rightArrowBox = new Rectangle(330, 308,34,28);
    public Rectangle2D boxBox = new Rectangle(375, 306,32,32);
    Rectangle2D xBox = new Rectangle(420, 308,28,28);
    Rectangle2D returnBox = new Rectangle(460, 308,28,28);

//---- start of UI images ----//
    private  BufferedImage backGround = Toolbox.loadImage("/imgs/background_sliding.png");
    private  BufferedImage infoFrame = Toolbox.loadImage("/imgs/infoFrame.png");
    private  BufferedImage blackBar = Toolbox.loadImage("/imgs/blackBar.png");
    
    private  BufferedImage startBtn = Toolbox.loadImage("/imgs/startBtn.png");
    private  BufferedImage startBtnPsd = Toolbox.loadImage("/imgs/startBtnPsd.png");
    
    private  BufferedImage selectBtn = Toolbox.loadImage("/imgs/selectBtn.png");
    private  BufferedImage selectBtnPsd = Toolbox.loadImage("/imgs/selectBtnPsd.png");

    private  BufferedImage box = Toolbox.loadImage("/imgs/box.png");
    private  BufferedImage boxPsd = Toolbox.loadImage("/imgs/boxPsd.png");
    
    private  BufferedImage xBtn = Toolbox.loadImage("/imgs/x.png");
    private  BufferedImage xBtnPsd = Toolbox.loadImage("/imgs/xPsd.png");
    
    private  BufferedImage rtnBtn = Toolbox.loadImage("/imgs/return.png");
    private  BufferedImage rtnBtnPsd = Toolbox.loadImage("/imgs/returnPsd.png");
    
    private  BufferedImage arrows = Toolbox.loadImage("/imgs/arrows.png");
    private  BufferedImage arrowsPsd = Toolbox.loadImage("/imgs/arrowPsd.png");
    private  BufferedImage rightArrowPsd = arrowsPsd.getSubimage(50, 0, 34, 28);
    private  BufferedImage leftArrowPsd = arrowsPsd.getSubimage(0, 0, 34, 28);
    
    private BufferedImage pokedexSel = Toolbox.loadImage("/imgs/pokedexSelGreen.png");    
    private  BufferedImage menu = Toolbox.loadImage("/imgs/pokedexSel.png");
//---- end of UI images ----//
    
    //Constructor
    public AnimationHandler(Game gameIn) {
    	game = gameIn;
    	popList();
    }
    
    //Reset button booleans
    public void resetButtons() {
    	StartBtnPressed = false;
    	SelectBtnPressed = false;
    	leftArrowPressed = false;
    	rightArrowPressed = false;
    	xPressed = false;
    	rtnPressed = false;
    }
    
//---- Button drawing ----//
    //Start button
     public  void drawStartBtn(Graphics g) {
    	if (StartBtnPressed)
			g.drawImage(startBtnPsd, 2, 303, 131, 40, null);
    	if(!StartBtnPressed)
    		g.drawImage(startBtn, 2, 303, 131, 40, null);
    }
     
     //Select button
     public void drawSelectBtn(Graphics g) {
     	if (SelectBtnPressed)
     		g.drawImage(selectBtnPsd, 140, 303,120,40, null);
     	if(!SelectBtnPressed)
     		g.drawImage(selectBtn, 140, 303,130,40, null);
     }
     
     //Arrows
     public void drawArrows(Graphics g) {
    	g.drawImage(arrows, 280, 308,84,28, null);
    	if (leftArrowPressed)
 			g.drawImage(leftArrowPsd, 281, 308,32,28, null);
    	else if(rightArrowPressed)
     		g.drawImage(rightArrowPsd, 331, 308,32,28, null);
     }
     
     //Tick box
     public void tickBox(Graphics g) {
    	 if(!boxTicked)
    		 g.drawImage(box, 375, 306,32,32, null);
    	 else if(boxTicked) 
    		 g.drawImage(boxPsd, 375, 306,32,32, null);
     }
     
     //X button
     public void drawX(Graphics g) {
    	 if(!xPressed) 
    		 g.drawImage(xBtn, 420, 308,28,28, null);
    	 else if(xPressed)
    		 g.drawImage(xBtnPsd, 420, 308,28,28, null);
     }
     
    //Return button
     public void drawRtn(Graphics g) {
    	 if(!rtnPressed)
    		 g.drawImage(rtnBtn, 460, 308,28,28, null);
    	 else if(rtnPressed)
    		 g.drawImage(rtnBtnPsd, 460, 308,28,28, null);
     } 
     
     //Drawing the menu boxes
     public void drawMenu(Graphics g) { 
    	 for(int i = 0; i<6; i++) {
    		 g.drawImage(menu, 192, 2+(i*50), 303, 42, null);
    	 }
     }   
     
     //Populating the menu with pokemon info/assets
     public void drawMenuInfo(Graphics g) {
    	 g.setColor(Color.white);
    	 for(int ii = 0; ii<6; ii++) {
    		 mons.get(ii+minDisplay).drawIcon(g, 193, (ii*50)-18);
    		 g.drawString(mons.get(ii+minDisplay).getName(), 270, (ii*50)+30);
    	 }
     }
     
     //Drawing all
     public void drawAll(Graphics g) {
    	 //Drawing the background assets
		 g.drawImage(backGround,0, 0, 512, 384, null);
    	 g.drawImage(infoFrame,0, 0, 186, 189, null);
       	 g.drawImage(blackBar, 0, -10, 512, 355, null);
       	
       	 //Drawing the buttons with logic
    	 drawStartBtn(g);
    	 drawSelectBtn(g);
    	 drawArrows(g);
    	 tickBox(g);
    	 drawX(g);
    	 drawRtn(g);
    	 drawMenu(g);
    	 g.drawImage(pokedexSel, 192, menuY, 303, 42, null);//This is the green highlighted menu option asset. Needs to be drawn here. 
    	 drawMenuInfo(g);
     }
//---- end of button drawing ----//
  
     //Populates the pokemon array
     public void popList() {
		 mons.add(new Snivy());
		 mons.add(new Servine());
		 mons.add(new Serperior());
    	 mons.add(new Tepig());
    	 mons.add(new Pignite());
    	 mons.add(new Emboar());
    	 mons.add(new Oshawatt());
    	 mons.add(new Dewott());
    	 mons.add(new Samurott());
    	 mons.add(new Patrat());
    	 mons.add(new Watchog());
     }
     
     //Main paint method
     public  void drawUI(Graphics g) {
    	drawAll(g);	
      	mons.get(selector).drawAni(g);
      }
}
