package model;

import java.util.Arrays;

// Represents the object book with it's title, author and
// whether it has been read by user
public class Book implements Comparable<Book> {

    private String name;
    private String author;
    private boolean checkRead;

    /*
     * REQUIRES: title of book
     * MODIFIES: this
     * EFFECTS: name of book is set to title.
     */
    public Book(String title, boolean check) {
        name = title;
        checkRead = check;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public boolean getCheckRead() {
        return checkRead;
    }

    public void setCheckRead(boolean read) {
        checkRead = read;
    }

    public void setAuthor(String authorName) {
        author = authorName;
    }

    /*
     * EFFECTS: returns string representation of the book object(Title and Author)
     */
    @Override
    public String toString() {
        return "Book title: " + getName() + ",Author Name: " + getAuthor();
    }

    /*
     * REQUIRES: Book object
     * EFFECTS: returns a comparison value between two book objects by author names
     *          so that list can be sorted by author.
     */
    @Override
    public int compareTo(Book other) {
        return author.compareTo(other.author);
    }

}