package Serializers;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface Serializable {
     JSONObject serialize();
     JSONObject deserialize(String str) throws ParseException;
}
