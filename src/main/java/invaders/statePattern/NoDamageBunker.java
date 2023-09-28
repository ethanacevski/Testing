/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The NoDamageBunker class is responsible for returning the image of the Bunker when the handle method
 * is called within the BunkerState Interface.
 *
 * Participant: The NoDamageBunker is a ConcreteState participant within the State Design Pattern.
 */

package invaders.statePattern;

import javafx.scene.image.Image;

import java.io.File;

public class NoDamageBunker implements BunkerState {

    private StateObject bunker;

    @Override
    public void handle(StateObject bunker) {
        bunker.setImage((new Image(new File("src/main/resources/" +
                "player/Bunker.png").toURI().toString())));
        bunker.setState(this);


    }
}




//@Override
//public Renderable changeColour(Renderable bunker) {
//    BunkerBuilder builder = new BunkerBuilder();
//    Director director = new Director();
//    director.buildBunker(builder);
//    builder.setPosition(bunker.getPosition());
//    builder.setSize(bunker.getWidth(),bunker.getHeight());
//    builder.setLayer(bunker.getLayer());
//    builder.setHealth(((Damagable)bunker).getHealth()-1);
//    builder.setAlive((bunker.getMarked()));
//    builder.setSprite(new Image(new File("src/main/resources/" +
//            "player/Bunker.png").toURI().toString()));
//    return builder.build();
//}