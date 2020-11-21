package ui;

import javax.swing.*;
import java.awt.*;

/**
 ** sources: https://www.tutorialspoint.com/how-to-change-jlabel-font-in-java
 **            https://stackoverflow.com/questions/16134549/how-to-make-a-splash-screen-for-gui
 **/

public class HomePage extends JWindow {

    ImageIcon icon;

    public HomePage() throws InterruptedException {
        JWindow j = new JWindow();

        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();

        icon = new ImageIcon("data/img.jpg");

        JPanel panel = new JPanel();

        LayoutManager imageTextOverlap = new OverlayLayout(panel);
        panel.setLayout(imageTextOverlap);

        JLabel title = new JLabel("Your Home Library Organiser");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Verdana", Font.PLAIN, 30));
        title.setAlignmentX(0.5f);
        title.setAlignmentY(0.5f);
        panel.add(title);

        JLabel label2 = new JLabel(icon);
        label2.setAlignmentX(0.5f);
        label2.setAlignmentY(0.5f);
        panel.add(label2);

        j.getContentPane().add(panel);
        j.setBounds(((int) d.getWidth() - 800) / 2, ((int) d.getHeight() - 533) / 2, 800, 533);
        j.setVisible(true);

        Thread.sleep(5000);

        j.setVisible(false);
    }


}
