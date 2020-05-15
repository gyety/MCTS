package game;

import java.util.ArrayList;

public class C4State extends State <C4Move> {
    private int curPlayer;
    private int pWon;
    private int grid[][] = new int[6][7]; // x is left to right, y is down to up

    public C4State() {
        this.curPlayer = 1;
        this.pWon = 0;
    }

    private C4State(int curPlayer, int pWon, int grid[][]) {
        this.curPlayer = curPlayer;
        this.pWon = pWon;
        this.grid = grid;
    }

    @Override
    public int applyMove(C4Move move) {
        if (!isLegal(move))
            return -1;
        int l = move.loc;
        int p = move.player;
        if (p == 0)
            p = curPlayer;
        for (int y = 0; y < 6; y++) {
            if (grid[y][l] == 0) {
                grid[y][l] = p;
                flipPlayer();
                return checkWon();
            }
        }
        return -1;
    }

    protected void updateWon() {
        if (pWon != 0)
            return;
        for (int y = 0; y < 6; y++) { // Horizontal
            for (int x = 0; x < 7 - 3; x++) {
                int c = grid[y][x];
                if (c != 0 && c == grid[y][x + 1] && c == grid[y][x + 2] && c == grid[y][x + 3]) {
                    pWon = c;
                    return;
                }
            }
        }
        for (int y = 0; y < 6 - 3; y++) { // Vertical
            for (int x = 0; x < 7; x++) {
                int c = grid[y][x];
                if (c != 0 && c == grid[y + 1][x] && c == grid[y + 2][x] && c == grid[y + 3][x]) {
                    pWon = c;
                    return;
                }
            }
        }
        for (int y = 0; y < 6 - 3; y++) { // Diagonal /
            for (int x = 0; x < 7 - 3; x++) {
                int c = grid[y][x];
                if (c != 0 && c == grid[y + 1][x + 1] && c == grid[y + 2][x + 2] && c == grid[y + 3][x + 3]) {
                    pWon = c;
                    return;
                }
            }
        }
        for (int y = 3; y < 6; y++) { // Diagonal \
            for (int x = 0; x < 7 - 3; x++) {
                int c = grid[y][x];
                if (c != 0 && c == grid[y - 1][x + 1] && c == grid[y - 2][x + 2] && c == grid[y - 3][x + 3]) {
                    pWon = c;
                    return;
                }
            }
        }
    }

    public int checkWon() {
        this.updateWon();
        return pWon;
    }

    @Override
    public ArrayList<C4Move> getLegalMoves() {
        ArrayList<C4Move> ms = new ArrayList<C4Move>();
        for (int m = 0; m < 7; m++) {
            C4Move cm = new C4Move(m);
            if (isLegal(cm))
                ms.add(cm);
        }
        return ms;
    }


    @Override
    protected boolean isLegal(C4Move move) {
        int l = move.loc;
        if(l<0 || l>=7)
            return false;
        for (int y = 0; y < 6; y++) {
            if (grid[y][l] == 0) {
                return true;
            }
        }
        return false;
    }

    private void flipPlayer() {
        if (curPlayer == 1)
            curPlayer = 2;
        else
            curPlayer = 1;
    }

    public int getCurPlayer() {
        return curPlayer;
    }

    public void printState() {
        for (int y = 5; y >= 0; y--) {
            for (int x = 0; x < 7; x++) {
                switch (grid[y][x]) {
                    case 0:
                        System.out.print(". ");
                        break;
                    case 1:
                        System.out.print("X ");
                        break;
                    case 2:
                        System.out.print("0 ");
                        break;
                }

            }
            System.out.println();
        }
    }

    public C4State clone() {
        int newGrid[][] = new int[6][7];
        for(int y=0; y<6; y++) {
            newGrid[y] = grid[y].clone();
        }
        C4State newState = new C4State(curPlayer, pWon, newGrid);
        return newState;
    }
}