/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: KeyboardInputHandler was a given class within the sample code that is responsible for the KeyEvent
 *              handling within the Application
 *
 * Dependencies:
 *          - GameEngine: Passed within the constructor of the KeyBoardInputHandler.
 */


package invaders.engine;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class KeyboardInputHandler {
    /** GameEngine holds the current model that is passed through the GameWindow. */
    private final GameEngine model;
    /** Handling for Player left movement */
    private boolean left = false;
    /** Handling for Player left movement */
    private boolean right = false;
    /** Handling of pressedKeys cache */
    private Set<KeyCode> pressedKeys = new HashSet<>();
    /** Holds media sounds for shooting projectiles */
    private Map<String, MediaPlayer> sounds = new HashMap<>();
    /** Constructor requires passing of current gameEngine Model as it retrieves to KeyEvents. */
    KeyboardInputHandler(GameEngine model) {
        this.model = model;

        // TODO (longGoneUser): Is there a better place for this code?
        URL mediaUrl = getClass().getResource("/shoot.wav");
        String jumpURL = mediaUrl.toExternalForm();
        Media sound = new Media(jumpURL);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        sounds.put("shoot", mediaPlayer);
    }

    /** handlePressed is responsible for handling Key Press Events. */
    void handlePressed(KeyEvent keyEvent) {
        if (pressedKeys.contains(keyEvent.getCode())) {
            return;
        }
        pressedKeys.add(keyEvent.getCode());
        if (keyEvent.getCode().equals(KeyCode.SPACE)) {
            if (model.shootPressed()) {
                MediaPlayer shoot = sounds.get("shoot");
                shoot.stop();
                shoot.play();
            }
        }
        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = true;
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            right = true;
        }
        if (left) {
            model.leftPressed();
        }
        if(right){
            model.rightPressed();
        }
    }
    /** handleReleased is responsible for handling Key Release Events. */
    void handleReleased(KeyEvent keyEvent) {
        pressedKeys.remove(keyEvent.getCode());

        if (keyEvent.getCode().equals(KeyCode.LEFT)) {
            left = false;
            model.leftReleased();
        }
        if (keyEvent.getCode().equals(KeyCode.RIGHT)) {
            model.rightReleased();
            right = false;
        }
    }
}
