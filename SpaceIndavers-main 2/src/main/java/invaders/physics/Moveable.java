/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Moveable Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the Moveable Interface.
 */


package invaders.physics;

// represents something that can move up, down, left, right
public interface Moveable {

	public void up();

	public void down();

	public void left();

	public void right();
}
