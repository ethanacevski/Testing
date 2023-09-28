/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The BunkerBuilder class is a ConcreteBuilder participant within the Builder Design Pattern for Bunkers.
 *          Its responsibility is to set the properties for the ConcreteProduct which is the BunkerBuilder Class
 *
 * Dependencies:
 *          - BunkerProduct Class: Due to the Builder having to return a BunkerProduct Object
 */


package invaders.builderPattern.Builders;

import invaders.builderPattern.ConcreteProducts.BunkerProduct;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.statePattern.BunkerState;
import javafx.scene.image.Image;

import java.awt.*;

public class BunkerBuilder implements EntityBuilder {
    private Vector2D position;
    private double width;
    private double height;
    private Image sprite;
    private Renderable.Layer layer;
    private boolean alive;
    private int health;
    private BunkerState state;
    private Vector2D startPos;
    private int startHealth;

    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void setSprite(Image sprite) {
        this.sprite = sprite;
    }

    @Override
    public void setLayer(Renderable.Layer layer) {
        this.layer = layer;
    }

    @Override
    public void setAlive(boolean bool) {
        this.alive = bool;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public void setState(BunkerState state){
        this.state = state;
    }

    @Override
    public BunkerProduct build(){
        return new BunkerProduct(position, width, height,
                sprite, layer, alive, health, state);
    }
}
