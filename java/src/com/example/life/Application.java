package com.example.life;

import java.awt.EventQueue;
import javax.swing.JFrame;

public final class Application extends JFrame {

    public Application() {
        setContentPane(new Board());

        setResizable(false);
        pack();

        setTitle("Life");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Application ex = new Application();
            ex.setVisible(true);
        });
    }
}
