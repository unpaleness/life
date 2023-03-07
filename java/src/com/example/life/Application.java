package com.example.life;

import java.awt.EventQueue;
import javax.swing.JFrame;

public final class Application extends JFrame {
    public Application(ConfigParser configParser) {
        setContentPane(new Board(new Colony(configParser.getOptionsColony()), configParser.getOptionsBoard()));

        setResizable(true);
        pack();

        setTitle("Life");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Config name is not provided as command line argument");
            System.exit(1);
        }
        ConfigParser configParser = new ConfigParser(args[0]);
        configParser.parse();
        if (!configParser.getDidSucceed()) {
            System.exit(1);
        }
        EventQueue.invokeLater(() -> {
            Application ex = new Application(configParser);
            ex.setVisible(true);
        });
    }
}
