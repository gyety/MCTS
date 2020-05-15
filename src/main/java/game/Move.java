package game;

public abstract class Move {
    public int player;

    public abstract boolean equals(C4Move m2);

    public abstract String getString();

}