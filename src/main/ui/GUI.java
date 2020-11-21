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

    private void design() {
        list.setForeground(Color.GRAY);
        setTitle("Home Library Organiser");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

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

    private void userTextPanel(JPanel flow0Panel) {
        flow0Panel.add(userName);
        flow0Panel.add(nameTextField);
        flow0Panel.add(go);
    }

    private void buttonStyle(JButton button) {
        button.setBackground(Color.decode("#CC6568"));
        button.setForeground(Color.WHITE);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 25));
        button.setOpaque(true);
    }

    private void markReadButton() {
        buttonStyle(markRead);
        markRead.addActionListener(e -> mark());
    }

    private void loadButton() {
        buttonStyle(load);
        load.addActionListener(e -> {
            list.setText("");
            loadList(nameTextField.getText());
        });
    }

    private void saveButton() {
        buttonStyle(save);
        save.addActionListener(e -> saveList());
    }

    private void authorSortButton() {
        buttonStyle(sortAuthor);
        sortAuthor.addActionListener(e -> sortByAuthor());
    }

    private void titleSortButton() {
        buttonStyle(sortTitle);
        sortTitle.addActionListener(e -> sortByTitle());
    }

    private void suggestionButton() {
        buttonStyle(getSuggestion);
        getSuggestion.addActionListener(e -> JOptionPane.showMessageDialog(null, randomize()));
    }

    private void deleteButton() {
        buttonStyle(delete);
        delete.addActionListener(e -> delete());
    }

    private void addButton() {
        buttonStyle(add);
        add.addActionListener(e -> add());
    }

    private void goButton() {


        go.addActionListener(e -> {
            playSound();
            initialize(nameTextField.getText());
            list.setText("");
            loadList(nameTextField.getText());
        });
    }

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

    public void initialize(String userName) {
        file = "./data/" + userName + ".json";
        acc = new UserAccount(userName);
        ArrayList<Book> books = acc.getBookList();
        jsonWriter = new JsonWriter(file);
        jsonReader = new JsonReader(file);
    }

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

    public String randomize() {
        int random = new Random().nextInt(acc.getBookList().size());
        return String.valueOf(acc.getBookList().get(random));
    }

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

    private void sortByAuthor() {
        Collections.sort(acc.getBookList());
        list.setText("");
        for (Book b : acc.getBookList()) {
            list.append(b.toString() + "\n");
        }
    }

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

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }
}
