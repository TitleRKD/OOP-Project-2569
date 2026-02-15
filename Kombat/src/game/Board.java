package game;

import java.util.ArrayList;
import java.util.List;
import player.Minion;
import enums.Direction;

public class Board {

    private final Hex[][] grid;

    public Board(int rows, int cols) {
        grid = new Hex[rows][cols];
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = new Hex(r + 1, c + 1);
            }
        }
    }

    public Hex getHex(int row, int col) {
        if (!isInside(row, col)) return null;
        return grid[row-1][col-1];
    }

    public boolean isInside(int row, int col) {
        return row >= 1 && col >= 1 &&
                row <= grid.length &&
                col <= grid[0].length;
    }

    public boolean isOccupied(int row, int col) {
        Hex h = getHex(row, col);
        return h != null && h.hasMinion();
    }

    public void placeMinion(Minion m, int row, int col) {
        Hex h = getHex(row, col);
        row = h.getRow();
        col = h.getCol();
        if (!isOccupied(row, col)) {
            h.setMinion(m);
            m.setPosition(row, col);
        }else{
            System.out.println("occupied");
        }
    }

    public void removeMinion(Minion m) {
        Hex h = getHex(m.getRow(), m.getCol());
        if (h != null){
            h.setMinion(null);
            m.setPosition(0, 0);
        }
    }

    public List<Hex> getAdjacentHexes(Hex hex) {
        List<Hex> result = new ArrayList<>();
        int row = hex.getRow();
        int col = hex.getCol();
        int[][] evenColDirs = {
                {-1, 0},
                {1, 0},
                {-1, -1},
                {-1, 1},
                {0, -1},
                {0, 1}
        };
        int[][] oddColDirs = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
                {1, -1},
                {1, 1}
        };

        int[][] dirs;
        if (col % 2 == 0) {
            dirs = evenColDirs;
        } else {
            dirs = oddColDirs;
        }

        for (int[] d : dirs) {
            int nr = row + d[0];
            int nc = col + d[1];
            if (isInside(nr, nc)) {
                result.add(getHex(nr, nc));
            }
        }
        return result;
    }

    public Hex getHexDirection(Hex hex, Direction dir) {
        int row = hex.getRow();
        int col = hex.getCol();
        int[][] evenColDirs = {
                {-1, 0},
                {1, 0},
                {-1, -1},
                {-1, 1},
                {0, -1},
                {0, 1},
                {0, -1}, // LEFT
                {0, 1}   // RIGHT
        };
        int[][] oddColDirs = {
                {-1, 0},
                {1, 0},
                {0, -1},
                {0, 1},
                {1, -1},
                {1, 1},
                {0, -1}, // LEFT
                {0, 1}   // RIGHT
        };

        int[][] dirs;
        if (col % 2 == 0) {
            dirs = evenColDirs;
        } else {
            dirs = oddColDirs;
        }

        int index = switch (dir) {
            case UP -> 0;
            case DOWN -> 1;
            case UPLEFT -> 2;
            case UPRIGHT -> 3;
            case DOWNLEFT -> 4;
            case DOWNRIGHT -> 5;
            case LEFT -> 6;
            case RIGHT -> 7;
        };

        int nearRow = row + dirs[index][0];
        int nearCol = col + dirs[index][1];

        if (!isInside(nearRow, nearCol)) return null;
        return getHex(nearRow, nearCol);
    }

    public void printBoard() {
        for (int r = 1; r <= grid.length; r++) {
            for (int c = 1; c <= grid[0].length; c++) {
                Hex h = getHex(r, c);
                if (h.hasMinion()) {
                    System.out.print("[M]");
                } else {
                    System.out.print("[ ]");
                }
            }
            System.out.println();
        }
    }
}
