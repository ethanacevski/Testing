/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The StateObject Interface is utilised to create a list objects which are able to call the defined
 *          methods within the Interface. By doing so, we remove the dependency of the GameEngine on another Class as
 *          we can cast the Bunkers to StateObjects since the BunkerProduct class implements the StateObject interface.
 *          Meaning the code better adheres to SOLID principles.
 */

package invaders.statePattern;

import javafx.scene.image.Image;

public interface StateObject {

    public void setImage(Image sprite);
    public void setState(BunkerState state);


}
