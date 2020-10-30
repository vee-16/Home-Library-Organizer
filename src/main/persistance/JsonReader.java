package persistance;

import model.Book;
import model.UserAccount;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.stream.Stream;
/***
 *    CITATION:
 *    Title: CPSC210/JsonSerializationDemo
 *    Author: Paul Carter
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 ***/

public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public UserAccount read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseUserAccount(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses workroom from JSON object and returns it
    private UserAccount parseUserAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        UserAccount user = new UserAccount(name);
        addBooks(user, jsonObject);
        return user;
    }

    // MODIFIES: wr
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addBooks(UserAccount user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(user, nextBook);
        }
    }

    // MODIFIES: wr
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addBook(UserAccount user, JSONObject jsonObject) {
        System.out.println(jsonObject);
        String author = jsonObject.getString("author");
        String name = jsonObject.getString("title");
        Boolean check = jsonObject.getBoolean("check read");

        Book b = new Book(name,author,check);
        user.addNewBook(b);
    }
}
