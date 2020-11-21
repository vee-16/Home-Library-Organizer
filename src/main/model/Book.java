package model;

import org.json.JSONObject;
import persistance.Writable;

import java.util.Arrays;

// Represents the object book with it's title, author and
// whether it has been read by user
public class Book implements Comparable<Book>, Writable {

    private String name;
    private String author;
    private boolean checkRead;


    /*
     * REQUIRES: title of book
     * MODIFIES: this
     * EFFECTS: name of book is set to title.
     */
    public Book(String title, String author, boolean check) {
        name = title;
        this.author = author;
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
        return "Book title: " + getName() + ", Author Name: " + getAuthor();
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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("title", name);
        json.put("author", author);
        json.put("check read", checkRead);
        return json;
    }
}