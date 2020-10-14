package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserAccountTest {
    private UserAccount testUser;
    private ArrayList<Book> testBooks;
    private Book bookObj;


    @BeforeEach
    void runBefore() {
        bookObj = new Book("A Brief History of Time", false);
        testBooks = new ArrayList<Book>();
        testBooks.add(bookObj);
        testUser = new UserAccount("Scofield", testBooks);
    }

    @Test
    void testConstructor() {
        assertEquals("Scofield", testUser.getUserName());
        assertEquals(1,testUser.getBookList().size());
    }

    @Test
    void testConstructorNoBooks() {
        testUser = new UserAccount("Rithin", new ArrayList<Book>());
        assertEquals("Rithin", testUser.getUserName());
        assertEquals(0,testUser.getBookList().size());
    }

    @Test
    void testAddNewBook() {
        bookObj = new Book("A Short History of Nearly Everything", false);
        testUser.addNewBook(bookObj);
        bookObj.setCheckRead(true);
        assertTrue(testUser.getBookList().size()==2);
        assertTrue(testUser.getBookList().contains(bookObj));
        assertTrue(testUser.getBookList().get(1).getCheckRead());
    }

    @Test
    void testDeleteBook() {
        testUser.deleteBook(bookObj);
        assertTrue(testUser.getBookList().size()==0);
    }

    @Test
    void testAddMultipleNewBooks() {
        bookObj = new Book("A Short History of Nearly Everything", false);
        Book bookObj1 = new Book("Julius Caesar", true);
        testUser.addNewBook(bookObj);
        testUser.addNewBook(bookObj1);
        bookObj1.setAuthor("Shakespeare");
        bookObj.setAuthor("Bill Bryson");
        assertTrue(testUser.getBookList().size()==3);
        assertFalse(testUser.getBookList().get(1).getCheckRead());
        assertEquals("Shakespeare",testUser.getBookList().get(2).getAuthor());
        assertTrue(testUser.getBookList().get(1).compareTo(bookObj1)<0);
    }

    @Test
    void testAddDuplicateBooks() {
        bookObj = new Book("Julius Caesar", false);
        testUser.addNewBook(bookObj);
        testUser.addNewBook(bookObj);
        assertTrue(testUser.getBookList().size()==2);
    }

    @Test
    void testMultipleDeleteBooks() {
        bookObj = new Book("Julius Caesar", true);
        Book bookObj1 = new Book("Lord of the Flies", false);
        Book bookObj2 = new Book("A Brief History of Time", false);
        testUser.addNewBook(bookObj);
        testUser.addNewBook(bookObj1);
        testUser.addNewBook(bookObj2);
        testUser.addNewBook(bookObj1);
        assertTrue(testUser.getBookList().size()==4);
        testUser.deleteBook(bookObj2);
        assertTrue(testUser.getBookList().size()==3);
        assertTrue(bookObj.getCheckRead());
    }

    @Test
    void testToString() {
        assertTrue( testUser.toString().contains
                ("user name = Scofield, book list = [[Book title: A Brief History of Time"));
    }


}