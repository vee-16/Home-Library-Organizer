package ui;

import model.Book;
import model.UserAccount;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class GUIform extends JFrame {

    public JButton a1AddBookButton;
    private JButton a2DeleteBookButton;
    private JButton a3SortByTitleButton;
    private JButton a4SortByAuthorButton;
    private JButton a5MarkAsReadButton;
    private JButton a6BookOfTheButton;
    private JButton a7SaveBookListButton;
    private JButton a8LoadBookListButton;
    private JButton quitButton;
    private JPanel rootPanel;

    public GUIform() throws FileNotFoundException {

        add(rootPanel);
        setTitle("My title");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400,500);


        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playSound();
            }
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
}
