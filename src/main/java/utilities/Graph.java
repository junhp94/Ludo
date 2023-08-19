package utilities;

import java.util.HashMap;
import java.util.Random;

public class Graph {
    private Node yellow;
    private Node yellowEntry;
    private Node green;
    private Node greenEntry;
    private Node red;
    private Node redEntry;
    private Node blue;
    private Node blueEntry;
    private Node[][] startingNodes;

    public Graph() {
        yellow = null;
        green = null;
        red = null;
        blue = null;
        startingNodes = new Node[4][4];
    }

    public void setYellow(Node y) {
        yellow = y;
    }

    public Node getYellow() {
        return yellow;
    }

    public void setYellowEntry(Node y) {
        yellowEntry = y;
    }

    public Node getYellowEntry() {
        return yellowEntry;
    }

    public void setGreen(Node g) {
        green = g;
    }

    public Node getGreen() {
        return green;
    }

    public void setGreenEntry(Node g) {
        greenEntry = g;
    }

    public Node getGreenEntry() {
        return greenEntry;
    }

    public void setRed(Node r) {
        red = r;
    }

    public Node getRed() {
        return red;
    }

    public void setRedEntry(Node r) {
        redEntry = r;
    }

    public Node getRedEntry() {
        return redEntry;
    }

    public void setBlue(Node b) {
        blue = b;
    }

    public Node getBlue() {
        return blue;
    }

    public void setBlueEntry(Node b) {
        blueEntry = b;
    }

    public Node getBlueEntry() {
        return blueEntry;
    }

    public Node[][] getStartingNodes() {
        return startingNodes;
    }
}
