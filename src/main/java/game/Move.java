package game;

public class Move {
    public int loc;
    public int player;

    public Move(int loc) {
        this.loc = loc;
    }

    public Move(int loc, int player) {
        this.loc = loc;
        this.player = player;
    }

    public boolean equals(Move m2) {
        return this.loc == m2.loc;
    }
}