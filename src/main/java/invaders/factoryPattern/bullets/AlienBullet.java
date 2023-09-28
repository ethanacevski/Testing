/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: AlienBullet class is responsible for defining the attributes and constructor of the Alien Projectiles.
 *          The main difference between this and the Player class is the update method whereby the Y position of the projectile
 *          is decreasing.
 * Participant: The AlienBullet Class is a ConcreteProduct participant in the Factory Design Pattern.
 */

package invaders.factoryPattern.bullets;

import invaders.physics.Vector2D;
import javafx.scene.image.Image;

public class AlienBullet extends Bullet {
    /** The Constructor takes holds a health variable for potential future extension purposes as it allows bullets
     * to be able to kill multiple aliens if needed. Additionally, it enables us to implement logic surround the
     * active status of the bullet.*/
    public AlienBullet(Vector2D position,
                       double width,
                       double height,
                       Image sprite,
                       Layer layer,
                       double yVel,
                       boolean active,
                       int health) {
        super(position, width, height, sprite, layer, yVel, active, health);
    }


    @Override
    public void update() {
        if(this.getPosition().getY()+getHeight()+1 >= wHeight){
            this.active = false;
        }
        this.getPosition().setY(this.getPosition().getY() + this.getyVel());
    }

    @Override
    public boolean getMarked() {
        return this.active;
    }
}
