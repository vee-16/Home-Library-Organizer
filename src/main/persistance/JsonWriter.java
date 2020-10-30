package persistance;

import model.UserAccount;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/***
 *    CITATION:
 *    Title: CPSC210/JsonSerializationDemo
 *    Author: Paul Carter
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 ***/

public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destinationFile;

    /*
     * REQUIRES: destination file as string
     * MODIFIES: this
     * EFFECTS: destination file to write to set to file
     */
    public JsonWriter(String file) {
        this.destinationFile = file;
    }


    // EFFECTS: opens writer, if file is not found, throws FileNotFoundException
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destinationFile));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of user book list to file
    public void write(UserAccount user) {
        JSONObject json = user.toJson();
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }
}
