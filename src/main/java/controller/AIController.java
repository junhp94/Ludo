package controller;

import utilities.SoundManager;
import view.GamePanel;
import view.MainMenuPanel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AIController
{
    private int AITurnCounter, rolledNumber;
    private boolean isPlayerBlueAI, isPlayerRedAI, isPlayerYellowAI, isPlayerGreenAI;
    public JLabel AIstrLabel, dummyLabel;
    private Random random;

    private GamePanel gamepanel;
    private MainMenuPanel mainMenuPanel;

    public AIController(GamePanel gamepanel)
    {
        this.gamepanel = gamepanel;
        random = new Random();
        AITurnCounter = 0;
        setAIPlayer();

        AIstrLabel = new JLabel(" ");
        gamepanel.add(AIstrLabel);
        AIstrLabel.setVisible(false);
        AIstrLabel.setBounds(630, 110, 150, 100);
    }

    public void setAIPlayer()
    {
        isPlayerBlueAI = false;
        isPlayerRedAI = true;
        isPlayerGreenAI = true;
        isPlayerYellowAI = true;
    }

    public void createDummyLabel()
    {
        dummyLabel = new JLabel();
        gamepanel.add(dummyLabel);
        dummyLabel.setBounds(620, 95, 170, 60);
        dummyLabel.setBackground(new Color(255,178,102));
        dummyLabel.setOpaque(true);
    }

    public void createAIstrLabel()
    {
        if (AIstrLabel.isVisible())
        {
//            System.out.println("remove dummy top");
            gamepanel.remove(dummyLabel);
            AIstrLabel.setVisible(false);
//            remove(AIstrLabel);
        }

        if (gamepanel.getCurrentPlayerIndex() == 0 && isPlayerBlueAI)
        {
            createDummyLabel();
            AIstrLabel.setText("AI Player Blue playing ...");
            AIstrLabel.setVisible(true);
//            System.out.println("Blue Label");
        }
        else if (gamepanel.getCurrentPlayerIndex() == 1 && isPlayerRedAI)
        {
            createDummyLabel();
            AIstrLabel.setText("AI Player Red playing ...");
            AIstrLabel.setVisible(true);
//            System.out.println("Red Label");
        }
        else if (gamepanel.getCurrentPlayerIndex() == 2 && isPlayerGreenAI)
        {
            createDummyLabel();
            AIstrLabel.setText("AI Player Green playing ...");
            AIstrLabel.setVisible(true);
//            System.out.println("Green Label");
        }
        else if (gamepanel.getCurrentPlayerIndex() == 3 && isPlayerYellowAI)
        {
            createDummyLabel();
            AIstrLabel.setText("AI Player Yellow playing ...");
            AIstrLabel.setVisible(true);
//            System.out.println("Yellow Label");
        }
        else
        {
//            System.out.println("remove dummy bottom");
            AIstrLabel.setVisible(false);
//            remove(AIstrLabel);
            gamepanel.remove(dummyLabel);
        }
    }

    public boolean isAllPiecesAtBase()
    {
        for(int piece = 0; piece <= 3; piece++)
        {
            if(!gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[piece].isJail())
            {
                return false;
            }
        }
        return true;
    }

    public void playGameAI()
    {
//        String soundEffectPath = "Music/dice.wav";
        String localTurnStr = " ";

        if(gamepanel.getDiceFlag() && !gamepanel.getMenuFlag())
        {
//            SoundManager.playSoundEffect(soundEffectPath, 0.7f);
            rolledNumber = random.nextInt(6)+1;
//            System.out.println(rolledNumber);
            gamepanel.setRolledNumber(rolledNumber);
            localTurnStr = String.format("Player %d turn to move",gamepanel.getCurrentPlayerIndex() + 1);
            gamepanel.setTurnStr(localTurnStr);
//            gamepanel.setMoveStr("Click piece to move");
            gamepanel.setMoveFlag(true);
            gamepanel.setDiceFlag(false);
        }

        if(gamepanel.getMoveFlag() && !gamepanel.getMenuFlag())
        {
            boolean flag1 = false, flag2 = false, flag3 = false;

            outer:
            if(!isAllPiecesAtBase())
            {
                int currentPlayerPiecePos;

                for (int currentPiece = 0; currentPiece <=3; currentPiece++)
                {
                    currentPlayerPiecePos = gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[currentPiece].getPosition().getNodeID();

                    for(int player = 0; player <=3; player++)
                    {
                        for(int piece = 0; piece <= 3; piece++)
                        {
                            int pos = gamepanel.getPlayer(player).getGamePieces()[piece].getPosition().getNodeID();
//                            System.out.println("NodeID[" + player + "][" + piece + "]: " + pos);
                            if(pos - currentPlayerPiecePos == rolledNumber && gamepanel.getCurrentPlayerIndex() != player && pos != 0 && currentPlayerPiecePos != 0)
                            {
                                gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[currentPiece].move(rolledNumber);
                                flag1 = true;
                                if(gamepanel.checkPieceOverlap(currentPiece) != null)
                                {
                                    gamepanel.movePieceToBase(gamepanel.checkPieceOverlap(currentPiece));
                                }

                                break outer;
                            }

                        }
                    }
                }
            }

            if(rolledNumber == 6 && !flag1)
            {
                for(int piece = 0; piece <= 3; piece++)
                {
                    if(gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[piece].isJail())
                    {
                        gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[piece].move(rolledNumber);
                        flag2 = true;
//                        System.out.println("Rolled 6");

                        if(gamepanel.checkPieceOverlap(piece) != null)
                        {
                            gamepanel.movePieceToBase(gamepanel.checkPieceOverlap(piece));
                        }

                        break;
                    }
                }
            }

            if(!flag1 && !flag2 && !isAllPiecesAtBase())
            {
                int randomPiece = 0;
                boolean pieceInBase = false;

                do
                {
                    randomPiece = random.nextInt(4);

                    if(gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[randomPiece].isJail())
                    {
                        pieceInBase = true;
                    }
                    else
                    {
                        pieceInBase = false;
                        gamepanel.getPlayer(gamepanel.getCurrentPlayerIndex()).getGamePieces()[randomPiece].move(rolledNumber);
                        if(gamepanel.checkPieceOverlap(randomPiece) != null)
                        {
                            gamepanel.movePieceToBase(gamepanel.checkPieceOverlap(randomPiece));
                        }

                    }

                }while(pieceInBase);
            }

            gamepanel.setDiceFlag(true);
            gamepanel.setMoveFlag(false);

            if (rolledNumber != 6) {
                gamepanel.setCurrentPlayerIndex((gamepanel.getCurrentPlayerIndex()+1)%4);
                localTurnStr = String.format("Player %d turn to roll",gamepanel.getCurrentPlayerIndex() + 1);
                gamepanel.setTurnStr(localTurnStr);
            }
        }

        AITurnCounter++;
        createAIstrLabel();
    }

    public boolean getIsPlayerBlueAI()
    {
        return isPlayerBlueAI;
    }

    public boolean getIsPlayerRedAI()
    {
        return isPlayerRedAI;
    }

    public boolean getIsPlayerGreenAI()
    {
        return isPlayerGreenAI;
    }

    public boolean getIsPlayerYellowAI()
    {
        return isPlayerYellowAI;
    }

}

