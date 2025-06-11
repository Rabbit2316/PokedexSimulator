package pokedex;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

//This class contains helper methods, primarily for importing images, maps, and soforth. 
//All sprite maps must be made of 96x96 boxes, with no more than 15 sprites per row - max width of 1440 pixels
public class Toolbox {
	
	//This is a helper method that loads an image
    public static BufferedImage loadImage(String path) {
        InputStream is = Toolbox.class.getResourceAsStream(path);
        try {
            return ImageIO.read(is);
        } catch (IOException e) {
        }
        return null;
    }
	
    //This imports the small menu sprites. Each map is 64x32 and consists of two 32x32 sprites.
    public static BufferedImage[] importMenuMap(String pathName) {
        BufferedImage image = loadImage(pathName);
        BufferedImage[] array = new BufferedImage[2];
        
        //This cuts the map up according to the mentioned dimensions, and populates an array with them.
        for(int i = 0; i<array.length; i++) {
        		array[i] = image.getSubimage(i*32, 0, 32, 32);
        }
        return array;
    }
    
    //This method is used to create animation maps. Sprite maps must be no longer than 15 sprites across, with each sprite existing in a 96x96 box. Line the sprites up with the bottom left corner of the 96x96 box
    public static BufferedImage[] importMap(String pathName, int arraySize, int framesInRow, int rowCount) {
        BufferedImage image = loadImage(pathName);//Gets the image
        BufferedImage[] array = new BufferedImage[arraySize];
        
        //This cuts up the image and adds the subimages to an array. 
        for(int ii = 0; ii<rowCount; ii++) {//This loop iterates through each row
            for(int i = 0; i<framesInRow; i++) {//This loop iterates through each frame in a row. 
                if((ii*framesInRow)+i < arraySize)//Takes how many images have been added to the array and makes sure that it should still be functioning/there are more sprites in the map
                    array[(framesInRow*ii)+i] = image.getSubimage(i*96, ii*96, 96, 96);
            }
        }
        return array;
    }
}