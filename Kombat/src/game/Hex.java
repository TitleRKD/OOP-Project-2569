package game;

import player.Minion;

public class Hex {
    private final int row;
    private final int col;
    private Minion minion;

    public Hex(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }

    public boolean hasMinion() {
        return minion != null;
    }

    public Minion getMinion() {
        return minion;
    }

    public void setMinion(Minion m) {
        this.minion = m;
    }


}
