package com.example.life;

import java.awt.EventQueue;
import javax.swing.JFrame;

public final class Application extends JFrame {
    public Application(ArgsParser argsParser) {
        setContentPane(new Board(argsParser));

        setResizable(true);
        pack();

        setTitle("Life");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        ArgsParser argsParser = new ArgsParser(args);
        if (!argsParser.getDidSucceed()) {
            System.exit(1);
        }
        EventQueue.invokeLater(() -> {
            Application ex = new Application(argsParser);
            ex.setVisible(true);
        });
    }
}
