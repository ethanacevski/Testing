package invaders.json;

import invaders.GameObject;
import invaders.builderPattern.Builders.AlienBuilder;
import invaders.builderPattern.Director;
import invaders.logic.Damagable;
import invaders.physics.Moveable;
import invaders.physics.Vector2D;
import invaders.rendering.Renderable;
import invaders.strategyPattern.FastProjectileLogic;
import invaders.strategyPattern.ProjectileLogic;
import invaders.strategyPattern.SlowProjectileLogic;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigAliens extends ConfigReader{


    public static void configGameItem(String config, List<Renderable> aliens) {
        Map<String, ProjectileLogic> projectileLogic = new HashMap<>();

        projectileLogic.put("slow_straight", new SlowProjectileLogic());
        projectileLogic.put("fast_straight", new FastProjectileLogic());

        AlienBuilder builder = new AlienBuilder();
        Director director = new Director();
        director.buildAlien(builder);

        JSONObject bull = (JSONObject) parse(config).get("BulletSpecs");
        JSONObject bullsize = (JSONObject) bull.get("size");
        int bullWidth = ((Long)bullsize.get("x")).intValue();
        int bullHeight =  ((Long)bullsize.get("y")).intValue();

        int bullStrength = ((Long)bull.get("strength")).intValue();


        JSONArray jsonEnemeies = (JSONArray) parse(config).get("Enemies");

        for (Object obj : jsonEnemeies) {
            JSONObject jsonEnemy = (JSONObject) obj;
            JSONObject position = (JSONObject) jsonEnemy.get("position");
            double x = ((Long) position.get("x")).intValue();
            double y = ((Long) position.get("y")).intValue();
            builder.setPosition(new Vector2D(x,y));
            builder.setLayer(Renderable.Layer.FOREGROUND);
            builder.setXVel(0.2);
            builder.setProjectileLogic(projectileLogic.get(((String)jsonEnemy.get("projectile"))));

            builder.setBulletWidth(bullWidth);
            builder.setBulletHeight(bullHeight);
            builder.setBulletStrength(bullStrength);

            Renderable al = builder.build();
            aliens.add(al);

        }
    }

    public static double AlienVelocity(String config){

        JSONObject bull = (JSONObject) parse(config).get("BulletSpecs");
        return (Double) bull.get("velocity");
    }
}
