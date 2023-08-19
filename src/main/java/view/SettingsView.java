package view;

import com.sun.tools.javac.Main;
import utilities.SoundManager;

import javax.imageio.ImageIO;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class SettingsView extends JPanel {
    private JSlider volumeSlider;
    private JButton backButton;
    private JToggleButton muteButton;
    private MainMenuPanel mainMenuPanel;
    private ImageIcon backIcon, onVolumeIcon, offVolumeIcon;
    private JLabel volumeLabel;

    public SettingsView(MainMenuPanel mainMenuPanel) throws IOException {
        initComponents();
        setBackground(Color.WHITE);
        this.mainMenuPanel = mainMenuPanel;
    }

    private void initComponents() throws IOException {
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Image backImage = ImageIO.read(new File("Images/arrowLeft.png")).getScaledInstance(30, 20, Image.SCALE_SMOOTH);
        backIcon = new ImageIcon(backImage);

        backButton = new JButton(backIcon);
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.setOpaque(false);
        backButton.setFocusPainted(false);
        topPanel.add(backButton); // Add backButton to the top-left corner of topPanel

        add(topPanel, BorderLayout.NORTH); // Add topPanel to the top of the panel

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        volumeLabel = new JLabel("Volume"); // Add "Volume" label
        centerPanel.add(volumeLabel); // Add volumeLabel to topPanel

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50); // Set initial volume to 50
        volumeSlider.setMajorTickSpacing(25);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);
        centerPanel.add(volumeSlider);

        // Load image for mute button
        Image onVolumeImage = ImageIO.read(new File("Images/onVolume.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        onVolumeIcon = new ImageIcon(onVolumeImage);
        Image offVolumeImage = ImageIO.read(new File("Images/offVolume.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        offVolumeIcon = new ImageIcon(offVolumeImage);

        muteButton = new JToggleButton();
        muteButton.setIcon(onVolumeIcon);
        muteButton.setFocusPainted(false);
        centerPanel.add(muteButton);
        add(centerPanel, BorderLayout.CENTER);

        topPanel.setBackground(Color.white);
        centerPanel.setBackground(Color.white);

        muteButton.addActionListener(e -> {
            if (muteButton.isSelected()) {
                // If the mute button is selected, mute the sound
                SoundManager.setVolume(0.0f);
                muteButton.setIcon(offVolumeIcon);
            } else {
                // If the mute button is deselected, unmute the sound and set the volume to the slider value
                int volume = volumeSlider.getValue();
                float normalizedVolume = volume / 100.0f;
                SoundManager.setVolume(normalizedVolume);
                muteButton.setIcon(onVolumeIcon);
            }
        });
    }

    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }

    public void setVolumeSliderListener(ChangeListener listener) {
        volumeSlider.addChangeListener(listener);
    }

    public int getVolumeValue() {
        return volumeSlider.getValue();
    }

    public void setVolumeValue(int volume) {
        volumeSlider.setValue(volume);
    }
}
