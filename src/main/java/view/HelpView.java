package view;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class HelpView extends JPanel {
    private final Image helpPicture;
    private MainMenuPanel mainMenuPanel;
    private ImageIcon backIcon;

    public HelpView(MainMenuPanel mainMenuPanel) throws IOException {
        this.mainMenuPanel = mainMenuPanel;
        this.setLayout(null);

        Image backImage = ImageIO.read(new File("Images/arrowLeft.png")).getScaledInstance(30,20,Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(backImage);
        JButton backButton = new JButton(backIcon);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setFocusPainted(false);
        backButton.setLayout(null);
        backButton.setBounds(30,10, backIcon.getIconWidth(), backIcon.getIconHeight());
        backButton.addActionListener(e -> {
            setVisible(false);
            mainMenuPanel.setButtonsEnabled(true);
            mainMenuPanel.setButtonsVisible(true);
        });
        add(backButton);
        helpPicture = ImageIO.read(new File("Images/glossary1.png")).getScaledInstance(600,600,Image.SCALE_SMOOTH);
    }

    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if(helpPicture!=null){
            g.drawImage(helpPicture,0,0,this);
        }
    }
}