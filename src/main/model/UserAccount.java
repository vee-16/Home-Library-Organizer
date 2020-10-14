package model;

import java.util.ArrayList;
import java.util.Arrays;

// Represents the user's account with id, name of account holder
// and their book list
public class UserAccount extends Book {

    private String userName;         // account holder name
    private ArrayList<Book> bookList;      // account's current book list


    /*
     * REQUIRES: name of book and list of book
     * MODIFIES: this, books
     * EFFECTS: call to super constructor with book name is made;
     *          userName of account holder is set to name;
     *          if books.size() > 0 then bookList on user account is
     *          set to books, otherwise bookList is initialized as
     *          empty arrayList of type Book.
     */
    public UserAccount(String name, ArrayList<Book> books) {
        super(books.toString(), false);
        userName = name;
        if (books.size() > 0) {
            bookList = new ArrayList<Book>(books);
        } else {
            bookList = new ArrayList<Book>();
        }
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Book> getBookList() {
        return bookList;
    }

    /*
     * REQUIRES: bookName
     * MODIFIES: this, bookName
     * EFFECTS: book is added to bookList and
     *          updated bookList is returned
     */
    public ArrayList<Book> addNewBook(Book bookName) {
        if (!(bookList.contains(bookName))) {
            bookList.add(bookName);
        }
        return bookList;
    }

    /*
     * REQUIRES: bookName and getBookList().size() > 0
     * MODIFIES: this, bookName
     * EFFECTS:  book is deleted from bookList and
     *           updated bookList is returned
     */
    public ArrayList<Book> deleteBook(Book bookName) {
        bookList.remove(bookName);
        return bookList;
    }

    /*
     * EFFECTS: returns string representation of the user account
     */
    @Override
    public String toString() {
        return "[ user name = " + userName + ", "
                + "book list = " + Arrays.asList(bookList.toString()) + "]";
    }

}