/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The Damageable Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the Damageable Interface.
 */

package invaders.logic;

public interface Damagable {

	public void takeDamage(int amount);

	public int getHealth();

	public boolean isAlive();

}
