package game;

public class C4Move extends Move{
    public int loc;
    public int player;

    public C4Move(int loc) {
        this.loc = loc;
    }

    public C4Move(int loc, int player) {
        this.loc = loc;
        this.player = player;
    }

    public boolean equals(C4Move m2) {
        return this.loc == m2.loc;
    }

    public String getString(){
        return Integer.toString(this.loc);
    }
}