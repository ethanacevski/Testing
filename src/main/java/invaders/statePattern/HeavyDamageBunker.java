/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The HeavyDamageBunker class is responsible for returning the image of the Bunker when the handle method
 * is called within the BunkerState Interface.
 *
 * Participant: The HeavyDamageBunker is a ConcreteState participant within the State Design Pattern.
 */
package invaders.statePattern;

import javafx.scene.image.Image;

import java.io.File;

public class HeavyDamageBunker implements BunkerState{
    private StateObject bunker;

    @Override
    public void handle(StateObject bunker) {
        bunker.setImage((new Image(new File("src/main/resources/" +
                "player/BunkerRed.png").toURI().toString())));
        bunker.setState(this);
    }
}
