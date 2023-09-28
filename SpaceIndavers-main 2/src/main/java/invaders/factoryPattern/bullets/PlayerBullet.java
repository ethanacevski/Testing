/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: PlayerBullet class is responsible for defining the attributes and constructor of the Player Projectiles.
 * The main difference between this and the AlienBullet class is the update method whereby the Y position of the projectile
 * is increasing.
 * Participant: The PlayerBullet Class is a ConcreteProduct participant in the Factory Design Pattern.
 */

package invaders.factoryPattern.bullets;

import invaders.physics.Vector2D;
import javafx.scene.image.Image;

public class PlayerBullet extends Bullet {

    public PlayerBullet(Vector2D position,
                           double width,
                           double height,
                           Image sprite,
                           Layer layer,
                           double yVel,
                           boolean active,
                            int health) {
        super(position, width, height, sprite, layer, yVel, true, health);
    }


    @Override
    public void update() {
        if(this.getPosition().getY()-getHeight() <= 0){
            this.active = false;
        }
        this.getPosition().setY(this.getPosition().getY() - this.getyVel());
    }

    @Override
    public boolean getMarked() {
        return this.active;
    }
}
