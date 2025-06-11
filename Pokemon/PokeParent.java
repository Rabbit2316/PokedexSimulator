package Pokemon;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pokedex.Toolbox;

//This is the parent class for Pokemon objects.
public abstract class PokeParent {
	
	public boolean oneAnimation = false;
	
	//Properties
	private String name;
	private int height;
	private int weight;
	private String bio;
	private BufferedImage[] menuSprite;
	private BufferedImage[] normalAniMap;
	private BufferedImage[] specialAniMap;
	private BufferedImage[] dexAniMap;
	
	//Animation variables
	public int aniIndex = 0;//Points towards the sprite that should be displayed for the animation
	protected int aniSpeed = 6;//How many frames I want each animation sprite to last. 
	private int aniCounter = 0;//Counts frames to determine when the index should be increased/reset
	
	//X and Y coordinates of the animated sprite
	protected int xPos;
	protected int yPos;
	
	//These are used for controlling pokemon with more than one animation to play
	private int normalAniCounter = 0;//Counts how many times a mon's normal animation has been played
	protected boolean normalAniState = true;//Switch that determines if the normal or special animation should be playing
	private int timesNormalAniRan;
	
	//This is used to control the menu sprite animations
	private int iconCounter, iconIndex = 0;
	
//----notes----//
// timesNormalAniRan = number of times for the normal animation to run before the special animation plays. 
// normalSize = total number of sprites in normal animation map. 
// normalFramesInRow = number of frames per row for the normal animation map. If normalSize > 15, then this number is always 15.
// the same rules apply for the special animation variables of the same name. 
//---- ----//
	
	//Constructor
	public PokeParent(Boolean oneAni, String nameIn, int heightIn, int weightIn, String bioIn, int xIn, int yIn, int timesNormalAniRan, int normalSize, int normalFramesInRow, int specialSize, int specialFramesInRow) {
		this.oneAnimation = oneAni;
		this.name = nameIn;
		this.height = heightIn;
		this.weight = weightIn;
		this.bio = bioIn;
		xPos = xIn;
		yPos = yIn;
		this.timesNormalAniRan = timesNormalAniRan;
		if(!oneAni)
			importAnis(normalSize, normalFramesInRow, specialSize, specialFramesInRow);
		else {
			this.normalAniMap = Toolbox.importMap("/imgs/"+this.name+"/"+this.name+"AniNormal.png", normalSize, normalFramesInRow, 15);
			this.dexAniMap = Toolbox.importMenuMap("/imgs/"+this.name+"/"+this.name+"Icon.png");
		}
	}
	
//---- notes ----//
//Note that the naming conventions for the maps must be followed - and the pokemon's name property must be correct (but is not case sensitive)
//To ensure correct importing, follow these conventions:
//*pokemonName*AniNormal , *pokemonName*AniSpecial , *pokemonName*Icon
//---- ----//
	
	//This method imports the animation maps. I set it up this way so that you don't have to specify a path in the constructor which is messy. 
	private void importAnis(int size1, int framesInRow1, int size2, int framesInRow2) {
		//System.out.println("/imgs/"+this.name+"/"+this.name+"AniNormal");//Debugger for making sure file paths are correct
		this.normalAniMap = Toolbox.importMap("/imgs/"+this.name+"/"+this.name+"AniNormal.png", size1, framesInRow1, 15);
		this.specialAniMap = Toolbox.importMap("/imgs/"+this.name+"/"+this.name+"AniSpecial.png", size2, framesInRow2, 15);
		this.dexAniMap = Toolbox.importMenuMap("/imgs/"+this.name+"/"+this.name+"Icon.png");
	}
	
	//Getters
	public String getName() {
		return name;
	}

	public int getHeight() {
		return height;
	}

	public int getWeight() {
		return weight;
	}

	public String getBio() {
		return bio;
	}

	public BufferedImage[] getMenuSprite() {
		return menuSprite;
	}

	public BufferedImage[] getSpriteMap() {
		return normalAniMap;
	}
	//end of getters
	
	//Basic animation method to simply run one animationMap logic. Not used currently, but would be used for sprites with only one animation
	//This method runs once every frame.
	public void animate() {
		//System.out.println(aniIndex);//Debugging for making sure the index is functioning correctly
		aniCounter++;//Increments by one every frame.
		if(aniCounter >= aniSpeed) {//If the number of frames I want per animation sprite has passed
			aniCounter = 0;//Reset the counter
			aniIndex++;//Increase the index by one to move to the next sprite	
		}
		if(aniIndex >= this.getSpriteMap().length) //If we reach the end of the sprite map
			aniIndex = 0;//Reset the index
	}
	
	//This is an alternative animate method that controls the logic if a mon has a normal and special animation. More than two animations is not supported and would have to involve an Override of this method
	public void animate(int timesNormalAniRan) {//This argument is set in the constructor and determines how many times the normal animation should play before the special animation does. 
		//System.out.println(aniIndex);
		aniCounter++;//This logic is the same as above.
		if(aniCounter >= aniSpeed) {
			aniCounter = 0;
			aniIndex++;
			
			if(normalAniState == false) {//If we should be playing the special animation
				if(aniIndex >= this.specialAniMap.length) {//If we reach the end of the special animation
					normalAniState = true;
					normalAniCounter = 0;
					aniIndex = 0;
				}
			} else if (normalAniState == true) {//If we should be playing the normal animation
				if(aniIndex >= this.normalAniMap.length) {//If we reach the end of the normal animation
					if(normalAniCounter < timesNormalAniRan) {//If there is another iteration of the normal animation to play
						normalAniCounter++;
					} else if(normalAniCounter >= timesNormalAniRan-1) {//If it's time to switch to special animation -- -1 is here because I used base0 for the normalAniCounter variable
						normalAniState = false;
					}
					aniIndex = 0;
				}
			}
		}
	}
	
	//This method controls the drawing portion of the animations. This is called every frame.
	public void drawAni(Graphics g) {
		if(!oneAnimation)
			this.animate(this.timesNormalAniRan);//Calls the logic method
		else
			this.animate();
		
		//If we should be drawing the normal animation
		if(normalAniState == true)
			g.drawImage(normalAniMap[aniIndex], xPos, yPos, 192, 192, null);
		else if(normalAniState == false)//If we should be drawing the special animation
			g.drawImage(specialAniMap[aniIndex], xPos, yPos, 192, 192, null);
	}
	
	//This method is similar to the above animation logic methods, but for the menu sprites. 
	public void animateIcon() {
		iconCounter++;
		if(iconCounter > 50) {
			iconCounter = 0;
			iconIndex++;
		}
		if(iconIndex >= 2) {
			iconIndex = 0;
		}
	}
	
	//Same as above, but for the drawing. 
	public void drawIcon(Graphics g, int x, int y) {
		this.animateIcon();
		g.drawImage(dexAniMap[iconIndex], x, y, 64, 64, null);
	}
}
