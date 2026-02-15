package game;

import player.Minion;
import player.Player;

public class Hex {
    private final int row;
    private final int col;
    private Minion minion;
    private Player player;

    public Hex(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public Player getPlayer() { return player; }
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
