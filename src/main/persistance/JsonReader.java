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
 **
 ***/

public class JsonReader {
    private String sourceFile;

    /*
     * REQUIRES: source file as string
     * MODIFIES: this
     * EFFECTS: source file to read from set to file
     */
    public JsonReader(String file) {
        this.sourceFile = file;
    }

    // EFFECTS: reads and returns userAccount from file, if source file
    //          has an error, IOException thrown.
    public UserAccount read() throws IOException {
        String jsonData = readFile(sourceFile);
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

    // EFFECTS: parses user book list from JSON object and returns it
    private UserAccount parseUserAccount(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        UserAccount user = new UserAccount(name);
        addBooks(user, jsonObject);
        return user;
    }

    // MODIFIES: user
    // EFFECTS: parses books from JSON object and adds them to user book list
    private void addBooks(UserAccount user, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("list");
        for (Object json : jsonArray) {
            JSONObject nextBook = (JSONObject) json;
            addBook(user, nextBook);
        }
    }

    // MODIFIES: user
    // EFFECTS: parses book from JSON object and adds it to user book list
    private void addBook(UserAccount user, JSONObject jsonObject) {
        System.out.println("Previous list:" + jsonObject);
        String author = jsonObject.getString("author");
        String name = jsonObject.getString("title");
        Boolean check = jsonObject.getBoolean("check read");

        Book b = new Book(name,author,check);
        user.addNewBook(b);
    }
}
