/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Shooter Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the Shooter Interface.
 */
package invaders.logic;

import invaders.rendering.Renderable;

public interface Shooter {

    public Renderable shoot();
}
