package game;

import java.util.ArrayList;

public abstract class State <T extends Move> {
    protected int curPlayer;
    protected int pWon;

    public State() {
        this.curPlayer = 1;
        this.pWon = 0;
    }

    public int applyMove(T move) {
        if (!isLegal(move))
            return -1;
        // Apply move
        return checkWon();
    }

    protected void updateWon() {
        if (pWon != 0)
            return;
        // Set pWon if a player has won
    }

    public int checkWon() {
        this.updateWon();
        return pWon;
    }

    public ArrayList<T> getLegalMoves() {
        return null;
    }

    protected abstract boolean isLegal(T move);

    private void flipPlayer() {
        if (curPlayer == 1)
            curPlayer = 2;
        else
            curPlayer = 1;
    }

    public int getCurPlayer() {
        return curPlayer;
    }

    public abstract void printState();

    public abstract State clone();
}