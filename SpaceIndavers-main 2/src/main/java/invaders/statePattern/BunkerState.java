/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The BunkerState Interface is utilised for specifying the required methods that must be implemented for
 * 			every class which implements the BunkerState Interface.
 *
 * Participant: The BunkerState Interface is the State Participant within the State Design patter.
 */

package invaders.statePattern;


public interface BunkerState {
    public void handle(StateObject bunker);
}