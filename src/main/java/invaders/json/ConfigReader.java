package invaders.json;

import invaders.builderPattern.Builders.AlienBuilder;
import invaders.builderPattern.Director;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategyPattern.FastProjectileLogic;
import invaders.strategyPattern.ProjectileLogic;
import invaders.strategyPattern.SlowProjectileLogic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigReader {

    public static String path;

    public static JSONObject parse(String path) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(new FileReader(path));
            // convert Object to JSONObject
            return (JSONObject) object;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
