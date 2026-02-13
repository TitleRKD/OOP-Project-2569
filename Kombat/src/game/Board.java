package game;

import java.util.ArrayList;
import java.util.List;
import player.Minion;

public class Board {

    private final Hex[][] grid;

    public Board(int rows, int cols) {
        grid = new Hex[rows][cols];
        for (int r = 0; r < rows; r++)
            for (int c = 0; c < cols; c++)
                grid[r][c] = new Hex(r, c);
    }

    public Hex getHex(int row, int col) {
        if (!isInside(row, col)) return null;
        return grid[row][col];
    }

    public boolean isInside(int row, int col) {
        return row >= 0 && col >= 0 && row < grid.length && col < grid[0].length;
    }

    public boolean isOccupied(int row, int col) {
        Hex h = getHex(row, col);
        return h != null && h.hasMinion();
    }

    public void placeMinion(Minion m, int row, int col) {
        Hex h = getHex(row, col);
        if (h != null && !h.hasMinion()) {
            h.setMinion(m);
        }
    }

    public void removeMinion(Minion m) {
        Hex h = getHex(m.getRow(), m.getCol());
        if (h != null) h.setMinion(null);
    }

    public List<Hex> getAdjacentHexes(Hex hex) {
        return new ArrayList<>(); // TODO
    }
}
