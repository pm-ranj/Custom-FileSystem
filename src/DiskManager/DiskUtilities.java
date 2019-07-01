package DiskManager;

import Serializers.Serializable;
import Utils.Block;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DiskUtilities implements Serializable {

    private static final String  LoadFilePath = "./DiskState.pm";
    private List<Block> blocks;

    public DiskUtilities(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        for(var item: blocks) {

            array.add(item.serialize());

        }
        obj.put("list", array);
        return obj;
    }

    @Override
    public JSONObject deserialize(String str) throws ParseException {
        JSONParser parser = new JSONParser();

        JSONObject obj = (JSONObject)parser.parse(str);
        return obj;
    }

    private File LoadOrCreateFile() throws IOException {
        File file = new File(LoadFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;

    }

    public void saveDiskState() {
        String json = serialize().toJSONString();
        System.out.println(json);

        try {
            var file = LoadOrCreateFile();
            FileOutputStream fis = new FileOutputStream(file);
            fis.write(json.getBytes());
            fis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public List<Block> LoadDiskState() throws Exception {

        var file = LoadOrCreateFile();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        var jsonStr = reader.readLine();
        System.out.println("json string: " + jsonStr);
        reader.close();

        if (jsonStr != null) {
                blocks = new ArrayList<>();
                var obj = deserialize(jsonStr);
                var jsonArray = (List)obj.get("list");
                for (int i=0; i<jsonArray.size(); i++) {
                    var b = new Block();
                    b.deserialize( jsonArray.get(i).toString());
                    blocks.add(b);
                }

                return blocks;
        } else {
            return null;
        }


    }

}
