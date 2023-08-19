package view;

import java.io.IOException;

public interface MainMenuPanelListener {
    void onStartGame() throws IOException;
    void onSetting();
    void onHelp();
    void onExit();
}
