package invaders.json;

import org.json.simple.JSONObject;

public class ConfigGame extends ConfigReader {

    public static int[] configGameItem(String config) {
        int[] dimensions = new int[2];
        JSONObject obj = (JSONObject) parse(config).get("Game");
        JSONObject size = (JSONObject) obj.get("size");
        dimensions[0] = ((Long) size.get("x")).intValue();
        dimensions[1] = ((Long) size.get("y")).intValue();
        return dimensions;
    }
}
