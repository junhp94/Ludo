package controller;

import model.Player;
import model.PlayerPiece;
import view.GamePanel;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PieceClickListener extends MouseAdapter {
    private GamePanel gamePanel;
    public PieceClickListener(GamePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    public void mouseClicked(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        Player player = gamePanel.getPlayers()[gamePanel.getCurrentPlayerIndex()];
        for(PlayerPiece gamePiece : player.getGamePieces()){
            if(x >= gamePiece.getPosition().getxPos() &&
                    x < gamePiece.getPosition().getxPos() + gamePanel.getPieceImage(gamePiece.getColor(),1).getWidth(null) &&
                    y >= gamePiece.getPosition().getyPos() &&
                    y < gamePiece.getPosition().getyPos() + gamePanel.getPieceImage(gamePiece.getColor(),1).getHeight(null) &&
                    !gamePanel.getDiceFlag() && gamePanel.getMoveFlag()) {
                // Move the game piece
                gamePiece.move(gamePanel.getRolledNumber());
                gamePanel.setDiceFlag(true);
                gamePanel.setMoveFlag(false);

                if(gamePanel.checkPieceOverlap(1) != null)
                {
                    gamePanel.movePieceToBase(gamePanel.checkPieceOverlap(1));
                }

                if(gamePanel.getRolledNumber() != 6){
                    gamePanel.setCurrentPlayerIndex((gamePanel.getCurrentPlayerIndex() + 1)%4);
                    gamePanel.setTurnStr(String.format("Player %d turn to roll", gamePanel.getCurrentPlayerIndex() + 1));
                }

                gamePanel.repaint();
                break;
            }
        }
    }
}
