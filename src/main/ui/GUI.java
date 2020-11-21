package ui;


import model.Book;
import model.UserAccount;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

// This class builds the UI for the BookApp
// source for sound: source: http://soundbible.com/
public class GUI extends JFrame {
    UserAccount acc;
    static String file;
    JsonReader jsonReader;
    JsonWriter jsonWriter;
    private Book bookObject;

    JLabel userName = new JLabel("Enter your name: ");
    JTextField nameTextField = new JTextField(10);
    JButton go = new JButton("Go!");

    JButton add = new JButton("Add Book");
    JButton delete = new JButton("Delete Book");
    JButton sortTitle = new JButton("Sort by title");
    JButton sortAuthor = new JButton("Sort by author");
    JButton markRead = new JButton("Mark as read");
    JButton getSuggestion = new JButton("Get Weekly Suggestion");
    JButton save = new JButton("Save list");
    JButton load = new JButton("Load list");

    JTextArea list = new JTextArea();

    /*
     * MODIFIES: this
     * EFFECTS: creates homepage and displays it, then renders the main ui panel for user to edit their book list.
     *          ui display is created through use of panels and grid. the panel compromises of text area for user
     *          to enter name,buttons for user to make selection and text area, where their book list is displayed.
     */
    public GUI() throws InterruptedException {
        HomePage homePage = new HomePage();
        homePage.setVisible(true);

        design();
        JPanel grid = getJPanel();

        add(list, BorderLayout.EAST);

        add(grid);

        goButton();
        addButton();
        deleteButton();
        suggestionButton();
        titleSortButton();
        authorSortButton();
        saveButton();
        loadButton();
        markReadButton();
    }

    /*
     * MODIFIES: this
     * EFFECTS: title of panel is set to 'Home Library Organiser', size of panel is set to 600x600, panel is centered
     *          and app quits on close button. the text of book list displayed in text area is set to gray.
     */

