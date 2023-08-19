package utilities;

import model.PlayerPiece;

import java.awt.*;
import java.util.ArrayList;

public class Node {

    private Integer nodeID;
    private Integer xPos;
    private Integer yPos;
    private Node next;
    private Node previous;
    private Node entryNode;
    private Boolean entry;
    private Boolean blocked;
    private Boolean exit;
    private Boolean home;
    private Boolean lastNode;
    public ArrayList<PlayerPiece> occupants;

    public Node() {
        nodeID = 0;
        xPos = 0;
        yPos = 0;
        next = null;
        entryNode = null;
        entry = false;
        blocked = false;
        exit = false;
        lastNode = false;
        home = false;
        occupants = new ArrayList<>();
    }


    public Integer getNodeID()
    {
        return nodeID;
    }

    public void setNodeID(Integer i)
    {
        nodeID = i;
    }
    public Integer getxPos() {
        return xPos;
    }

    public void setxPos(Integer i) {
        xPos = i;
    }

    public Integer getyPos() {
        return yPos;
    }

    public void setyPos(Integer i) {
        yPos = i;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node n) {
        next = n;
    }

    public Node getPrevious() {
        return previous;
    }

    public void setPrevious(Node n) {
        previous = n;
    }

    public Node getEntryNode() {
        return entryNode;
    }

    public void setEntryNode(Node n) {
        entryNode = n;
    }

    public Boolean isEntry() {
        return entry;
    }

    public void setEntry() {
        entry = !entry;
    }

    public Boolean isBlocked() {
        return blocked;
    };

    public void setBlocked() {
        blocked = !blocked;
    }

    public Boolean isExit() {
        return exit;
    }

    public void setExit() {
        exit = !exit;
    }

    public Boolean isHome() {
        return home;
    }

    public void setHome() {
        home = !home;
    }

    public Boolean isLastNode() {
        return lastNode;
    }

    public void setLastNode() {
        lastNode = !lastNode;
    }
}
