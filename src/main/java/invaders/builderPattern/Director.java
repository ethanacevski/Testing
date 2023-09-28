/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Direction is Director Participant in the Builder Design Pattern. It constructs an object using
 *              the Builder interface
 *
 * Extension/Implementations:
 *
 * Dependencies:
 *      - Renderable: Each built object is returned as a Renderable
 *      - AlienBuilder: Builder for the AlienProduct Object
 *      - BunkerBuilder: Builder for the BunkerProduct Object
 */

package invaders.builderPattern;

import invaders.builderPattern.Builders.AlienBuilder;
import invaders.builderPattern.Builders.BunkerBuilder;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.rendering.Renderable.Layer;
import invaders.statePattern.NoDamageBunker;
import javafx.scene.image.Image;

import java.io.File;

public class Director {

    public Renderable buildAlien(AlienBuilder builder){
        builder.setPosition(new Vector2D(0,0));
        builder.setSize(30,20);
        builder.setSprite(new Image(new File("src/main/resources/" +
                "enemy/CrabUp.png").toURI().toString()));
        builder.setLayer(Renderable.Layer.FOREGROUND);
        builder.setAlive(true);
        builder.setHealth(1);
        builder.setXVel(0.1);
        return builder.build();
    }

    public Renderable buildBunker(BunkerBuilder builder){
        builder.setLayer(Layer.FOREGROUND);
        builder.setSprite((new Image(new File("src/main/resources/" +
                "player/Bunker.png").toURI().toString())));;
        builder.setAlive(true);
        builder.setHealth(3);
        builder.setState(new NoDamageBunker());
        return builder.build();
    }
}
