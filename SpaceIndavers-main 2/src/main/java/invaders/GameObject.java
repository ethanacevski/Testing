/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The GameObject Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the GameObject Interface. GameObject is responsible for specifying the basic
 * 		    methods all GameObjects must implement
 */
package invaders;

public interface GameObject {

    public void start();
    public void update();

}
