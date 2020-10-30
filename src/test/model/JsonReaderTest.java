package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {
    JsonReader reader;
    JsonWriter writer;
    UserAccount userAccount;
    Book book;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        userAccount = new UserAccount("test user");
        writer = new JsonWriter("./data/testFile.json");
        book = new Book("Harry Potter", "JK Rowling", false);
        writer.open();
        writer.write(userAccount);
        writer.close();
    }

    @Test
    void testReaderInvalidFile() {
        reader = new JsonReader("./data/noFile.json");
        try {
            userAccount = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyUserList() {
        reader = new JsonReader("./data/testFile.json");
        try {
            userAccount = reader.read();
            assertEquals("test user", userAccount.getUserName());
            assertEquals(0, userAccount.getBookList().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderUserBookList() {
        JsonReader reader = new JsonReader("./data/testFile.json");
        try {
            userAccount = reader.read();
            assertEquals("test user", userAccount.getUserName());
            ArrayList<Book> bk = userAccount.getBookList();
            bk.add(book);
            assertEquals(1, bk.size());
            assertEquals("Harry Potter", bk.get(0).getName());
            //checkThingy("needle", Category.STITCHING, thingies.get(0));
            //checkThingy("saw", Category.WOODWORK, thingies.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
