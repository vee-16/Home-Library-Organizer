package persistance;

import org.json.JSONObject;

/***
 *    CITATION:
 *    Title: CPSC210/JsonSerializationDemo
 *    Author: Paul Carter
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 ***/
public interface Writable {
    // EFFECTS: returns JSON object
    JSONObject toJson();
}
