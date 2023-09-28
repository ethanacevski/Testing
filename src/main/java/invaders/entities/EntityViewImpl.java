/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The EntityViewImpl Class was given from the Sample Code. Minor changes were added to the code which were:
 *          - When running the viewUpdate Method we check whether the Renderable was marked for delete.
 *          - Created a conditional statement which marks the entity for deletion if it lies outside the screen border.
 */

package invaders.entities;

import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

public class EntityViewImpl implements EntityView {
    /** Holds reference to entity passed through. */
    private Renderable entity;
    /** Holds reference to position of entity. */
    private Vector2D position;
    /** Holds reference to whether the alien is marked for deletion. */
    private boolean delete;
    /** Holds ref to the ImageView created for the entity. */
    private ImageView node;
    /** Ref to the Window Width passed through GameWindow. */
    private int wWidth;
    /** Ref to the Window Height passed through GameWindow. */
    private int wHeight;
    /** Constructor passed Renderable entity for creating an ImageView for display. */

    public EntityViewImpl(Renderable entity, int wWidth, int wHeight) {
        this.entity = entity;
        this.wWidth = wWidth;
        this.wHeight = wHeight;
        this.position = entity.getPosition();
        node = new ImageView(entity.getImage());
        node.setViewOrder(getViewOrder(entity.getLayer()));
        update(0.0, 0.0);
    }
    /** Gets the view order of the EntityViewImpl Object based off the Layer of the Renderable. */
    private static double getViewOrder(Renderable.Layer layer) {
        switch (layer) {
            case BACKGROUND: return 100.0;
            case FOREGROUND: return 50.0;
            case EFFECT: return 25.0;
            default: throw new IllegalStateException("Javac doesn't understand how enums work so now I have to exist");
        }
    }
    /** update it utilised to change the ImageView of the Object along with update the value of delete for the respective
     * EntityViewImpl Object. */
    @Override
    public void update(double xViewportOffset, double yViewportOffset) {
        if (!node.getImage().equals(entity.getImage())) {
            node.setImage(entity.getImage());
        }
        node.setX(position.getX() - xViewportOffset);
        node.setY(position.getY() - yViewportOffset);
        node.setFitHeight(entity.getHeight());
        node.setFitWidth(entity.getWidth());
        node.setPreserveRatio(false);
        if(!entity.getMarked()){
            delete = true;
        }
        if(position.getX() <= 1 || position.getY() <= 1
                || position.getX() > wWidth || position.getY() > wHeight){
            delete = true;
        }
    }
    /** matchesEntity determines whether an existing Renderable entity already exists within the EntityView List in GameWindow. */
    @Override
    public boolean matchesEntity(Renderable entity) {
        return this.entity.equals(entity);
    }
    /** Gets to current Node of the EntityViewImpl Object. */
    @Override
    public Node getNode() {
        return node;
    }
    /** isMarkedForDelete returns the boolean value of the delete attribute held by the current EntityViewImpl Object. */
    @Override
    public boolean isMarkedForDelete() {
        return delete;
    }
}
