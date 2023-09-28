package invaders.json;

import invaders.entities.Player;
import invaders.factoryPattern.factories.PlayerBulletFactory;
import invaders.physics.Vector2D;
import javafx.scene.paint.Paint;
import org.json.simple.JSONObject;

public class ConfigPlayer extends ConfigReader {


    public static Player configGameItem(String config){
        //note: potential SRP Issue with reading JSON and setting Player???
        JSONObject obj = (JSONObject) parse(config).get("Player");
        JSONObject objPos = (JSONObject) obj.get("position");
        double x = ((Long)objPos.get("x")).intValue();
        double y =  ((Long)objPos.get("y")).intValue();
        int lives = ((Long)obj.get("lives")).intValue();

        JSONObject bull = (JSONObject) parse(config).get("BulletSpecs");
        JSONObject bullsize = (JSONObject) bull.get("size");
        int bullWidth = ((Long)bullsize.get("x")).intValue();
        int bullHeight =  ((Long)bullsize.get("y")).intValue();

        int bullStrength = ((Long)bull.get("strength")).intValue();
        double bullVel = (Double) bull.get("velocity");

        return new Player(new Vector2D(
                x,y),new PlayerBulletFactory(),
                lives,
                ((String)obj.get("colour")),
                ((Long)obj.get("speed")).intValue(),
                bullWidth,bullHeight, bullStrength, bullVel)
        ;
    }
}
