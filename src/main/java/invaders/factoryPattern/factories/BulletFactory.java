/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The BulletFactory Abstract Class defines the createBullet method which is required to be implemented by
 * the ConcreteCreator Classes as per the Factory Design Pattern.
 *
 * Participant: The BulletFactory Abstract Class is the Creator participant within the Factory Design Pattern.
 */

package invaders.factoryPattern.factories;

import invaders.factoryPattern.bullets.Bullet;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable.Layer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public interface BulletFactory {

    public Bullet createBullet(Vector2D position,
                               double width,
                               double height,
                               Image sprite,
                               Layer layer,
                               double yVel,
                               boolean active,
                               int health);

}
