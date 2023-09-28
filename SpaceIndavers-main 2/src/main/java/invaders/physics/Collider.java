/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Collider Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the Collider Interface.
 */

package invaders.physics;

public interface Collider {
    /** If Objects are touching on Initialisation this prevents collision being recognised. */
    public final double offset = 0.0000001;

    public double getWidth();

    public double getHeight();

    public Vector2D getPosition();

    public default boolean isColliding(Collider col) {

        double minX1 = this.getPosition().getX();
        double maxX1 = this.getPosition().getX() + this.getWidth()-offset;
        double minY1 = this.getPosition().getY();
        double maxY1 = this.getPosition().getY() + this.getHeight()-offset;

        double minX2 = col.getPosition().getX();
        double maxX2 = col.getPosition().getX() + col.getWidth()-offset;
        double minY2 = col.getPosition().getY();
        double maxY2 = col.getPosition().getY() + col.getHeight()-offset;

        if (maxX1 < minX2 || maxX2 < minX1) {
            return false; // No overlap in the x-axis
        }

        if (maxY1 < minY2 || maxY2 < minY1) {
            return false; // No overlap in the y-axis
        }

        return true; // Overlap in both x-axis and y-axis
    }
}