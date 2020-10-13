package ui;

import model.UserAccount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Random;

// Library organizer application
public class BookApp {
    private UserAccount acc;
    private Scanner input;
    boolean run = true;

    // EFFECTS: runs the book application
    public BookApp() {
        runBook();
    }

    /*
     * MODIFIES: this
     * EFFECTS: interprets input from the user
     */
    private void runBook() {
        String userCommand;
        initialize();

        while (run) {
            menu();
            userCommand = input.next().toLowerCase();

            if (userCommand.equals("q")) {
                run = false;
            } else {
                processCommand(userCommand);
            }
        }

        System.out.print("\nGoodbye!");

    }


    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String userCommand) {
        switch (userCommand) {
            case "1": add();
                break;
            case "2": delete();
                break;
            case "3": sort();
                break;
            case "4": randomize();
                break;
            default:
                System.out.println("Invalid input.");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes accounts
    private void initialize() {
        input = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = input.next();
        System.out.print("Enter books: ");
        ArrayList<String> books = new ArrayList<String>();
        while (input.hasNext()) {
            books.add(input.next());
        }
        acc = new UserAccount(name, books);
    }

    // EFFECTS: displays menu of options to user
    private void menu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add book");
        System.out.println("\t2 -> delete book");
        System.out.println("\t3 -> sort by title");
        System.out.println("\t4 -> get book of the week suggestion");
        System.out.println("\tq -> quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds new book entered by user, if entered length of book name
     *          is greater than 0 and, to if book entered is not in list,
     *          then book is added.
     *          Else book is not added.
     */
    private void add() {
        System.out.print("Enter book to add: ");
        String book = input.nextLine();

        if ((book.length() > 0) && (!(acc.getBookList().contains(book)))) {
            acc.addNewBook(book);
        } else {
            System.out.println("\nPlease enter a book");
        }

        printBookList(acc);

    }

    /*
     * MODIFIES: this
     * EFFECTS: deletes book entered by user, if entered length of book name
     *          is greater and book exists in list. Else book not deleted.
     */
    private void delete() {
        System.out.print("Enter book to add: ");
        String book = input.nextLine();

        if ((book.length() > 0) && (acc.getBookList().contains(book))) {
            acc.deleteBook(book);
        } else {
            System.out.println("\nPlease enter a book");
        }

        printBookList(acc);
    }

    /*
     * MODIFIES: this
     * EFFECTS: outputs list sorted by title to the user
     */
    private void sort() {
        Collections.sort(acc.getBookList());
        System.out.println("Sorted list: " +  acc.getBookList());
    }


    // EFFECTS: prints random book from user's book list
    private void randomize() {
        int random = new Random().nextInt(acc.getBookList().size());
        System.out.print(acc.getBookList().get(random));
    }

    // EFFECTS: prints updated book list to the screen to user.
    public void printBookList(UserAccount acc) {
        System.out.print("Your updated book list: " + acc.getBookList());
    }
}
