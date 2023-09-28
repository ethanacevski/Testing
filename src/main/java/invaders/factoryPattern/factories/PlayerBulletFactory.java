/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The PlayerBulletFactory implements the BulletFactory Abstract class's createBullet method.
 *
 * Participant: The PlayerBulletFactory is a ConcreteCreator participant within the Factory Design Pattern.
 */

package invaders.factoryPattern.factories;

import invaders.factoryPattern.bullets.Bullet;
import invaders.factoryPattern.bullets.PlayerBullet;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable.Layer;
import javafx.scene.image.Image;

public class PlayerBulletFactory implements BulletFactory{


    @Override
    public Bullet createBullet(Vector2D position,
                               double width,
                               double height,
                               Image sprite,
                               Layer layer,
                               double yVel,
                               boolean active,
                               int health) {
        return new PlayerBullet(position, width, height,
                sprite, layer, yVel, active, health);
    }
}
