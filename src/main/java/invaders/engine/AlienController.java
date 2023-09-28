/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: AlienController Class is responsible for keeping track of front most alien in each respective column
 *          Note: the comprehensive construction of the is due to the requirement of having to keep track of the alien
 *          positions each after it has been killed. This so we can dynamically check the aliens in the list and those
 *          which are in-front of it in the original horde of aliens
 *
 * Extension/Implementations:
 *      - Renderable: The list of frontMostRow are of type Renderables
 */

package invaders.engine;

import invaders.logic.Shooter;
import invaders.rendering.Renderable;

import java.util.ArrayList;
import java.util.List;

public class AlienController {

    //Keeps a reference to the aliens list in GameEngine
    private final List<Renderable> intialA;
    //Keeps a reference to the original aliens generated via the config file in GameEngine
    private List<Renderable> aliensCopy;
    //Keeps a reference to the original row and column structure of the original alien horde
    private int[] alienMatrix;
    //Keeps a list to the current alien list row and column structure
    private final int[] matrix;
    public AlienController (List<Renderable> intialAliens){
        this.intialA = new ArrayList<>(intialAliens);;

        // Initialize the aliens list with a copy of the initialAliens list
        //        - this is the only way to allow us to get a reference to the aliens list which isn't affected by
        //          deletion
        this.aliensCopy = new ArrayList<>(intialA);
        // Initialize the aliens matrix with a copy of the initialAliens list
        //        - this is the only way to allow us to get a reference to the aliens list which isn't affected by
        //          deletion
        this.matrix = matrixGenerator(intialAliens);
        // Creates the alienMatrix which is not changed throughout the game
        this.alienMatrix = new int[]{matrix[0], matrix[1]};




    }

    // getFrontMostAliens is responsible for the returning the front-most alien in each column of the alien horde
    public List<Renderable> getFrontMostAliens(List<Renderable> aliens){

        Renderable[][] alienPositions = new Renderable[alienMatrix[0]][alienMatrix[1]];

        int index = 0;
        for (int i = 0; i < alienMatrix[0]; i++){
            for (int j = 0; j < alienMatrix[1]; j++){
                if(aliens.contains(aliensCopy.get(index))){
                    alienPositions[i][j] = aliensCopy.get(index);
                }else {
                    alienPositions[i][j] = null;
                }
                index++;

            }
        }

        List<Renderable> frontMostAlienList = new ArrayList<>();

        for (int j = 0; j < alienMatrix[1]; j++) {
            double largestY = Double.MIN_VALUE;
            Renderable frontMostAlien = null;

            for (int i = 0; i < alienMatrix[0]; i++) {
                Renderable currentAlien = alienPositions[i][j];
                if (currentAlien != null && currentAlien.getPosition().getY() > largestY && currentAlien.getMarked()) {
                    largestY = currentAlien.getPosition().getY();
                    frontMostAlien = currentAlien;
                }
            }
            if (frontMostAlien != null) {
                frontMostAlienList.add(frontMostAlien);
            }
        }
        return frontMostAlienList ;

    }

    // Responsible for getting the desired Shooter (Alien) from the Renderables list to fire the projectile
    //      - Shooter Interface has a shoot() method which allows our aliens to fire.
    public Shooter getShooter(List<Shooter> shooters, Renderable ro){
        for (Shooter sh : shooters){
            if (ro.equals((Renderable) sh)){
                return sh;
            }
        }
        return null;
    }

    // matrixGenerator sets the row column structure for the aliens allowing us get the no. of rows and columns
    //          for the initial array
    public int[] matrixGenerator(List<Renderable> initalAliens){
        int[] rowCol = new int[2];

        ArrayList<Double> row = new ArrayList<>();
        ArrayList<Double> col = new ArrayList<>();

        for (Renderable al : aliensCopy){
            if(!row.contains(al.getPosition().getY()) && al.getMarked()){
                row.add(al.getPosition().getY());
            }
            if(!col.contains(al.getPosition().getX()) && al.getMarked()){
                col.add(al.getPosition().getX());
            }
        }
        rowCol[0] = row.size();
        rowCol[1] = col.size();

        return rowCol;
    }
}
