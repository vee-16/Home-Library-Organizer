package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;
/***
 *    CITATION:
 *    Title: CPSC210/JsonSerializationDemo
 *    Author: Paul Carter
 *    Availability: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 *
 ***/
public class JsonWriterTest {

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
    void testWriterUserBookList() {
        try {
            user.addNewBook(book);
            user.addNewBook(new Book("Julius Caesar", "Shakespeare", false));
            writer.open();
            writer.write(user);
            writer.close();
            assertTrue(user.getBookList().size()==2);

            reader = new JsonReader("./data/testFile.json");
            user = reader.read();
            assertEquals("test user", user.getUserName());
            ArrayList<Book> bookList = user.getBookList();
            assertEquals(2, bookList.size());

        } catch (IOException e) {
            fail("Unexpected IOException");
        }
    }
}
