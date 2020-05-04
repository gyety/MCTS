package mcts;

import java.util.ArrayList;

import game.*;

public class Node {
	public State state;
	public Move move;
	public Node parent;
	public ArrayList<Node> children;
	public int wins;
	public int plays;

	public Node(State state, Move move, Node parent) {
		this.state = state;
		this.move = move;
		this.parent = parent;
		this.children = new ArrayList<Node>();
		this.wins = 0;
		this.plays = 0;
	}

}
