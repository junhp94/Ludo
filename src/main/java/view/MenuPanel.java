package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class MenuPanel extends JPanel {
    private JButton backButton,mainMenuButton,exitButton,restartButton,saveButton,loadButton;
    private MenuPanelListener listener;
    public MenuPanel() {
        this.setBackground(new Color(224,224,224));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize buttons and add to the panel
        backButton = createButton("CLOSE", new Color(153, 204, 255));
        mainMenuButton = createButton("BACK TO MAIN", new Color(204, 255, 153));
        exitButton = createButton("EXIT", new Color(128, 128, 128));
        restartButton = createButton("NEW GAME", new Color(255, 255, 153));
        saveButton = createButton("SAVE", new Color(255, 153, 153));
        loadButton = createButton("LOAD", new Color(255, 204, 153));

        gbc.gridy = 0;
        add(saveButton, gbc);
        gbc.gridy = 1;
        add(loadButton, gbc);
        gbc.gridy = 2;
        add(restartButton, gbc);
        gbc.gridy = 3;
        add(mainMenuButton, gbc);
        gbc.gridy = 4;
        add(backButton, gbc);
        gbc.gridy = 5;
        add(exitButton, gbc);
    }
    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        Dimension buttonSize = new Dimension(180,50);
        button.setPreferredSize(buttonSize);
        button.setMaximumSize(buttonSize);
        button.addActionListener(e -> {
            if (listener != null) {
                switch (text) {
                    case "CLOSE":
                        listener.onCloseMenu();
                        break;
                    case "BACK TO MAIN":
                        try {
                            listener.backToMain();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "EXIT":
                        System.exit(0);
                        break;
                    case "NEW GAME":
                        listener.onResetGame();
                        listener.onCloseMenu();
                        break;
                    case "SAVE":
                        try {
                            listener.saveFile();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "LOAD":
                        try {
                            listener.restoreFile();
                            listener.onCloseMenu();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    default:
                        break;
                }
            }
        });

        button.setToolTipText(text);
        button.setFont(button.getFont().deriveFont(Font.BOLD));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(color);
        button.setForeground(Color.black);

        return button;
    }
    public void setMenuPanelListener(MenuPanelListener listener){this.listener=listener;}
}
