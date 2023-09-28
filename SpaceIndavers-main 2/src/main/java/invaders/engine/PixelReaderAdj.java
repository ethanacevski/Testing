/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The PixelReaderAdj Class purpose is to change the colour of the Player based on the provided
 * colour in the config file. This allows our Player (CoreCannon) to take the colour of any Colour specified in Java Color
 *
 */

package invaders.engine;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class PixelReaderAdj {

    private Image sprite;
    private String colour;

    /** Constructor takes in the sprite to be changed along with the colour which is it to be changed to. */
    public PixelReaderAdj(Image sprite, String colour){
        this.sprite = sprite;
        this.colour = colour;
    }

    /** spriteColorChange is responsible for changing the standard image to the desired colour in the config. */
    public Image spriteColorChange(){

        double width = sprite.getWidth();       // Get sprite width.
        double height = sprite.getHeight();     // Get sprite height.

        WritableImage modifiableSprite = new WritableImage((int) width, (int) height); // Creates WriteableImage.

        PixelReader pixReader = sprite.getPixelReader();                // Creates PixelReader which reads each colour pixel.
        PixelWriter pixWriter = modifiableSprite.getPixelWriter();      // Creates PixelWriter for changing each pixel colour.

        Color originalCol = pixReader.getColor((int)width/2,(int)height/2); // Gets current pixel colour of Player.
        // Note: this is required as we do not want to change black pixels in the PNG only the green pixels.

        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                Color currentColour = pixReader.getColor(i,j);          // Reads current pixel colour.
                if(hueInRange(currentColour,originalCol,30)){     // Used to check the current pixel is green not black to change.
                    Color newColour = Color.valueOf(colour);            // Gets new colour based on provided String in constructor.
                    pixWriter.setColor(i, j, newColour);                // Sets current pixel to new colour.
                }
            }
        }
        return modifiableSprite;                                        // Returns the modified sprite
    }

    /** hueInRange is responsible checking that the current pixel colour within a specified range based off the
     * specified "originalCol" */
    public boolean hueInRange (Color color, Color newColour, double range){
        double hueDiff = Math.abs(color.getHue() - newColour.getHue()) * 360;
        return hueDiff <= range;
    }
}
