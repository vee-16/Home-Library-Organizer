package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {
    private UserAccount testUser;
    private ArrayList<String> testBooks;

    @BeforeEach
    void runBefore() {
        testBooks = new ArrayList<String>();
        testBooks.add("A Brief History of Time");
        testUser = new UserAccount("Scofield", testBooks);
    }

    @Test
    void testConstructor() {
        assertEquals("Scofield", testUser.getUserName());
        assertEquals(1,testUser.getBookList().size());

    }

    @Test
    void testConstructorNoBooks() {
        testUser = new UserAccount("Rithin", new ArrayList<>());
        assertEquals("Rithin", testUser.getUserName());
        assertEquals(0,testUser.getBookList().size());

    }

    @Test
    void testAddNewBook() {
        testUser.addNewBook("A Short History of Nearly Everything");
        assertTrue(testUser.getBookList().size()==2);
        assertTrue(testUser.getBookList().contains("A Short History of Nearly Everything"));
    }

    @Test
    void testDeleteBook() {
        testUser.deleteBook("A Brief History of Time");
        assertTrue(testUser.getBookList().size()==0);
    }

    @Test
    void testAddMultipleNewBooks() {
        testUser.addNewBook("A Short History of Nearly Everything");
        testUser.addNewBook("Julius Caesar");
        assertTrue(testUser.getBookList().size()==3);

    }

    @Test
    void testAddDuplicateBooks() {
        testUser.addNewBook("Julius Caesar");
        testUser.addNewBook("Julius Caesar");
        assertTrue(testUser.getBookList().size()==2);
    }

    @Test
    void testMultipleDeleteBooks() {
        testUser.addNewBook("Julius Caesar");
        testUser.addNewBook("Lord of the Flies");
        testUser.deleteBook("A Brief History of Time");
        testUser.deleteBook("Lord of the Flies");
        assertTrue(testUser.getBookList().size()==1);
        testUser.deleteBook("A Brief History of Time");
        assertTrue(testUser.getBookList().size()==1);

    }

    @Test
    void testToString() {
        assertTrue( testUser.toString().contains("user name = Scofield, book list = {[[A Brief History of Time]]}"));
    }

}