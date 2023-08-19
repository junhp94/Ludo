package model;

import utilities.Node;

import java.awt.*;

public class PieceFactory {
    public Piece createPiece(String color, Node startingNode, Node entryNode) {
        if (color.equals("red")) {
            return new PlayerPiece(Color.RED, startingNode, entryNode);
        } else if (color.equals("blue")) {
            return new PlayerPiece(Color.BLUE, startingNode, entryNode);
        } else if (color.equals("green")) {
            return new PlayerPiece(Color.GREEN, startingNode, entryNode);
        } else if (color.equals("yellow")) {
            return new PlayerPiece(Color.YELLOW, startingNode, entryNode);
        }
        throw new IllegalArgumentException("Invalid color: " + color);
    }
}
