/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Renderable Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the Renderable Interface.
 * 		    Renderable represents something that can be rendered.
 */

package invaders.rendering;

import invaders.physics.Vector2D;
import javafx.scene.image.Image;

public interface Renderable {

    public Image getImage();

    public double getWidth();
    public double getHeight();

    public Vector2D getPosition();

    public Renderable.Layer getLayer();

    public boolean getMarked();

    /**
     * The set of available layers
     */
    public static enum Layer {
        BACKGROUND, FOREGROUND, EFFECT
    }
}
