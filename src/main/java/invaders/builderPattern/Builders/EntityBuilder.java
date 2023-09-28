/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The EntityBuilder Interface is the Builder participant within the Builder Design Pattern.
 *          Its responsibility is to lay out the setters which must be implemented by the ConcreteBuilders
 *          for the Builder Design Pattern
 *
 * Dependencies:
 *          - Entity class dependency is due to the requirement of the Builder to return an object type.
 *              -> Note: Entity is an abstract class which all ConcreteProducts extend.
 */

package invaders.builderPattern.Builders;

import invaders.builderPattern.ConcreteProducts.Entity;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable.Layer;
import javafx.scene.image.Image;

public interface EntityBuilder {

    public void  setPosition(Vector2D position);
    public void setSize(double width, double height);
    public void setSprite(Image sprite);
    public void setLayer(Layer layer);
    public void setAlive(boolean bool);
    public void setHealth(int health);

    public Entity build();

}
