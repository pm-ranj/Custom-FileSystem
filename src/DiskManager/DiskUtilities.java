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

/**
 * DiskUtilities class meant to be a helper class for the Disk class
 * to optimize the saving and loading system for the disk
 * it is appropriate for situations like lack of power and sudden causes to shouting down the disk
 * and it grantees the data consistency of the disk
 */
public class DiskUtilities implements Serializable {
    // location for the state saving file
    private static final String  LoadFilePath = "./DiskState.pm";

    // temporary list of blocks for save and load the main Block list
    // it holds the copy of the main list or helping to populate with previous data
    private List<Block> blocks;

    public DiskUtilities(List<Block> blocks) {
        this.blocks = blocks;
    }

    /**
     * @see Serializable
     *
     */
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
    /**
     * @see Serializable
     */
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

    /**
     * to methosds below is for saving the state of disk
     * and the loading syetem of dist
     * both use data format of Json and read/write it from the mentioned file
     *
     */
    public void saveDiskState() {
        String json = serialize().toJSONString();
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
