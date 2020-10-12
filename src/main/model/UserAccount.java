package model;

import java.util.ArrayList;
import java.util.Arrays;

// Represents the user's account with id, name of account holder
// and their book list
public class UserAccount {
    private static int nextID = 1;   // tracks id of next user
    private int userId;              // account id
    private String userName;         // account holder name
    private ArrayList<String> bookList;      // account's current book list

    /*
     * REQUIRES:
     * EFFECTS: userName of account holder is set to name;
     *          userId is positive and unique to the account holder;
     *          if books.size() > 0 then bookList on user account is
     *          set to books, otherwise bookList is initialized as
     *          empty arrayList.
     */

    public UserAccount(String name, ArrayList<String> books) {
        userId = nextID++;
        userName = name;
        if (books.size() > 0) {
            bookList = new ArrayList<String>(books);
        } else {
            bookList = new ArrayList<String>();
        }
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<String> getBookList() {
        return bookList;
    }

    /*
     * REQUIRES: bookName
     * MODIFIES: this
     * EFFECTS: book is added to bookList and
     *          updated bookList is returned
     */
    public ArrayList<String> addNewBook(String bookName) {
        if (!(bookList.contains(bookName))) {
            bookList.add(bookName);
        }
        return bookList;
    }

    /*
     * REQUIRES: bookName and getBookList().size() > 0
     * MODIFIES: this
     * EFFECTS:  book is deleted from bookList and
     *           updated bookList is returned
     */
    public ArrayList<String> deleteBook(String bookName) {
        bookList.remove(bookName);
        return bookList;
    }


    /*
     * EFFECTS: returns string representation of the user account
     */
    @Override
    public String toString() {
        return "[ user id = " + userId + ", user name = " + userName + ", "
                + "book list = {" + Arrays.asList(bookList) + "}";
    }

}