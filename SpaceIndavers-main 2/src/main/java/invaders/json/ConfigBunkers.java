package invaders.json;

import invaders.GameObject;
import invaders.builderPattern.*;
import invaders.builderPattern.Builders.BunkerBuilder;
import invaders.builderPattern.ConcreteProducts.BunkerProduct;
import invaders.logic.Damagable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.statePattern.StateObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ConfigBunkers extends ConfigReader{


    public static void configGameItem( String config, List<Renderable> bunkers) {


        BunkerBuilder builder = new BunkerBuilder();
        Director director = new Director();
        director.buildBunker(builder);
        JSONArray jsonBunkers = (JSONArray) parse(config).get("Bunkers");

        for (Object obj : jsonBunkers) {
            JSONObject jsonBunker = (JSONObject) obj;
            JSONObject position = (JSONObject) jsonBunker.get("position");
            JSONObject size = (JSONObject) jsonBunker.get("size");
            double x = ((Long) position.get("x")).intValue();
            double y = ((Long) position.get("y")).intValue();
            builder.setPosition(new Vector2D(x , y));
            builder.setSize(((Long) size.get("x")).intValue(),
                    ((Long) size.get("y")).intValue());
            builder.setLayer(Renderable.Layer.FOREGROUND);

            Renderable bunk = builder.build();
            bunkers.add(bunk);
        }
    }
}
