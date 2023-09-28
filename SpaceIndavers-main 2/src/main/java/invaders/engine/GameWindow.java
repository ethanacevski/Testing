/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The GameWindow is responsible for displaying the Renderable objects on the screen by checking if they
 * are alive, and if so it creates an EntityView of Object them. Further, it initialises the Timeline and stops it
 * temporarily during a GameWin or GameLoss sequence it prevent gameplay from occurring in the background.
 *
 * Interfaces/Dependencies:
 *          - GameEngine: Holds a reference to the passed GameEngine to call getter methods to determine whether to
 *                 queue a loss sequence.
 *          - Renderable: Enables us to hold the Renderable objects and check logic
 *          - Scene: Holds reference to the current Scene
 *          - Pane: Holds reference to Pane which is displayed throughout the game
 *          - EntityView: Utilised for creating EntityView Objects
 *          - Timeline: Utilised to create the TimeLine for the Game
 *          - ImageView: for the created and displayed images during Win and Loss Sequences.
 *          - Rectangle: Creating the respective Shape
 *          - PauseTransition for Stopping KeyEvents.
 */

package invaders.engine;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

import invaders.entities.EntityViewImpl;
import invaders.entities.SpaceBackground;
import javafx.animation.PauseTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import invaders.entities.EntityView;
import invaders.rendering.Renderable;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class GameWindow {
    /** Holds Window Width passed from App. */
	private final int width;
    /** Holds Window Height passed from App. */
    private final int height;
    /** Holds current Scene. */
	private Scene scene;
    /** Holds pane used to display different objects. */
    private Pane pane;
    /** Holds reference to GameEngine created in App. */
    private GameEngine model;
    /** Holds list of EntityView Objects which is displayed throughout the course of the game. */
    private List<EntityView> entityViews;
    /** Holds reference to background created for the game. */
    private Renderable background;
    /** Enables use to pause and resume the timeline throughout GameWin and GameLoss sequences. */
    private Timeline timeline;
    /** Used for logic in handling active and non-active TimeLine. */
    private boolean timelineActive;
    /** Reference to the GameLoss Image. */
    private ImageView gameLoss;
    /** Reference to the GameWin Image. */
    private ImageView gameWin;
    /** Reference to the PlayAgain Image. */
    private ImageView playAgain;
    /** Reference to the Game Ending Background. */
    private Rectangle end;
    /** Object allowing us to temporarily disable KeyEvents. */
    private PauseTransition pause;
    /** Utilised to re-enable KeyEvents following a Pause. */
    private boolean pausedBool;
    /** ViewPortOffset to re-adjust x value offset. Note: kept as a pre-caution if it was required following a future extension. */
    private double xViewportOffset = 0.0;
    /** ViewPortOffset to re-adjust y value offset. Note: kept as a pre-caution if it was required following a future extension. */
    private double yViewportOffset = 0.0;

    /** Constructor for GameWindow passes the GameEngine instance instantiated in App along with the GameWindow Width and Height
     *      Additionally does the following:
     *         - Instantiates Pane, Scene the SpaceBackground, A 1.5second PauseTransition, KeyboardInputHandler
     *         - Sets scene key press event handlers
     *         */
	public GameWindow(GameEngine model, int width, int height){
		this.width = width;
        this.height = height;
        this.model = model;
        pane = new Pane();
        scene = new Scene(pane, width, height);
        this.background = new SpaceBackground(model, pane);
        this.timelineActive = true;
        pause = new PauseTransition(Duration.millis(1500)); // Disables KeyEvents for 1.5 seconds
        pausedBool = false;

        KeyboardInputHandler keyboardInputHandler = new KeyboardInputHandler(this.model); // Instantiates KeyEvent Logic

        scene.setOnKeyPressed(key -> {
            keyboardInputHandler.handlePressed(key);

            if(!timelineActive && !pausedBool){     // If GameWin or GameLoss Sequence is active and TimeLine Pause.
                model.reset();                      // Model is reset.
                for (EntityView en : entityViews){  // Removes all EntityView Objects in Pane.
                    pane.getChildren().remove(en.getNode());
                }
                entityViews.clear();                // Once images removed from Pane, EntityViews List is cleared.
                clearStage();                       // Removes GameWin/GameLoss Screen.
                timeline.play();                    // Timeline Resumed.
                timelineActive = true;              // TimelineActive changed.

            }
        });
        scene.setOnKeyReleased(keyboardInputHandler::handleReleased);
        pause.setOnFinished(e -> pausedBool = false);   // Following 1.5s KeyEvent Pause, KeyEvents are re-enables
        entityViews = new ArrayList<EntityView>();  // Instantiates the entityViews List.
    }

    /** run is called upon Application initialisation, it is responsible for creating the timeline and defining which
     * methods are called throughout the Timeline along with the duration between each specified method call.
     * Further, it started the TimeLine and runs it for an indefinite amount of time. */
	public void run() {
        // Timeline calls this.draw() every 17ms
        this.timeline = new Timeline(new KeyFrame(Duration.millis(17), t -> this.draw()));
        timeline.setCycleCount(Timeline.INDEFINITE); // Cycle count set to continuous.
        timeline.play();                             // Timeline started.
    }
    /** draw is responsible for calling the update function of the GameEngine, alongside displaying the returned Renderables
     * on the screen. Further, is containing the logic for GameLoss and GameWin sequences. */
    private void draw(){
        model.update();                                             // Calling update on the GameEngine.
        List<Renderable> renderables = model.getRenderables();      // Gets renderables list from Model.
        for (Renderable entity : renderables) {
            boolean notFound = true;
            for (EntityView view : entityViews) {
                if (view.matchesEntity(entity)) {
                    notFound = false;
                    view.update(xViewportOffset, yViewportOffset);  // Updates the EntityView of the Object.
                    break;
                }
            }
            if (notFound) {
                EntityView entityView = new EntityViewImpl(entity, width, height); // Creates EntityView Objects for Each Renderable.
                entityViews.add(entityView);                        // Adding EntityView Object to EntityView List.
                pane.getChildren().add(entityView.getNode());       // adding Objects to the Pane.
            }
        }

        for (EntityView entityView : entityViews) {
            if (entityView.isMarkedForDelete()) {
                pane.getChildren().remove(entityView.getNode());    // Removes EntityView Objects which are marked for delete.
            }
        }
        entityViews.removeIf(EntityView::isMarkedForDelete);        // Removes EntityView Objects if marked for delete.
        renderables.removeIf(ro -> !ro.getMarked());                // Removes Renderables if marked for delete.

        if(model.getLives() <= 0 || model.getLowestAlien()){
            gameOver();                                             // Initiates the gameOver sequence.
            return;
        }
        if(model.getAliens() <= 0){
            gameWin();                                              // Initiates the gameWin sequence.
            return;
        }
    }
    /** getScene returns the current Scene utilised in GameWindow */
	public Scene getScene() {
        return scene;
    }
    /** gameOver initialises the gameOver sequence for the game */
    public void gameOver(){
        // Creates a black backdrop to go over the game.
        end = new Rectangle(0,0,width,height);
        end.setFill(Paint.valueOf("BLACK"));
        end.setViewOrder(1);

        //Creates an ImageView Object of the GameLoss PNG in the resources folder.
        this.gameLoss = new ImageView(new Image(new File("src/main/resources/gameScenes/GameOver.png")
                .toURI().toString()));
        gameLoss.setPreserveRatio(true);
        gameLoss.setCache(true);
        gameLoss.setSmooth(true);
        gameLoss.setFitHeight((double) height*0.1);
        gameLoss.setFitWidth((double) width*0.5);
        gameLoss.setX(width*0.25);
        gameLoss.setY(height*0.2);

        playAgain = setPlayAgain();                           // Returns the playAgain ImageView
        pausedBool = true;                                    // Sets logic for pausing KeyEvent
        timelineActive = false;                               // Sets logic for pausing Timeline

        pane.getChildren().addAll(end,gameLoss,playAgain);    // Adds respective images to the Pane
        timeline.stop();                                      // Stops Timeline
        pause.play();                                         // Disables KeyEvents for 1.5 seconds
    }

    /** gameWin initialises the gameOver sequence for the game */
    public void gameWin(){
        // Creates a black backdrop to go over the game.
        end = new Rectangle(0,0,width,height);
        end.setFill(Paint.valueOf("BLACK"));
        end.setViewOrder(1);
        //Creates an ImageView Object of the GameWin PNG in the resources foloder
        this.gameWin = new ImageView(new Image(new File("src/main/resources/gameScenes/youwin.png")
                .toURI().toString()));
        gameWin.setPreserveRatio(true);
        gameWin.setCache(true);
        gameWin.setSmooth(true);
        gameWin.setFitHeight((double) height*0.1);
        gameWin.setFitWidth((double) width*0.5);
        gameWin.setX(width*0.25);
        gameWin.setY(height*0.2);

        playAgain = setPlayAgain();                           // Returns the playAgain ImageView.
        pausedBool = true;                                    // Sets logic for pausing KeyEvent.
        timelineActive = false;                               // Sets logic for pausing Timeline.
        pane.getChildren().addAll(end,gameWin,playAgain);     // Adds created Objects to Pane.
        timeline.stop();                                      // Stops Timeline.
        pause.play();                                         // Disables KeyEvents for 1.5 seconds.
    }

    /** clearStage removes the 3 added Objects from the Pane. */
    public void clearStage(){
        int no = pane.getChildren().size();
        pane.getChildren().remove(no-1);
        pane.getChildren().remove(no-2);
        pane.getChildren().remove(no-3);
    }
    /** setPlayAgain returns the ImageView created for the PlayAgain PNG*/
    public ImageView setPlayAgain(){
        playAgain = new ImageView(new Image(new File("src/main/resources/gameScenes/PlayAgain.png")
                .toURI().toString()));

        playAgain.setPreserveRatio(true);
        playAgain.setCache(true);
        playAgain.setSmooth(true);
        playAgain.setFitHeight((double) height*0.2);
        playAgain.setFitWidth((double) width*0.4);
        playAgain.setX(width*0.3);
        playAgain.setY(height*0.5);
        return playAgain;
    }
}
