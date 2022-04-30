package org.example;

import javax.swing.*;
import java.awt.*;

public class MainFrame {

    public MainFrame(int width, int height, String title, Game game) {
        JFrame frame = new JFrame(title);
//        frame.setContentPane();
        frame.setPreferredSize(new Dimension(width,height));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.add(game);
//        frame.add(MiniMap); todo Каким-то образом добавить мини-карту
        frame.setVisible(true);
        game.start();
    }
}
