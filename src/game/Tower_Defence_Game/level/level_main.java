package game.Tower_Defence_Game.level;

import java.awt.*;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;

import static java.awt.BorderLayout.CENTER;

public class level_main extends JFrame {
    private JLabel statusbar;
    public level_main() {
        initUI();
    }

    private void initUI() {
        statusbar = new JLabel("");
        add(statusbar , BorderLayout.SOUTH);
        add(new levelBoard(statusbar, 1));
        setResizable(false);
        pack();
        setTitle("Tower Defence Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            level_main ex = new level_main();
            ex.setVisible(true);
        });
    }
}
