/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Animation Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the Animation Interface.
 */


package invaders.rendering;

import javafx.scene.image.Image;

public interface Animation {
    public String getName();
    public Image getCurrentFrame();
    public void next();
}