    private void design() {
        setTitle("Home Library Organiser");
        list.setForeground(Color.GRAY);
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * MODIFIES: this
     * EFFECTS: the panel is made of a 9x2 grid. first row is user text label, text box and 'Go!' button. Rows 2-9 are
     *          button options, and column 2 is text area of user list(only displayed once user hits go button)
     */
    private JPanel getJPanel() {
        JPanel flow0Panel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JPanel flow1Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow3Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow4Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow5Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow6Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow7Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel flow8Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel grid = new JPanel(new GridLayout(9, 2));

        userTextPanel(flow0Panel);

        buttonPanel(flow1Panel, flow2Panel, flow3Panel, flow4Panel, flow5Panel, flow6Panel, flow7Panel, flow8Panel);

        grid.add(flow0Panel);
        grid.add(flow1Panel);
        grid.add(flow2Panel);
        grid.add(flow3Panel);
        grid.add(flow4Panel);
        grid.add(flow5Panel);
        grid.add(flow6Panel);
        grid.add(flow7Panel);
        grid.add(flow8Panel);

        return grid;
    }

    /* REQUIRES: JPanel flow1Panel (buttons)
     * MODIFIES: this
     * EFFECTS: rows 2-9 of panel with buttons added
     */
    private void buttonPanel(JPanel flow1Panel, JPanel flow2Panel, JPanel flow3Panel, JPanel flow4Panel,
                             JPanel flow5Panel, JPanel flow6Panel, JPanel flow7Panel, JPanel flow8Panel) {
        flow1Panel.add(add);
        flow2Panel.add(delete);
        flow3Panel.add(sortTitle);
        flow4Panel.add(sortAuthor);
        flow5Panel.add(markRead);
        flow6Panel.add(getSuggestion);
        flow7Panel.add(save);
        flow8Panel.add(load);
    }

    /* REQUIRES: JPanel flow0Panel
     * MODIFIES: this
     * EFFECTS: panel made of text label(Enter username:), text box(for user to input name) and button(Go!)
     */
    private void userTextPanel(JPanel flow0Panel) {
        flow0Panel.add(userName);
        flow0Panel.add(nameTextField);
        flow0Panel.add(go);
    }

    /* REQUIRES: JButton button
     * MODIFIES: this
     * EFFECTS: sets the design of the button, color, shape and size modified.
     */
    private void buttonStyle(JButton button) {
        button.setBackground(Color.decode("#CC6568"));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 25));
        button.setOpaque(true);
    }

    /*
     * EFFECTS: when mark as read button clicked, mark() method is run
     */
    private void markReadButton() {
        buttonStyle(markRead);
        markRead.addActionListener(e -> mark());
    }

    /*
     * EFFECTS: when load button clicked, list area is cleared and loadList(nameTextField.getText()) method is run
     */
    private void loadButton() {
        buttonStyle(load);
        load.addActionListener(e -> {
            list.setText("");
            loadList(nameTextField.getText());
        });
    }

    /*
     * EFFECTS: when save button clicked, saveList() method is run
     */
    private void saveButton() {
        buttonStyle(save);
        save.addActionListener(e -> saveList());
    }

    /*
     * EFFECTS: when sort by author button clicked, sortByAuthor() method is run
     */
    private void authorSortButton() {
        buttonStyle(sortAuthor);
        sortAuthor.addActionListener(e -> sortByAuthor());
    }

    /*
     * EFFECTS: when sort by title button clicked, sortByTitle() method is run
     */
    private void titleSortButton() {
        buttonStyle(sortTitle);
        sortTitle.addActionListener(e -> sortByTitle());
    }

    /*
     * EFFECTS: when get weekly suggestion clicked, Dialog box displays a book(method randomize() is run which returns)
     *          book name.
     */
    private void suggestionButton() {
        buttonStyle(getSuggestion);
        getSuggestion.addActionListener(e -> JOptionPane.showMessageDialog(null, randomize()));
    }

    /*
     * EFFECTS: when delete button clicked, delete() method is run
     */
    private void deleteButton() {
        buttonStyle(delete);
        delete.addActionListener(e -> delete());
    }

    /*
     * EFFECTS: when add button clicked, add() method is run
     */
    private void addButton() {
        buttonStyle(add);
        add.addActionListener(e -> add());
    }

    /*
     * EFFECTS: when go button clicked, sound is played, initialize() method is run, text area is cleared and loaded
     *          with user book list(if user previously exists)
     */
    private void goButton() {
        go.addActionListener(e -> {
            playSound();
            initialize(nameTextField.getText());
            list.setText("");
            loadList(nameTextField.getText());
        });
    }

    /*
     *
     * EFFECTS: when user clicks go button, this sound is played.
     *          source: http://soundbible.com/
     */
    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("./data/exit.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    /* REQUIRES: String username as inputted in text field
     * MODIFIES: UserAccount
     * EFFECTS: initializes an account for user, if already exists, loads file with user and user book list
     */
    public void initialize(String userName) {
        file = "./data/" + userName + ".json";
        acc = new UserAccount(userName);
        ArrayList<Book> books = acc.getBookList();
        jsonWriter = new JsonWriter(file);
        jsonReader = new JsonReader(file);
    }

    /*
     * MODIFIES: UserAccount
     * EFFECTS: adds new book entered by user, if book entered is not in list,
     *          then book is added.
     *          Else book is not added. result is displayed on text panel
     */
    public void add() {
        String bookName = JOptionPane.showInputDialog("Enter book title: ");
        String author = JOptionPane.showInputDialog("Enter author: ");
        bookObject = new Book(bookName, author, false);
        boolean canAdd = true;

        for (Book b: acc.getBookList()) {
            if (b.getName().equals(bookName)) {
                canAdd = false;
                JOptionPane.showMessageDialog(null, "Book exists in list");
            }
        }

        if (canAdd == true) {
            acc.addNewBook(bookObject);
        }

        list.setText("");
        ArrayList<Book> books = acc.getBookList();
        for (Book b : books) {
            list.append(b.toString() + "\n");
        }

    }

    /* MODIFIES: UserAccount
     * REQUIRES: string username
     * EFFECTS: loads current user book list
     */
    private void loadList(String userName) {
        try {
            acc = new UserAccount(userName);
            file = "./data/" + userName + ".json";
            jsonReader = new JsonReader(file);
            acc = jsonReader.read();
            ArrayList<Book> books = acc.getBookList();
            for (Book b : books) {
                list.append(b.toString() + "\n");
            }
            JOptionPane.showMessageDialog(null, "Loaded " + acc.getUserName() + " from " + file);
            System.out.println("Loaded " + acc.getUserName() + " from " + file);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + file);
        }
    }

    // EFFECTS: displays random book from user's book list
    public String randomize() {
        int random = new Random().nextInt(acc.getBookList().size());
        return String.valueOf(acc.getBookList().get(random));
    }

    // MODIFIES: UserAccount
    // EFFECTS: saves booklist from file
    private void saveList() {
        String save = JOptionPane.showInputDialog("Do you want to save this list? (Y/N)");
        if (save.equals("Y")) {
            try {
                jsonWriter.open();
                jsonWriter.write(acc);
                jsonWriter.close();

                JOptionPane.showMessageDialog(null, "Saved " + acc.getUserName() + " to " + file);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + file);
            }
        }

        list.setText("");
        loadList(acc.getUserName());
    }

    // EFFECTS: sorts booklist by title
    private void sortByTitle() {
        ArrayList<String> titles = new ArrayList<>();
        for (int i = 0; i < acc.getBookList().size(); i++) {
            titles.add(acc.getBookList().get(i).getName());
        }
        list.setText("");
        for (String t : titles) {
            list.append(t + "\n");
        }
    }

    // EFFECTS: sorts booklist by author
    private void sortByAuthor() {
        Collections.sort(acc.getBookList());
        list.setText("");
        for (Book b : acc.getBookList()) {
            list.append(b.toString() + "\n");
        }
    }

    /*
     * MODIFIES: UserAccount
     * EFFECTS: sets book read as true for inputted title and displays list
     *          of read books in text area.
     */
    private void mark() {
        String book = JOptionPane.showInputDialog("Enter title of book from list to mark as read: ");

        for (int i = 0; i < acc.getBookList().size(); i++) {
            if (acc.getBookList().get(i).getName().equals(book)) {
                acc.getBookList().get(i).setCheckRead(true);
            }
        }

        ArrayList<Book> result = new ArrayList<>();
        for (int i = 0; i < acc.getBookList().size(); i++) {
            if (acc.getBookList().get(i).getCheckRead()) {
                result.add(acc.getBookList().get(i));
            }
        }

        list.setText("Books read are: \n");
        for (Book b : result) {
            list.append(b.toString() + "\n");
        }

    }

    /*
     * MODIFIES: UserAccount
     * EFFECTS: deletes book entered by user, if entered book name
     *          book exists in list. Else book not deleted. Displays current book list in text area
     */
    private void delete() {
        String book = JOptionPane.showInputDialog("Enter title of book to delete: ");

        for (Book b: acc.getBookList()) {
            if (b.getName().equals(book)) {
                acc.deleteBook(b);
            }
        }

        ArrayList<Book> result = new ArrayList<>();
        for (Book b: acc.getBookList()) {
            result.add(b);
        }

        list.setText("");
        for (Book b : result) {
            list.append(b.toString() + "\n");
        }

    }

}
