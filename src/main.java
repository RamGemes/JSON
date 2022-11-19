import ramgames.json.JSON;
import ramgames.json.JSONHash;

import java.io.File;

public class main {
    public static void main(String[] args) {
            JSONHash object = JSON.getJSON(new File("./thing.json"));
            System.out.println(object);
    }
}
