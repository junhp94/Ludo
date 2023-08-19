package model;

import java.awt.*;

public class Player {
    private Color color;
    private PlayerPiece[] gamePieces;
    private Integer score;
    private Board board;

    public Player(Color color, Board board){
        this.color = color;
        gamePieces = new PlayerPiece[4];
        score = 0;
        this.board = board;
        createGamePieces();
    }

    public void createGamePieces(){
        PieceFactory pieceFactory = new PieceFactory();
        for(int i = 0;i < 4; i++){
            if (this.color == Color.BLUE) {
                gamePieces[i] = (PlayerPiece) pieceFactory.createPiece("blue", board.board.getStartingNodes()[0][i],
                        board.board.getBlueEntry());
            } else if (this.color == Color.RED) {
                gamePieces[i] = (PlayerPiece) pieceFactory.createPiece("red", board.board.getStartingNodes()[1][i],
                        board.board.getRedEntry());
            } else if (this.color == Color.GREEN) {
                gamePieces[i] = (PlayerPiece) pieceFactory.createPiece("green", board.board.getStartingNodes()[2][i],
                        board.board.getGreenEntry());
            } else if (this.color == Color.YELLOW) {
                gamePieces[i] = (PlayerPiece) pieceFactory.createPiece("yellow", board.board.getStartingNodes()[3][i],
                        board.board.getYellowEntry());
            }
        }
    }

    public Integer getScore() {
        return score;
    }

    public void addScore() {
        score++;
    }

    public PlayerPiece[] getGamePieces() {
        return gamePieces;
    }

}
