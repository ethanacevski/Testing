/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The BunkerProduct class is the ConcreteProduct participant within the Builder Design Pattern.
 *          Its responsibility to define the constructor and parameters which must be specified in order to create
 *          an BunkerProduct Object
 *
 * Extension/Implementations:
 *      - Entity: Reduces code repetition across concrete classes
 *      - StateObject: Implements the BunkerState Interface
 *
 * Dependencies:
 *      - BunkerState: To hold the current state of the Bunker
 *      - Vector2D: For the position of the Alien and Projectile
 */

package invaders.builderPattern.ConcreteProducts;

import invaders.physics.Vector2D;
import invaders.statePattern.*;
import javafx.scene.image.Image;

public class BunkerProduct extends Entity implements StateObject {
    private BunkerState state;
    public BunkerProduct(Vector2D position,
                         double width,
                         double height,
                         Image sprite,
                         Layer layer,
                         boolean alive,
                         int health,
                         BunkerState state) {
        super(position,
                width,
                height,
                sprite,
                layer,
                alive,
                health);
                this.state = state;

    }

    @Override
    public void start() {
    }

    @Override
    public void update() {

    }

    @Override
    public Image getImage() {
        return this.sprite;
    }

    @Override
    public double getWidth() {
        return this.width;
    }

    @Override
    public double getHeight() {
        return this.height;
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public Layer getLayer() {
        return this.layer;
    }

    @Override
    public boolean getMarked() {
        return this.alive;
    }

    @Override
    public void takeDamage(int amount) {
        if(this.health > amount) {
            this.health -= amount;
        } else {
            this.health = 0;
            this.alive = false;}
    }

    @Override
    public int getHealth() {
        return this.health;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public void setImage(Image sprite){
        this.sprite = sprite;
    }
    @Override
    public void setState(BunkerState state){
        this.state = state;
    }
}
