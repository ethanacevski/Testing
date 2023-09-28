/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The AlienBuilder class is a ConcreteBuilder participant within the Builder Design Pattern for Aliens.
 *          Its responsibility is to set the properties for the ConcreteProduct which is the AlienProduct Class
 *
 * Dependencies:
 *          - AlienProduct Class: Due to the Builder having to return an AlienProduct Object
 */

package invaders.builderPattern.Builders;

import invaders.builderPattern.ConcreteProducts.AlienProduct;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategyPattern.ProjectileLogic;
import javafx.scene.image.Image;

public class AlienBuilder implements EntityBuilder {

    private Vector2D position;
    private double width;
    private double height;
    private Image sprite;
    private Renderable.Layer layer;
    private boolean alive;
    private int health;
    private double xVel;
    private ProjectileLogic projectileLogic;
    private int bulletWidth;
    /** Holds ref for player starting Health. */
    private int bulletHeight;
    private int bulletStrength;


    @Override
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }


    public void setXVel(double xVel) {
        this.xVel = xVel;
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

    public void setProjectileLogic(ProjectileLogic projectileLogic){
        this.projectileLogic = projectileLogic;
    }

    public void setBulletWidth(int bulletWidth) {
        this.bulletWidth = bulletWidth;
    }

    public void setBulletHeight(int bulletHeight) {
        this.bulletHeight = bulletHeight;
    }

    public void setBulletStrength(int bulletStrength) {
        this.bulletStrength = bulletStrength;
    }

    @Override
    public AlienProduct build() {
        return new AlienProduct(position, width, height,
                sprite, layer, alive, health, xVel, projectileLogic,
                bulletWidth, bulletHeight, bulletStrength);
    }
}
