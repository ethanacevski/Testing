/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The AlienBulletFactory implements the BulletFactory Abstract class's createBullet method.
 *
 * Participant: The AlienBulletFactory is a ConcreteCreator participant within the Factory Design Pattern.
 */

package invaders.factoryPattern.factories;

import invaders.factoryPattern.bullets.AlienBullet;
import invaders.factoryPattern.bullets.Bullet;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable.Layer;
import javafx.scene.image.Image;

public class AlienBulletFactory implements BulletFactory {


    @Override
    public Bullet createBullet(Vector2D position,
                               double width,
                               double height,
                               Image sprite,
                               Layer layer,
                               double yVel,
                               boolean active,
                               int health) {
        return new AlienBullet(position, width, height,
                sprite, layer, yVel, active, health);
    }
}
