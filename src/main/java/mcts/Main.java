package mcts;

import java.util.Scanner;

import game.*;

public class Main {

    public static void main(String[] args) {
        playMCTS(4);
    }
    static void playWithMCTS() {
        Scanner sc = new Scanner(System.in);
        C4State s = new C4State();
        s.printState();
        System.out.println();
        while (s.checkWon() == 0) {
            System.out.print("Move: ");
            int loc = sc.nextInt() - 1;
            if (loc == -1) {
                Node root = new Node(s, null, null); // TODO: Add new constructor that defaults nulls
                MCTS.search(root, sc.nextDouble(), true);
                Node bestNode = MCTS.getBestNode(root);
                System.out.println(String.format("Move:%d, Conf:%.2f, Sims:%d", bestNode.move.getString(),
                        (double) bestNode.wins / bestNode.plays, root.plays));
                continue;
            }
            if (s.applyMove(new C4Move(loc)) == -1) {
                System.out.println("Not legal.");
                continue;
            }
            s.printState();
            System.out.println();

            if (s.checkWon() != 0)
                break;
        }
        System.out.println("Player " + s.checkWon() + " won!");
        sc.close();
    }

    static void playMCTS(double timeGiven) {
        Scanner sc = new Scanner(System.in);
        C4State s = new C4State();
        s.printState();
        System.out.println();
        while (s.checkWon() == 0) {
            System.out.print("Move: ");
            int loc = sc.nextInt() - 1;
            if (s.applyMove(new C4Move(loc)) == -1) {
                System.out.println("Not legal.");
                continue;
            }
            s.printState();
            System.out.println();

            if(s.checkWon()!=0)
                break;

            Node root = new Node(s, null, null); // TODO: Add new constructor that defaults nulls
            MCTS.search(root, timeGiven, false);
            Node bestNode = MCTS.getBestNode(root);
            System.out.println(String.format("Conf:%.2f, Pred:%dk", (double)bestNode.wins/bestNode.plays, root.plays/1000));
            s.applyMove((C4Move) bestNode.move);
            s.printState();
            System.out.println();
        }
        System.out.println("Player " + s.checkWon() + " won!");
        sc.close();
    }

    static void testMCTS() {
        C4State s = new C4State();
        Node n = new Node(s, null, null);
        MCTS.search(n, 10);

        MCTS.printTree(n, 0);
    }

    static void playDemo() {
        Scanner sc = new Scanner(System.in);
        C4State s = new C4State();
        s.printState();
        while (s.checkWon() == 0) {
            int loc = sc.nextInt() - 1;
            if (s.applyMove(new C4Move(loc)) == -1) {
                System.out.println("Not legal.");
                continue;
            }
            s.printState();
        }
        System.out.println("Player " + s.checkWon() + " won!");
        sc.close();
    }
}