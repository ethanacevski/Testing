/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The FastProjectileLogic Class implements the ProjectileLogic class and as such defines the projectileType
 *          method as part of the Strategy Design Pattern.
 *
 * Participant: The FastProjectileLogic Class is a ConcreteStrategy Participant in the Strategy Design Pattern.
 */

package invaders.strategyPattern;

import invaders.App;
import invaders.factoryPattern.factories.AlienBulletFactory;
import invaders.json.ConfigAliens;


public class FastProjectileLogic implements ProjectileLogic {
    private AlienBulletFactory aBulletFact;
    @Override
    public double projectileType() {

        return (ConfigAliens.AlienVelocity(App.path)*2);
    }
}
