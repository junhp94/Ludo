package model;

import utilities.*;

import java.util.Random;

public class Board {

    public Graph board;

    public Board() {
        InitializeGraph graph = new InitializeGraph();
        board = graph.setupGraph();

    }
}
