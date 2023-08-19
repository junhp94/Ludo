package model;
import utilities.Graph;
import utilities.Node;
import java.util.*;
import java.awt.*;

public class InitializeGraph {
    private Map<Integer,Point> coordinates;

    public Graph setupGraph() {
        coordinates = new HashMap<Integer, Point>() {{
            put(1, new Point(5, 245)); //starting blue
            put(2, new Point(45, 245));
            put(3, new Point(85, 245));
            put(4, new Point(125, 245));
            put(5, new Point(165, 245));
            put(6, new Point(205, 245));

            put(7, new Point(245, 205));
            put(8, new Point(245, 165));
            put(9, new Point(245, 125));
            put(10, new Point(245, 85));
            put(11, new Point(245, 45));
            put(12, new Point(245, 5));
            put(13, new Point(285, 5)); //leading to middle path (check 100s)
            put(14, new Point(325, 5)); //starting red?
            put(15, new Point(325, 45));
            put(16, new Point(325, 85));
            put(17, new Point(325, 125));
            put(18, new Point(325, 165));
            put(19, new Point(325, 205));

            put(20, new Point(365, 245));
            put(21, new Point(405, 245));
            put(22, new Point(445, 245));
            put(23, new Point(485, 245));
            put(24, new Point(525, 245));
            put(25, new Point(565, 245));
            put(26, new Point(565, 285)); //leading to middle path (check 200s)
            put(27, new Point(565, 325)); //starting green
            put(28, new Point(525, 325));
            put(29, new Point(485, 325));
            put(30, new Point(445, 325));
            put(31, new Point(405, 325));
            put(32, new Point(365, 325));

            put(33, new Point(325, 365));
            put(34, new Point(325, 405));
            put(35, new Point(325, 445));
            put(36, new Point(325, 485));
            put(37, new Point(325, 525));
            put(38, new Point(325, 565));
            put(39, new Point(285, 565)); //leading to middle path (check 300s)
            put(40, new Point(245, 565)); //starting yellow
            put(41, new Point(245, 525));
            put(42, new Point(245, 485));
            put(43, new Point(245, 445));
            put(44, new Point(245, 405));
            put(45, new Point(245, 365));

            put(46, new Point(205, 325));
            put(47, new Point(165, 325));
            put(48, new Point(125, 325));
            put(49, new Point(85, 325));
            put(50, new Point(45, 325));
            put(51, new Point(5, 325)); //leading to middle path (check 400s)
            put(52, new Point(5, 285)); //connect this back to initial graph

            put(101, new Point(285, 45));
            put(102, new Point(285, 85));
            put(103, new Point(285, 125));
            put(104, new Point(285, 165));
            put(105, new Point(285, 205));
            put(106, new Point(285, 235));//last node

            put(201, new Point(525, 285));
            put(202, new Point(485, 285));
            put(203, new Point(445, 285));
            put(204, new Point(405, 285));
            put(205, new Point(365, 285));
            put(206, new Point(335, 285));//last node

            put(301, new Point(285, 525));
            put(302, new Point(285, 485));
            put(303, new Point(285, 445));
            put(304, new Point(285, 405));
            put(305, new Point(285, 365));
            put(306, new Point(285, 335));//last node

            put(401, new Point(45, 285));
            put(402, new Point(85, 285));
            put(403, new Point(125, 285));
            put(404, new Point(165, 285));
            put(405, new Point(205, 285));
            put(406, new Point(235, 285));//last node

            put(501, new Point(65,65));
            put(502, new Point(145,65));
            put(503, new Point(65,140));
            put(504, new Point(145,140));//blue start

            put(505, new Point(425,65));
            put(506, new Point(505,65));
            put(507, new Point(425,140));
            put(508, new Point(505,140));//red start


//            put(509, new Point(425,435));
//            put(510, new Point(505,435));
//            put(511, new Point(425,500));
//            put(512, new Point(505,500));//green start

            put(512, new Point(505,500));
            put(511, new Point(425,500));
            put(510, new Point(505,435));
            put(509, new Point(425,435));//green start


            put(513, new Point(65,435));
            put(514, new Point(145,435));
            put(515, new Point(65,500));
            put(516, new Point(145,500));//yellow start
        }};

        Graph graph = new Graph();

        Node newNode = new Node();
        Node curr = new Node();
        Node first = null;

        for (int i = 1; i <= 52; i++) {
            Point coords = coordinates.get(i);
            int x = coords.x;
            int y = coords.y;
            newNode.setNodeID(i);
            newNode.setxPos(x);
            newNode.setyPos(y);

            if (i == 1) {
                first = newNode;
            }
            if (i == 2) { //set starting positions
                graph.setBlue(newNode);
                newNode.setExit();
            } else if (i == 15) {
                graph.setRed(newNode);
                newNode.setExit();
            } else if (i == 28) {
                graph.setGreen(newNode);
                newNode.setExit();
            } else if (i == 41) {
                graph.setYellow(newNode);
                newNode.setExit();
            }

            if (i == 13 || i == 26 || i == 39 || i == 52) { //create paths to inside
                Node interiorNewNode = new Node();
                Node interiorCurr;
                newNode.setEntry();
                newNode.setEntryNode(interiorNewNode);
                if (i == 13) {
                    graph.setRedEntry(newNode.getEntryNode());
                } else if (i == 26) {
                    graph.setGreenEntry(newNode.getEntryNode());
                } else if (i == 39) {
                    graph.setYellowEntry(newNode.getEntryNode());
                } else {
                    graph.setBlueEntry(newNode.getEntryNode());
                }

                for (int j = (i/13) * 100 + 1; j <= (i/13) * 100 + 6; j++) {
                    Point interiorCoords = coordinates.get(j);
                    int interiorX = interiorCoords.x;
                    int interiory = interiorCoords.y;
                    interiorNewNode.setxPos(interiorX);
                    interiorNewNode.setyPos(interiory);

                    if (j != (i/13) * 100 + 6) {
                        interiorCurr = interiorNewNode;
                        interiorNewNode = new Node();
                        interiorCurr.setNext(interiorNewNode);
                        interiorNewNode.setPrevious(interiorCurr);
                    }
                }
                interiorNewNode.setHome();
            }

            if (i == 52) { //make graph circular and continue nodes
                newNode.setNext(first);
            } else {
                curr = newNode;
                newNode = new Node();
                curr.setNext(newNode);
            }
        }

        int k = 1;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                curr = new Node();
                if (i == 0) {
                    curr.setNext(graph.getBlue());
                } else if (i == 1) {
                    curr.setNext(graph.getRed());
                } else if (i == 2) {
                    curr.setNext(graph.getGreen());
                } else {
                    curr.setNext(graph.getYellow());
                }

                curr.setxPos(coordinates.get(500+k).x);
                curr.setyPos(coordinates.get(500+k).y);
                graph.getStartingNodes()[i][j] = curr;
                k++;
            }
        }

        return graph;
    }
}