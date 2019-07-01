package Serializers;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

/**
 * an Interface for supporting Json serializations
 * all state saving methods are using formatting data in to Json
 */
public interface Serializable {
     JSONObject serialize();
     JSONObject deserialize(String str) throws ParseException;
}
