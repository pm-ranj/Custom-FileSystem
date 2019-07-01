package Utils;

import DiskManager.Disk;
import Serializers.Serializable;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Block implements Serializable {
    public byte[] data;
    public String filename;


    private final int  size = Disk.BLOCK_SIZE;

    public Block(String filename) {
        this.filename = filename;
        data  = new byte[size];
    }

    public Block(String filename, byte[] data) {
        this.filename = filename;
        this.data  = new byte[size];
        this.data = data;
    }

    public Block() {
        data  = new byte[size];
    }

    public String getDataAsStringToPrint() {
        String res = "";
        for (int i=0; i<data.length; i++) {
            if (i == data.length-1) {
                res += data[i];
            } else {
                res += data[i] + " ";
            }
        }
        return res;
    }

    // to fill data byte buffer with buffers with less than Global buffer size
    public void fillDataBuffer(byte [] bytes){
        for (int i=0; i<bytes.length; i++) {
            data[i] = bytes[i];
        }
    }

    // it used on state saving/loading methods for more efficiency
    public byte[] buildDataFromString(String str) {
        var list = str.split(" ");

        byte[] bytes = new byte[size];

        for (int i=0; i<list.length; i++) {
            bytes[i] = Byte.valueOf(list[i]);
        }

        return bytes;
    }

    //a method for detecting weather a data buffer is full or empty
    //it commented out from code
    public boolean isDataValidForWritingBytes() {
        for (byte b : data) {
            if(b != 0) {
                return false;
            }
        }

        return true;
    }


    /**
     * @see Serializable
     */
    @Override
    public JSONObject serialize() {
        JSONObject obj = new JSONObject();
        obj.put("filename", filename);
        obj.put("data", getDataAsStringToPrint());
        return obj;
    }

    // deserialize method also used as re evaluating the class attributes
    @Override
    public JSONObject deserialize(String str) throws ParseException {

        JSONParser parser = new JSONParser();
        var json = (JSONObject)parser.parse(str);
        filename = (String) json.get("filename");
        data = buildDataFromString((String) json.get("data"));

        return json;
    }

}
