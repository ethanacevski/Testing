/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: EntityView was a given Interface within the sample code that is responsible for specifying the methods
 *              which the EntityView objects must implement.
 */

package invaders.entities;

import javafx.scene.Node;
import invaders.rendering.Renderable;

/** EntityView Interface defines the methods which each class that implements EntityView must define.  */
public interface EntityView {
    void update(double xViewportOffset, double yViewportOffset); //Updates EntityView

    boolean matchesEntity(Renderable entity);                   // Checks Renderable is in EntityView List

    Node getNode();                                             // gets node of EntityView

    boolean isMarkedForDelete();                                // Boolean return if marked for delete
}
