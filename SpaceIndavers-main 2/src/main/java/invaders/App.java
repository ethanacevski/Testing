/**
 * Author: Ethan Acevski 520406429 | Date Modified: 25-Sep-23
 * Functionality: The App class is responsible for setting up the application.
 *      It calls upon on the game configuration and passes it through the GameEngine and GameWindow
 *      during initialisation
 * Dependency: None
 */
package invaders;

import invaders.json.ConfigGame;
import javafx.application.Application;
import javafx.stage.Stage;
import invaders.engine.GameEngine;
import invaders.engine.GameWindow;

import java.util.Map;

public class App extends Application {

    public static String path = "src/main/resources/config.json";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        int[] dimensions = ConfigGame.configGameItem(path);
        GameEngine model = new GameEngine(path, dimensions[0], dimensions[1]);
        GameWindow window = new GameWindow(model, dimensions[0], dimensions[1]);
        primaryStage.setTitle("Space Invaders");
        primaryStage.setScene(window.getScene());
        primaryStage.show();
        window.run();
    }
}
