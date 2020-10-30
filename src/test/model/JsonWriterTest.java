package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.
    private UserAccount user;
    JsonWriter writer;
    JsonReader reader;
    Book book;

    @BeforeEach
    public void setUp(){
        user = new UserAccount("test user");
        writer = new JsonWriter("./data/testFile.json");
        book = new Book("Harry Potter", "JK Rowling", false);
    }

    @Test
    void testWriterIIllegalFileName() {
        try {
            writer = new JsonWriter("./data\0testuser.json");
            writer.open();
            fail("File doesn't exist");
        } catch (IOException e) {
            // expected
        }
    }

    @Test
    void testWriterEmptyUserBookList() {
        try {
            writer.open();
            writer.write(user);
            writer.close();

            reader = new JsonReader("./data/testFile.json");
            user = reader.read();
            assertEquals("test user", user.getUserName());
            assertEquals(new ArrayList<Book>(), user.getBookList());
        } catch (IOException e) {
            fail("Unexpected error");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            user.addNewBook(book);
            user.addNewBook(new Book("Julius Caesar", "Shakespeare", false));
            writer.open();
            writer.write(user);
            writer.close();

            reader = new JsonReader("./data/testFile.json");
            user = reader.read();
            assertEquals("test user", user.getUserName());
            ArrayList<Book> bookList = user.getBookList();
            assertEquals(2, bookList.size());
         //   checkThingy("saw", Category.METALWORK, thingies.get(0));
         //   checkThingy("needle", Category.STITCHING, thingies.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
