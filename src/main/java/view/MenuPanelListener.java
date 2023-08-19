package view;

import java.io.IOException;

public interface MenuPanelListener {
    void onCloseMenu();
    void backToMain() throws IOException;
    void onResetGame();
    void saveFile() throws IOException;
    void restoreFile() throws IOException;
}
