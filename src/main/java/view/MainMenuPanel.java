package view;
import utilities.SoundManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Set;

public class MainMenuPanel extends JPanel{
    private JButton startButton,settingButton,helpButton,exitButton;
    private JLabel logo, mainMenuLabel;
    private Image logoImage, mainMenuImage;
    private ImageIcon startButtonImage,settingButtonImage,helpButtonImage,exitButtonImage;
    private MainMenuPanelListener listener;
    private SettingsView settingsView;
    private HelpView helpView;

    public MainMenuPanel() throws IOException {
        setLayout(null);
        loadImages();
        setLogoImage();
        setMainMenuImage();
        setStartButton();
        setSettingButton();
        setHelpButton();
        setExitButton();
        setSettingsView();
        setHelpView();
        setMusic();
    }

    public void setMusic(){
        String bgmPath = "Music/bgm1.wav";
        SoundManager.playSoundEffect(bgmPath, 0.5f);
    }
    public void loadImages() throws  IOException{
        logoImage = ImageIO.read(new File("Images/logo.png")).getScaledInstance(150,150,Image.SCALE_SMOOTH);
        mainMenuImage = ImageIO.read(new File("Images/mainMenu.png"));
        startButtonImage = new ImageIcon("Images/button_start.png");
        settingButtonImage = new ImageIcon("Images/button_settings.png");
        helpButtonImage = new ImageIcon("Images/button_help.png");
        exitButtonImage = new ImageIcon("Images/button_exit.png");
    }
    public void setLogoImage(){
        logo = new JLabel(new ImageIcon(logoImage));
        logo.setBounds(30,30,150,150);
        add(logo);
    }

    public void setMainMenuImage(){
        mainMenuLabel = new JLabel(new ImageIcon(mainMenuImage));
        mainMenuLabel.setBounds(350,100,mainMenuImage.getWidth(null),mainMenuImage.getHeight(null));
        add(mainMenuLabel);
    }


    public void setStartButton(){
        startButton = new JButton(startButtonImage);
        startButton.setBounds(465,200,startButtonImage.getIconWidth(),startButtonImage.getIconHeight());
        add(startButton);
        startButton.addActionListener(e -> {
            try {
                listener.onStartGame();
                SoundManager.stopSound();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public void setSettingButton(){
        settingButton = new JButton(settingButtonImage);
        settingButton.setBounds(465,300,settingButtonImage.getIconWidth(),settingButtonImage.getIconHeight());
        add(settingButton);
        settingButton.addActionListener(e -> {
            if(settingsView!=null){showSettings();}
        });
    }
    public void setSettingsView() throws IOException {
        settingsView = new SettingsView(this);
        settingsView.setBounds(150,150,400,200);
        settingsView.setBackground(Color.white);
        settingsView.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK, 2),
                "Settings", // Replace "Settings" with your desired title
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("SansSerif", Font.BOLD, 16),
                Color.BLACK
        ));
        settingsView.setVisible(false);
        add(settingsView);

        // Set the volume slider listener to update music volume
        settingsView.setVolumeSliderListener(e -> {
            int volume = settingsView.getVolumeValue();
            float normalizedVolume = volume / 100.0f;
            SoundManager.setVolume(normalizedVolume);
        });

        // Set the back button listener to hide the settings view
        settingsView.setBackButtonListener(e -> {
            settingsView.setVisible(false);
            setButtonsEnabled(true);
            setButtonsVisible(true);
        });
    }

    private void showSettings(){
        setButtonsEnabled(false);
        settingsView.setVolumeValue((int) (SoundManager.getVolume() * 100)); // Set the slider to the current volume
        setButtonsVisible(false);
        settingsView.setVisible(true);
        setComponentZOrder(settingsView, 0);
    }

    public void setHelpButton(){
        helpButton = new JButton(helpButtonImage);
        helpButton.setBounds(465,400,helpButtonImage.getIconWidth(),helpButtonImage.getIconHeight());
        add(helpButton);
        helpButton.addActionListener(e -> {
            if(helpView!=null){showHelp();}
        });
    }
    public void setHelpView() throws IOException {
        helpView = new HelpView(this);
        helpView.setBounds(100,0,600,600);
        helpView.setVisible(false);
        add(helpView);
    }

    private void showHelp(){
        setButtonsEnabled(false);
        setButtonsVisible(false);
        helpView.setVisible(true);
        setComponentZOrder(helpView,0);
    }


    public void setExitButton(){
        exitButton = new JButton(exitButtonImage);
        exitButton.setBounds(465,500,exitButtonImage.getIconWidth(),exitButtonImage.getIconHeight());
        add(exitButton);
        exitButton.addActionListener(e -> {
            listener.onExit();
        });
    }
    public void setButtonsEnabled(boolean enabled) {
        startButton.setEnabled(enabled);
        settingButton.setEnabled(enabled);
        helpButton.setEnabled(enabled);
        exitButton.setEnabled(enabled);
    }

    public void setButtonsVisible(boolean bool)
    {
        startButton.setVisible(bool);
        settingButton.setVisible(bool);
        helpButton.setVisible(bool);
        exitButton.setVisible(bool);
    }
    public void setMainMenuPanelListener(MainMenuPanelListener listener){
        this.listener = listener;
    }
}
