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
        testUser = new UserAccount("Scofield");
        testBooks = new ArrayList<Book>();
        bookObj = new Book("A Brief History of Time", "Stephen Hawking", false);
    }

    @Test
    void testConstructor() {
        assertEquals("Scofield", testUser.getUserName());
        assertEquals(0,testUser.getBookList().size());
    }

    @Test
    void testAddNewBook() {
        testUser.addNewBook(bookObj);
        bookObj.setCheckRead(true);
        assertTrue(testUser.getBookList().size()==1);
        assertTrue(testUser.getBookList().contains(bookObj));
        assertTrue(testUser.getBookList().get(0).getCheckRead());
    }

    @Test
    void testDeleteBook() {
        testUser.deleteBook(bookObj);
        assertTrue(testUser.getBookList().size()==0);
    }

    @Test
    void testAddMultipleNewBooks() {
        Book bookObj1 = new Book("Julius Caesar", "Shakespeare", true);
        testUser.addNewBook(bookObj);
        testUser.addNewBook(bookObj1);
        assertTrue(testUser.getBookList().size()==2);
    }

    @Test
    void testAddDuplicateBooks() {
        Book bookObj1 = new Book("Julius Caesar", "Shakespeare", false);
        testUser.addNewBook(bookObj1);
        testUser.addNewBook(bookObj1);
        assertTrue(testUser.getBookList().size()==1);
    }

    @Test
    void testMultipleDeleteBooks() {
        Book bookObj1 = new Book("Julius Caesar", "Shakespeare", false);
        Book bookObj2 = new Book("Lord of the Flies", "William Golding", false);
        testUser.addNewBook(bookObj);
        testUser.addNewBook(bookObj1);
        testUser.addNewBook(bookObj2);
        testUser.addNewBook(bookObj);
        System.out.println(testUser.getBookList());
        assertTrue(testUser.getBookList().size()==3);
        testUser.deleteBook(bookObj2);
        assertTrue(testUser.getBookList().size()==2);
    }

    @Test
    void testToString() {
        testUser.addNewBook(bookObj);
        assertTrue( testUser.toString().contains
                ("user name = Scofield, book list = [[Book title: A Brief History of Time"));
    }

}