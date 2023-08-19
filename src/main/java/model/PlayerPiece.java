package model;

import utilities.*;

import java.awt.*;

public class PlayerPiece implements Piece{

    private Node position;
    private Node startingNode;
    private Node entryNode;
    private Color color;
    private boolean winning;

    public PlayerPiece(Color color, Node startingNode, Node entryNode) {
        position = startingNode;
        this.startingNode = startingNode;
        this.entryNode = entryNode;
        this.color = color;
        winning = false;
    }

    public Node getPosition() {
        return position;
    }
    public void setPosition(Node p) {
        position = p;
    }

    public Node getStartingNode() {
        return startingNode;
    }
    public void setStartingNode(Node n) {
        startingNode = n;
    }

    public void setPosX(int x){this.position.setxPos(x);}
    public void setPosY(int y){this.position.setyPos(y);}

    public boolean getWinning() {
        return winning;
    }
    public void setWinning() {
        winning = true;
    }

    public Color getColor() {
        return color;
    }

    public Boolean isJail() {
        if (position == startingNode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void move(Integer n) {
        String soundEffectPath = "Music/piece.wav";
        int soundDelay = 10; // milliseconds

        if (this.getWinning()) {
            return;
        }

        this.getPosition().occupants.remove(this);

        for (int i = 0; i < n; i++) {
            if (this.isJail() && n != 6) {
                break;
            } else if (this.isJail() && n == 6) {
                this.setPosition(this.getPosition().getNext());
                break;
            } else if (this.getPosition().isEntry() &&
                    this.getPosition().getEntryNode().equals(this.entryNode)) {
                this.setPosition(this.getPosition().getEntryNode());
            } else if (this.getPosition().getNext() == null) {
                while (i < n) {
                    if (this.getPosition().getPrevious() != null) {
                        this.setPosition(this.getPosition().getPrevious());
                    }
                    i++;
                }
                break;
            } else if (this.getPosition().getNext().isBlocked() &&
                    this.getPosition().getNext().occupants.get(0).getColor() != this.getColor()) {
                break;
            } else if (!this.getWinning()){
                this.setPosition(this.getPosition().getNext());
            }
        }

        playSoundWithDelay(soundEffectPath, soundDelay);
        this.getPosition().occupants.add(this);

        if (this.getPosition().getNext() == null) {
            this.setWinning();
        }
    }
    private void playSoundWithDelay(String soundEffectPath, int delay) {
        try {
            Thread.sleep(delay);
            SoundManager.playSoundEffect(soundEffectPath, 0.7f);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
