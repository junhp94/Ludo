import view.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class Game{
    private static final int GAME_LOOP_DELAY_MS = 10;
    private static final Dimension windowSize = new Dimension(800,640);
    public static void main(String args[]) throws IOException{
        JFrame frame = new JFrame("Ludo");

        frame.setLayout(null);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        view.MainMenuPanel mainMenuPanel = new view.MainMenuPanel();
        mainMenuPanel.setLayout(null);
        mainPanel.add(mainMenuPanel);
        mainMenuPanel.setPreferredSize(windowSize);

        GamePanel gamePanel = new GamePanel(mainMenuPanel);
        gamePanel.setLayout(null);
        mainPanel.add(gamePanel);
        gamePanel.setPreferredSize(windowSize);
        gamePanel.setVisible(false);

        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowSize);
        frame.setVisible(true);

        mainMenuPanel.setMainMenuPanelListener(new MainMenuPanelListener() {
            @Override
            public void onStartGame() throws IOException {
                gamePanel.setVisible(true);
                mainMenuPanel.setVisible(false);
                startGameLoop(gamePanel);
            }
            @Override
            public void onSetting() {}
            @Override
            public void onHelp() {}
            @Override
            public void onExit() {
                System.exit(0);
            }
        });
    }
    private static void startGameLoop(GamePanel gamePanel){
        Timer timer = new Timer(GAME_LOOP_DELAY_MS, e -> gamePanel.activeLoop());
        timer.start();
    }
}





