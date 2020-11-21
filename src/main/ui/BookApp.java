package ui;

import model.UserAccount;
import model.Book;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.*;

// Library organizer application
public class BookApp {
    private static String file;
    private UserAccount acc;
    private Book bookObject;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    boolean run = true;

    // EFFECTS: runs the book application
    public BookApp() throws FileNotFoundException {
        runBook();
    }

    public BookApp(String name) {
        file = "./data/" + name + ".json";
        acc = new UserAccount(name);
        jsonWriter = new JsonWriter(file);
        jsonReader = new JsonReader(file);
        ArrayList<Book> books = acc.getBookList();
        runInterface();
    }

    /*
     * MODIFIES: this
     * EFFECTS: interprets input from the user
     */
    private void runBook() throws FileNotFoundException {
        String userCommand;
        initialize();

        do {
            menu();
            userCommand = input.next().toLowerCase();
            processCommand(userCommand);
        } while (run);

        System.out.print("\nExit.");

    }

    private ArrayList<Book> runInterface() {
        return acc.getBookList();
    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String userCommand) {
        switch (userCommand) {
            case "1": add();
                break;
            case "2": delete();
                break;
            case "3": sortByTitle();
                break;
            case "4": sortByAuthor();
                break;
            case "5": mark();
                break;
            case "6": randomize();
                break;
            case "7": saveList();
                break;
            case "8": loadList();
                break;
            case "q": run = false;
                break;
            default:
                System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    public void initialize() throws FileNotFoundException {
        input = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = input.next();
        file = "./data/" + name + ".json";
        ArrayList<Book> books = new ArrayList<Book>();
        acc = new UserAccount(name);
        jsonWriter = new JsonWriter(file);
        jsonReader = new JsonReader(file);
    }

    // EFFECTS: displays menu of options to user
    private void menu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add book");
        System.out.println("\t2 -> delete book");
        System.out.println("\t3 -> sort by title");
        System.out.println("\t4 -> sort by author");
        System.out.println("\t5 -> mark book as read");
        System.out.println("\t6 -> get book of the week suggestion");
        System.out.println("\t7 -> save current book list");
        System.out.println("\t8 -> load previous book list");
        System.out.println("\tq -> quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds new book entered by user, if book entered is not in list,
     *          then book is added.
     *          Else book is not added.
     */
    public void add() {
        System.out.print("Enter book to add: ");
        String book = input.next();

        if ((!(acc.getBookList().contains(book)))) {
            bookObject = new Book(book, "",false);
            System.out.print("Enter author: ");
            String author = input.next();
            bookObject.setAuthor(author);
            acc.addNewBook(bookObject);
        } else if (acc.getBookList().contains(book)) {
            System.out.println("\nBook exists in list");
        } else {
            System.out.println("\nPlease enter a book");
        }

        printBookList(acc);

    }

    /*
     * MODIFIES: this
     * EFFECTS: deletes book entered by user, if entered book name
     *          book exists in list. Else book not deleted.
     */
    private void delete() {
        System.out.print("Enter book to delete: ");
        String book = input.next();

        for (int i = 0; i < acc.getBookList().size(); i++) {
            if (acc.getBookList().get(i).getName().equals(book)) {
                acc.deleteBook(acc.getBookList().get(i));
            } else {
                System.out.print("Book is not in list.");
            }
        }

        printBookList(acc);
    }

    /*
     * MODIFIES: this
     * EFFECTS: outputs book title list sorted by title of each book to the user
     */
    private void sortByTitle() {
        ArrayList<String> titles = new ArrayList<String>();
        for (int i = 0; i < acc.getBookList().size(); i++) {
            titles.add(acc.getBookList().get(i).getName());
        }
        Collections.sort(titles);
        System.out.println("Sorted list by title: " +  titles.toString());
    }

    /*
     * MODIFIES: this
     * EFFECTS: sorts books by author name and outputs book title and author name
     */
    private void sortByAuthor() {
        Collections.sort(acc.getBookList());
        System.out.println("Sorted list by author: " +  acc.getBookList());
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets book read as true for inputted title and outputs list
     *          of read books.
     */
    private void mark() {
        System.out.print("Enter title of book from list to mark as read: ");
        String book = input.next();

        for (int i = 0; i < acc.getBookList().size(); i++) {
            if (acc.getBookList().get(i).getName().equals(book)) {
                acc.getBookList().get(i).setCheckRead(true);
            }
        }

        ArrayList<Book> result = new ArrayList<Book>();
        for (int i = 0; i < acc.getBookList().size(); i++) {
            if (acc.getBookList().get(i).getCheckRead()) {
                result.add(acc.getBookList().get(i));
            }
        }
        System.out.print("Books read are: " + result.toString());
    }


    // EFFECTS: prints random book from user's book list
    public String randomize() {
        int random = new Random().nextInt(acc.getBookList().size());
        return String.valueOf(acc.getBookList().get(random));
    }

    // EFFECTS: prints updated book list to the screen to user.
    public void printBookList(UserAccount acc) {
        ArrayList<Book> books = acc.getBookList();
        System.out.print("Your updated book list: " + books.toString());
    }

    // MODIFIES: this
    // EFFECTS: save booklist to file
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(acc);
            jsonWriter.close();
            System.out.println("Saved " + acc.getUserName() + " to " + file);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + file);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads booklist from file
    private void loadList() {
        try {
            acc = jsonReader.read();
            System.out.println("Loaded " + acc.getUserName() + " from " + file);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + file);
        }
    }

}
