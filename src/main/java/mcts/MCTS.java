package mcts;

import java.util.ArrayList;
import java.util.Random;

import game.*;

public class MCTS{
    private static Random random = new Random();

    public static void search(Node root, double timeGiven) {
        search(root, timeGiven, true);
    }

    public static void search(Node root, double timeGiven, boolean verbose) {
        long timeGivenNano = (long) (timeGiven * 1000000000);
        long tStart = System.nanoTime();
        long tCurrent = System.nanoTime();
        int printTimes = 10;
        int intervsElapsed = 0;
        long timeInterv = timeGivenNano / printTimes;
        while (tCurrent - tStart < timeGivenNano) {
            int oneItResult = oneIt(root);
            if (oneItResult == -1)
                break;
            tCurrent = System.nanoTime();

            if (tCurrent - tStart > intervsElapsed * timeInterv) {
                if (verbose)
                    System.out.print(String.format("%.1f  ", (double) (tCurrent - tStart) / 1000000000));
                intervsElapsed++;
            }
        }
        if (verbose)
            System.out.println();
    }

    public static Node getBestNode(Node root) {
        double maxV = 0;
        Node bestNode = null;
        for (Node c : root.children) {
            double score = ((double) c.wins / c.plays);
            if (score > maxV) {
                maxV = score;
                bestNode = c;
            }
        }
        return bestNode;
    }

    public static Move getBestMove(Node root) {
        double maxV = 0;
        Move bestMove = null;
        for (Node c : root.children) {
            double score = ((double) c.wins / c.plays);
            if (score > maxV) {
                maxV = score;
                bestMove = c.move;
            }
        }
        return bestMove;
    }

    private static int oneIt(Node root) {
        Node leaf = traverse(root);

        // TODO: Check WTF Python code here does

        ArrayList<Move> legalMoves = leaf.state.getLegalMoves();

        for (int moveNum = 0; moveNum < legalMoves.size(); moveNum++) {
            Move move = legalMoves.get(moveNum);
            for (Node child : leaf.children) {
                if (child.move.equals(move)) {
                    legalMoves.remove(moveNum); // Be careful because it's modifying the array while looping
                    moveNum--;
                    break;
                }
            }
        }

        if (legalMoves.size() == 0)
            return -1;
        Node newLeaf = genNode(leaf, legalMoves.get(random.nextInt(legalMoves.size())));
        leaf.children.add(newLeaf);
        boolean res = playout(newLeaf);
        backTrace(newLeaf, root, res);
        return 0;
    }

    private static Node traverse(Node node) {
        boolean fe = fullyExpanded(node);
        while (fe) {
            node = pickUCT(node);
            fe = fullyExpanded(node);
        }
        return node;
    }

    private static boolean fullyExpanded(Node node) {
        ArrayList<? extends Move> legalMoves = node.state.getLegalMoves();
        if (legalMoves.size() == 0) // Make sure this is OK
            return false;
        for (Move m : legalMoves) {
            boolean found = false;
            for (Node c : node.children) {
                if (c.move.equals(m)) {
                    found = true;
                    break;
                }
            }
            if (!found)
                return false;
        }
        return true;
    }

    private static Node pickUCT(Node node) {
        double maxV = 0;
        Node maxN = null;
        for (Node c : node.children) {
            double score = ((double) c.wins / c.plays) + Math.sqrt(2) * Math.sqrt(Math.log(node.plays + 1) / c.plays);
            if (score > maxV) {
                maxV = score;
                maxN = c;
            }
        }
        return maxN;
    }

    private static Node genNode(Node node, Move move) {
        State newState = node.state.clone();
        newState.applyMove(move);
        return new Node(newState, move, node);
    }

    private static boolean playout(Node node) {
        State s = node.state.clone();
        int startPlayer = s.getCurPlayer();
        int pWon = s.checkWon();
        while (pWon == 0) {
            ArrayList<Move> legalMoves = s.getLegalMoves();
            if (legalMoves.size() == 0) {
                pWon = s.checkWon();
                break;
            }
            // TODO: Implement smarter playout: automatically plays if can win
            pWon = s.applyMove(legalMoves.get(random.nextInt(legalMoves.size())));
        }
        return pWon != startPlayer;
    }

    private static void backTrace(Node node, Node root, boolean res) {
        node.plays++;
        if (res)
            node.wins++;
        if (node == root)
            return;
        backTrace(node.parent, root, !res);
    }

    public static void printTree(Node node, int depth) {
        if (depth >= 2) // Arbitrary
            return;
        for (int i = 0; i < depth; i++) {
            System.out.print("   ");
        }
        if (node.move != null) {
            System.out.print(node.move + ": ");
        } else {
            System.out.print("R: ");
        }
        System.out.println(String.format("%d/%d,%.2f", node.wins, node.plays, (double) node.wins / node.plays));
        // System.out.println(node.wins + "/" + node.plays + "," +
        // (double)node.wins/node.plays);
        for (Node c : node.children) {
            printTree(c, depth + 1);
        }
    }
}