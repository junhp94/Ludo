package view;

import controller.AIController;
import controller.PieceClickListener;
import model.Board;
import model.Player;
import model.PlayerPiece;
import utilities.Node;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class GamePanel extends JPanel implements MenuPanelListener{
    private Random random;
    private int rolledNumber,currentPlayerIndex,AITurnCounter;
    private String turnStr, moveStr, rollStr1, rollStr2, diceImgPath, imagePath;
    private Image backgroundImage, rollDiceImage, dice1, dice2, dice3, dice4, dice5, dice6, blueImg1, blueImg2, blueImg3, blueImg4, redImg1, redImg2, redImg3, redImg4, greenImg1, greenImg2, greenImg3, greenImg4, yellowImg1, yellowImg2, yellowImg3, yellowImg4;
    private ImageIcon settingIcon, rollDiceIcon, oneIcon, twoIcon, threeIcon, fourIcon;
    private boolean diceFlag, moveFlag, menuFlag, playGameFlag, previousRollSixFlag;
    private Player[] players;
    private JButton diceButton,menuButton, piece1, piece2, piece3, piece4;
//    private JLabel AIstrLabel, dummyLabel;
    private Board board;
    private MenuPanel menuPanel;
    private MainMenuPanel mainMenuPanel;
    private AIController aiController;

    public GamePanel(MainMenuPanel mainMenuPanel) throws IOException{
        this.mainMenuPanel = mainMenuPanel;
        loadImages();
        init();
    }
    public void init(){
        this.setBackground(new Color(255,178,102));
        random = new Random();
        rolledNumber = 1;
        currentPlayerIndex = 0;
        AITurnCounter = 0;
        board = new Board();
        players = createPlayers();
        turnStr = String.format("Player %d turn to roll",currentPlayerIndex + 1);
        moveStr = "";
        rollStr1 = "";
        rollStr2 = "";
        diceFlag = true;
        moveFlag = false;
        menuFlag = false;
        playGameFlag = false;
        previousRollSixFlag = false;

        // set buttons
        createDiceButton(diceImgPath);
        createPieceButton();
        createMenuButton();

        aiController = new AIController(this);
    }
    private JButton createButton(Icon icon, int x, int y){
        JButton button = new JButton(icon);
        button.setBounds(x, y, icon.getIconWidth(), icon.getIconHeight());
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }
    public void createDiceButton(String img) {
        diceButton = createButton(rollDiceIcon, 617, 200);
        add(diceButton);
    }

    public void createMenuButton(){
        menuButton = createButton(settingIcon, 750, 20);
        menuButton.addActionListener(e -> {
            if(menuPanel!=null){
                showMenu();
            }
        });
        add(menuButton);

        menuPanel = new MenuPanel();
        menuPanel.setBounds(200,100,200,400);
        menuPanel.setVisible(false);
        add(menuPanel);
        menuPanel.setMenuPanelListener(this);
    }
    public void createPieceButton(){
        // Physically clicking pieces
        PieceClickListener pieceClickListener = new PieceClickListener(this);
        addMouseListener(pieceClickListener);

        // buttons to control each pieces
        piece1 = createButton(oneIcon, 630, 480);
        piece2 = createButton(twoIcon, 710, 480);
        piece3 = createButton(threeIcon, 630, 550);
        piece4 = createButton(fourIcon, 710, 550);

        add(piece1);
        add(piece2);
        add(piece3);
        add(piece4);
    }
    public void loadImages() throws IOException {
        imagePath = "Images/board.png";
        diceImgPath = "Images/dice.png";
        backgroundImage = ImageIO.read(new File(imagePath)).getScaledInstance(600,600,Image.SCALE_SMOOTH);
        blueImg1 = ImageIO.read(new File("Images/blue_one.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        blueImg2 = ImageIO.read(new File("Images/blue_two.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        blueImg3 = ImageIO.read(new File("Images/blue_three.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        blueImg4 = ImageIO.read(new File("Images/blue_four.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        redImg1 = ImageIO.read(new File("Images/red_one.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        redImg2 = ImageIO.read(new File("Images/red_two.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        redImg3 = ImageIO.read(new File("Images/red_three.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        redImg4 = ImageIO.read(new File("Images/red_four.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        greenImg1 = ImageIO.read(new File("Images/green_one.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        greenImg2 = ImageIO.read(new File("Images/green_two.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        greenImg3 = ImageIO.read(new File("Images/green_three.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        greenImg4 = ImageIO.read(new File("Images/green_four.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        yellowImg1 = ImageIO.read(new File("Images/yellow_one.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        yellowImg2 = ImageIO.read(new File("Images/yellow_two.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        yellowImg3 = ImageIO.read(new File("Images/yellow_three.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        yellowImg4 = ImageIO.read(new File("Images/yellow_four.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);

        rollDiceImage = ImageIO.read(new File("Images/roll_button.png")).getScaledInstance(150,50, Image.SCALE_SMOOTH);
        rollDiceIcon = new ImageIcon(rollDiceImage);

        dice1 = ImageIO.read(new File("Images/dice_one.png")).getScaledInstance(100,100,Image.SCALE_SMOOTH);
        dice2 = ImageIO.read(new File("Images/dice_two.png")).getScaledInstance(100,100,Image.SCALE_SMOOTH);
        dice3 = ImageIO.read(new File("Images/dice_three.png")).getScaledInstance(100,100,Image.SCALE_SMOOTH);
        dice4 = ImageIO.read(new File("Images/dice_four.png")).getScaledInstance(100,100,Image.SCALE_SMOOTH);
        dice5 = ImageIO.read(new File("Images/dice_five.png")).getScaledInstance(100,100,Image.SCALE_SMOOTH);
        dice6 = ImageIO.read(new File("Images/dice_six.png")).getScaledInstance(100,100,Image.SCALE_SMOOTH);
        Image settingImage = ImageIO.read(new File("Images/settingsButton.png")).getScaledInstance(30,30,Image.SCALE_SMOOTH);
        settingIcon = new ImageIcon(settingImage);
        Image numOneImage = ImageIO.read(new File("Images/one.png")).getScaledInstance(50,50,Image.SCALE_SMOOTH);
        oneIcon = new ImageIcon(numOneImage);
        Image numTwoImage = ImageIO.read(new File("Images/two.png")).getScaledInstance(50,50,Image.SCALE_SMOOTH);
        twoIcon = new ImageIcon(numTwoImage);
        Image numThreeImage = ImageIO.read(new File("Images/three.png")).getScaledInstance(50,50,Image.SCALE_SMOOTH);
        threeIcon = new ImageIcon(numThreeImage);
        Image numFourImage = ImageIO.read(new File("Images/four.png")).getScaledInstance(50,50,Image.SCALE_SMOOTH);
        fourIcon = new ImageIcon(numFourImage);
    }

    public Image getDiceImage(int num){
        if(num == 1){
            return dice1;
        } else if(num == 2){
            return dice2;
        }else if(num == 3){
            return dice3;
        }else if(num == 4){
            return dice4;
        }else if(num == 5){
            return dice5;
        }else if(num == 6){
            return dice6;
        }
        return null;
    }

    public Image getPieceImage(Color color, int pieceID) {
        Image[][] pieceImages = {
                {redImg1, redImg2, redImg3, redImg4},
                {blueImg1, blueImg2, blueImg3, blueImg4},
                {greenImg1, greenImg2, greenImg3, greenImg4},
                {yellowImg1, yellowImg2, yellowImg3, yellowImg4}
        };

        int colorIndex = -1;
        if (color == Color.RED) {
            colorIndex = 0;
        } else if (color == Color.BLUE) {
            colorIndex = 1;
        } else if (color == Color.GREEN) {
            colorIndex = 2;
        } else if (color == Color.YELLOW) {
            colorIndex = 3;
        }

        if (colorIndex >= 0 && colorIndex < pieceImages.length && pieceID >= 1 && pieceID <= 4) {
            return pieceImages[colorIndex][pieceID - 1];
        }

        return null;
    }

    public Player[] createPlayers(){
        Player[] players = new Player[4];
        players[0] = new Player(Color.BLUE, board);
        players[1] = new Player(Color.RED, board);
        players[2] = new Player(Color.GREEN, board);
        players[3] = new Player(Color.YELLOW, board);
        return players;
    }

    public PlayerPiece checkPieceOverlap(int pieceNumber)
    {
        for (int i = 1; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
            {
                if (players[currentPlayerIndex].getGamePieces()[pieceNumber].getPosition().equals(players[(currentPlayerIndex + i) % 4].getGamePieces()[j].getPosition()))
                {
                    return players[(currentPlayerIndex + i) % 4].getGamePieces()[j];
                }
            }
        }
        return null;
    }

    public void movePieceToBase(PlayerPiece piece)
    {
        piece.getPosition().occupants.remove(piece);
        piece.setPosition(piece.getStartingNode());
    }

    public boolean isAllPiecesAtBase()
    {
        for(int piece = 0; piece <= 3; piece++)
        {
            if(!players[currentPlayerIndex].getGamePieces()[piece].isJail())
            {
                return false;
            }
        }
        return true;
    }

    public void setBlock() {
        Node curr;
        curr = board.board.getBlue();
        for (int i = 0; i < 52; i++) {
            if (curr.occupants.size() > 1 && !curr.isBlocked()) {
                curr.setBlocked();
            } else if (curr.isBlocked() && curr.occupants.size() <= 1){
                curr.setBlocked();
            }
            curr = curr.getNext();
        }
    }

    public void playGame()
    {
        diceButton.addActionListener(e -> {
            if(diceFlag && !menuFlag){
                rolledNumber = random.nextInt(6)+1;
                turnStr = String.format("Player %d turn to move",currentPlayerIndex + 1);
                rollStr1 = "";
                rollStr2 = "";
                moveStr = "Click on the piece/number";
                moveFlag = true;
                diceFlag = false;
            }
        });

        skipPieceButton:
        if(moveFlag)
        {
            if(rolledNumber != 6 && isAllPiecesAtBase())
            {
                currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                turnStr = String.format("Player %d turn to roll", currentPlayerIndex + 1);
                break skipPieceButton;
            }


            piece1.addActionListener(e -> {
                if (moveFlag && !menuFlag) {
                    players[currentPlayerIndex].getGamePieces()[0].move(rolledNumber);
                    diceFlag = true;
                    moveFlag = false;

                    if (checkPieceOverlap(0) != null) {
                        movePieceToBase(checkPieceOverlap(0));
                    }

                    previousRollSixFlag = true;
                    if (rolledNumber != 6) {
                        previousRollSixFlag = false;
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                        turnStr = String.format("Player %d turn to roll", currentPlayerIndex + 1);
                    }
                }
            });
            piece2.addActionListener(e -> {
                if (moveFlag && !menuFlag) {
                    players[currentPlayerIndex].getGamePieces()[1].move(rolledNumber);
                    diceFlag = true;
                    moveFlag = false;

                    if (checkPieceOverlap(1) != null) {
                        movePieceToBase(checkPieceOverlap(1));
                    }

                    previousRollSixFlag = true;
                    if (rolledNumber != 6) {
                        previousRollSixFlag = false;
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                        turnStr = String.format("Player %d turn to roll", currentPlayerIndex + 1);
                    }
                }
            });
            piece3.addActionListener(e -> {
                if (moveFlag && !menuFlag) {
                    players[currentPlayerIndex].getGamePieces()[2].move(rolledNumber);
                    diceFlag = true;
                    moveFlag = false;

                    if (checkPieceOverlap(2) != null) {
                        movePieceToBase(checkPieceOverlap(2));
                    }

                    previousRollSixFlag = true;
                    if (rolledNumber != 6) {
                        previousRollSixFlag = false;
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                        turnStr = String.format("Player %d turn to roll", currentPlayerIndex + 1);
                    }
                }
            });
            piece4.addActionListener(e -> {
                if (moveFlag && !menuFlag) {
                    players[currentPlayerIndex].getGamePieces()[3].move(rolledNumber);
                    diceFlag = true;
                    moveFlag = false;

                    if (checkPieceOverlap(3) != null) {
                        movePieceToBase(checkPieceOverlap(3));
                    }

                    previousRollSixFlag = true;
                    if (rolledNumber != 6) {
                        previousRollSixFlag = false;
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                        turnStr = String.format("Player %d turn to roll", currentPlayerIndex + 1);
                    }
                }
            });
        }

        if(previousRollSixFlag == true)
        {
            moveStr = "";
            rollStr1 = "Great! You rolled a 6.";
            rollStr2 = "ROLL AGAIN!!";
            previousRollSixFlag = false;
        }

        if((currentPlayerIndex == 0 && aiController.getIsPlayerBlueAI()) || (currentPlayerIndex == 1 && aiController.getIsPlayerRedAI()) || (currentPlayerIndex == 2 && aiController.getIsPlayerGreenAI()) || (currentPlayerIndex == 3 && aiController.getIsPlayerYellowAI()))
        {
            playGameFlag = true;
            aiController.createAIstrLabel();
            moveStr = "";
            turnStr = String.format("AI Player playing...");
        }
    }

    public int getRolledNumber(){return rolledNumber;}
    public void setRolledNumber(int num){rolledNumber = num;}
    public Player[] getPlayers(){return players;}
    public Player getPlayer(int index){return players[index];}
    public int getCurrentPlayerIndex(){return currentPlayerIndex;}
    public void setCurrentPlayerIndex(int i){this.currentPlayerIndex = i;}
    public String getTurnStr(){return turnStr;}
    public void setTurnStr(String s){this.turnStr = s;}
    public boolean getDiceFlag(){return diceFlag;}
    public void setDiceFlag(boolean flag){this.diceFlag = flag;}
    public boolean getMenuFlag(){return menuFlag;}
    public boolean getMoveFlag(){return moveFlag;}
    public void setMoveStr(String s){this.moveStr = s;}
    public void setMoveFlag(boolean flag){this.moveFlag = flag;}

    private void showMenu(){
        if(menuPanel != null){
            menuPanel.setVisible(true);
            menuFlag = true;
            revalidate();
            repaint();
        }
    }
    public void onCloseMenu(){
        menuPanel.setVisible(false);
        menuFlag = false;
    }

    public void backToMain(){
        Container parentContainer = this.getParent();
        if (parentContainer instanceof JPanel) {
            JPanel mainPanel = (JPanel) parentContainer;
            this.setVisible(false);
            mainMenuPanel.setVisible(true);
            onResetGame();
        }
    }

    public void onResetGame(){
        rolledNumber = 1;
        currentPlayerIndex = 0;
        AITurnCounter = 0;
        board = new Board();
        players = createPlayers();
        turnStr = String.format("Player %d turn to roll",currentPlayerIndex + 1);
        moveStr = "";
        rollStr1 = "";
        rollStr2 = "";
        diceFlag = true;
        moveFlag = false;
        menuFlag = false;
        playGameFlag = false;
//        AIstrLabel.setVisible(false);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,this);
        g.drawImage(getDiceImage(this.rolledNumber),642,300,this);

        g.drawString(turnStr,640,150);
        g.drawString(moveStr, 620, 465);
        g.drawString(rollStr1, 640, 445);
        g.drawString(rollStr2, 655, 465);

        for(Player player:players){
            int pieceID = 1;
            for(PlayerPiece gamePiece: player.getGamePieces()){
                g.drawImage(getPieceImage(gamePiece.getColor(),pieceID),gamePiece.getPosition().getxPos(), gamePiece.getPosition().getyPos(), this);
                pieceID++;
            }
        }
    }

    public void checkWin() {
        for (int i = 0; i < 4; i++) {
            if (!players[currentPlayerIndex].getGamePieces()[i].getPosition().isHome()) {
                break;
            } if (i == 3) {
                moveStr = "Game ended!";
                aiController.AIstrLabel.setText("Game ended!");
                moveFlag = false;
                diceFlag = false;
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);
    }

    public void activeLoop(){
        repaint();

        if (aiController.getIsPlayerRedAI() && playGameFlag && currentPlayerIndex == 1)
        {
            aiController.playGameAI();
            try {
                // Pause for 1 second (1500 milliseconds)
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (aiController.getIsPlayerGreenAI() && playGameFlag && currentPlayerIndex == 2) {
            aiController.playGameAI();

            try {
                // Pause for 1 second (1500 milliseconds)
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (aiController.getIsPlayerYellowAI() && playGameFlag && currentPlayerIndex == 3) {
            aiController.playGameAI();

            try {
                // Pause for 1 second (1500 milliseconds)
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            playGameFlag = false;
            playGame();

        }
//        System.out.println(rolledNumber);
        repaint();
        setBlock();
        checkWin();
    }

    //everything below this is for saving

    public void saveFile() throws IOException {
        FileWriter fw = new FileWriter("data.txt");
        BufferedWriter bw = new BufferedWriter(fw);
        Memento memento = makeSnapshot();
        bw.write(String.valueOf(memento.memRolledNumber));
        bw.newLine();
        bw.write(String.valueOf(memento.memCurrentPlayerIndex));
        bw.newLine();
         bw.write(String.valueOf(memento.memAITurnCounter));
        bw.newLine();
        bw.write(String.valueOf(memento.memTurnStr));
        bw.newLine();
        bw.write(String.valueOf(memento.memMoveStr));
        bw.newLine();
        bw.write(String.valueOf(memento.memDiceFlag));
        bw.newLine();
        bw.write(String.valueOf(memento.memMoveFlag));
        bw.newLine();
        bw.write(String.valueOf(memento.memPlayGameFlag));
        bw.newLine();

        for (int i = 0; i < memento.memPlayers.length; i++) {
            Player curr = memento.memPlayers[i];
            bw.write(String.valueOf(curr.getScore()));
            bw.newLine();
            PlayerPiece[] gamePieces = curr.getGamePieces();
            for (int j = 0; j < gamePieces.length; j++) {
                bw.write(String.valueOf(gamePieces[j].isJail()) + " " + 
                // bw.write(
                String.valueOf(gamePieces[j].getPosition().getxPos()) + " " + 
                String.valueOf(gamePieces[j].getPosition().getyPos()) + " ");
            }
            bw.newLine();
        }

        bw.close();
    }

    public void setScore(Player tempPlayer, int setScore) {
        int j = 0;
        while (j < setScore) {
            tempPlayer.addScore();
            j++;
        }
    }

    public boolean checkIsJail(String str) {
        return Boolean.valueOf(str);
    }

    public boolean isInPosition(Node curr, String pieceX, String pieceY) {
        return ((curr.getxPos().equals(Integer.valueOf(pieceX))) && (curr.getyPos().equals(Integer.valueOf(pieceY))));
    }

    public void restoreFile() throws IOException {
        FileReader fr = new FileReader("data.txt");
        BufferedReader br = new BufferedReader(fr);

        int tempRolledNumber = Integer.valueOf(br.readLine());
        int tempCurrentPlayerIndex = Integer.valueOf(br.readLine());
        int tempAITurnCounter = Integer.valueOf(br.readLine());
        String tempTurnStr = br.readLine();
        String tempMoveStr = br.readLine();
        boolean tempDiceFlag = Boolean.valueOf(br.readLine());
        boolean tempMoveFlag = Boolean.valueOf(br.readLine());
        boolean tempPlayGameFlag = Boolean.valueOf(br.readLine());
        
        Player[] tempPlayers = createPlayers();
        for (int i = 0; i < 4; i++) { //iterate through players and properly set its pieces
            int playerScore = Integer.valueOf(br.readLine());
            setScore(tempPlayers[i], playerScore);

            PlayerPiece[] gamePieces = tempPlayers[i].getGamePieces();
            String[] piecePositions = br.readLine().split("\\s+");
            for (int j = 0; j < 4; j++) { //iterate through each player's pieces and properly set its position
                String jailCheck = piecePositions[j*3];
                String pieceX = piecePositions[j*3 + 1];
                String pieceY = piecePositions[j*3 + 2];
                
                if (!checkIsJail(jailCheck)) {
                    Node curr = gamePieces[j].getPosition();
                    while (!isInPosition(curr, pieceX, pieceY)) {
                        curr = curr.getNext();
                    }
                    gamePieces[j].setPosition(curr); //set position of the game piece to the proper node
                }
            }
        }

        Restore(new Memento(tempPlayers, tempRolledNumber, tempCurrentPlayerIndex, tempAITurnCounter, tempTurnStr, tempMoveStr, tempDiceFlag, tempMoveFlag, tempPlayGameFlag));

        br.close();
    }

    Memento makeSnapshot() {
        return new Memento(players, rolledNumber, currentPlayerIndex, AITurnCounter, turnStr, moveStr, diceFlag, moveFlag, playGameFlag);
    }

    void Restore(Memento boardMemento) {
        players = boardMemento.memPlayers; //has to set score, color, board, and gamepicees of each
        rolledNumber = boardMemento.memRolledNumber;
        currentPlayerIndex = boardMemento.memCurrentPlayerIndex;
        AITurnCounter = boardMemento.memAITurnCounter;
        turnStr = boardMemento.memTurnStr;
        moveStr = boardMemento.memMoveStr;
        diceFlag = boardMemento.memDiceFlag; //true by default
        moveFlag = boardMemento.memMoveFlag; //false by default, mennuflag false by default
        playGameFlag = boardMemento.memPlayGameFlag;
    }

    private class Memento {
        private Player[] memPlayers; //players have getters so can record their info from getters into a file
        private int memRolledNumber,memCurrentPlayerIndex,memAITurnCounter; //all these and below can be recorded easily in a file
        private String memTurnStr, memMoveStr;
        private boolean memDiceFlag,memMoveFlag,memMenuFlag,memPlayGameFlag;
        private boolean memIsPlayerBlueAI, memIsPlayerRedAI, memIsPlayerYellowAI, memIsPlayerGreenAI;
        // private Board memBoard;

        public Memento(Player[] cPlayers, int cRolledNumber, int cCurrentPlayerIndex, int cAITurnCounter, String cTurnStr, String cMoveStr, boolean cDiceFlag, boolean cMoveFlag, boolean cPlayGameFlag) {
            memPlayers = cPlayers;
            memRolledNumber = cRolledNumber;
            memCurrentPlayerIndex = cCurrentPlayerIndex;
            memAITurnCounter = cAITurnCounter;
            memTurnStr = cTurnStr;
            memMoveStr = cMoveStr;
            memDiceFlag = cDiceFlag;
            memMoveFlag = cMoveFlag;
            memPlayGameFlag = cPlayGameFlag;
        }
    }
}
