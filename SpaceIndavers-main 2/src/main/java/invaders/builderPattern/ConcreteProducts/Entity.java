/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Entity is the abstract class which the ConcreteProducts extend in the Builder  Design Pattern.
 *          Its responsibility for defining the common constructor parameters and getters that each entity within
 *          the design pattern must have.
 *
 * Extension/Implementations:
 *      - Renderable: Each ConcreteProduct is a renderable
 *      - GameObject: Similarly each ConcreteProduct is a GameObject in its update() method.
 *      - Collider: Each ConcreteProduct can collide with other objects
 *      - Damageable: Each ConcreteProduct is also damageable as they have lives
 *
 * Dependencies:
 *      - Vector2D: Holds position for each ConcreteProduct
 */

package invaders.builderPattern.ConcreteProducts;

import invaders.GameObject;
import invaders.logic.Damagable;
import invaders.physics.Collider;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.image.Image;

public abstract class Entity implements Renderable, GameObject, Collider, Damagable {


    protected Vector2D position;
    protected double width;
    protected double height;
    protected Image sprite;
    protected Layer layer;
    protected boolean alive;
    protected int health;

    public Entity(Vector2D position,
                  double width,
                  double height,
                  Image sprite,
                  Layer layer,
                  boolean alive,
                  int health){
        this.position = position;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.layer = layer;
        this.alive = alive;
        this.health = health;
    }

}
