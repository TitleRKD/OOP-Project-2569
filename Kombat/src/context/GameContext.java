package context;

import enums.Direction;
import enums.InfoType;
import game.Board;
import game.Game;
import game.Hex;
import player.Minion;
import player.Player;

public class GameContext {
    private final Game game;
    private final Board board;
    private final Player currentPlayer;

    public GameContext(Game game, Board board, Player currentPlayer) {
        this.game = game;
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    public Board getBoard() { return board; }

    public long getBudget() {
        return (long) currentPlayer.getBudget();
    }

    public long getMaxBudget() {
        return game.getConfig().maxBudget;
    }

    public long getInterestRate() {
        return game.getConfig().interestPct;
    }

    public int getSpawnsLeft() {
        return currentPlayer.getSpawnsLeft();
    }

    public long getRow(Minion m) {
        return m.getRow();
    }

    public long getCol(Minion m) {
        return m.getCol();
    }

    public long evaluateInfoExpression(Minion minion, InfoType type, Direction dir) {
        switch (type) {
            case ALLY:
                return findClosest(minion, true, null);
            case OPPONENT:
                return findClosest(minion, false, null);
            case NEARBY:
                return findNearby(minion, dir);
            default:
                return 0;
        }
    }

    private long findClosest(Minion source, boolean ally, Direction specificDir) {
        // If specificDir is null, search all directions; else only that direction.
        long bestValue = 0;
        int bestDist = Integer.MAX_VALUE;
        int bestDirNum = Integer.MAX_VALUE;

        Direction[] dirs = specificDir != null ? new Direction[]{specificDir} : Direction.values();

        for (Direction d : dirs) {
            int dist = 1;
            Hex current = board.getHex(source.getRow(), source.getCol());
            while (true) {
                Hex next = board.getHexDirection(current, d);
                if (next == null) break;
                current = next;
                if (current.hasMinion()) {
                    Minion m = current.getMinion();
                    boolean isAlly = m.getOwner() == source.getOwner();
                    if ((ally && isAlly) || (!ally && !isAlly)) {
                        // found
                        int dirNum = getDirectionNumber(d);
                        if (dist < bestDist || (dist == bestDist && dirNum < bestDirNum)) {
                            bestDist = dist;
                            bestDirNum = dirNum;
                            bestValue = dirNum + dist * 10; // as per spec: units digit = direction, rest = distance
                        }
                        break; // stop searching this direction
                    }
                }
                dist++;
            }
        }
        return bestValue;
    }

    private long findNearby(Minion source, Direction dir) {
        int dist = 1;
        Hex current = board.getHex(source.getRow(), source.getCol());
        while (true) {
            Hex next = board.getHexDirection(current, dir);
            if (next == null) break;
            current = next;
            if (current.hasMinion()) {
                Minion m = current.getMinion();
                boolean isAlly = m.getOwner() == source.getOwner();
                long hp = m.getHp();
                int hpDigits = String.valueOf(hp).length();
                int defDigits = String.valueOf(m.getType().getDefenseFactor()).length();
                long value = 100 * hpDigits + 10 * defDigits + dist;
                return isAlly ? -value : value;
            }
            dist++;
        }
        return 0;
    }

    private int getDirectionNumber(Direction d) {
        switch (d) {
            case UP: return 1;
            case UPRIGHT: return 2;
            case DOWNRIGHT: return 3;
            case DOWN: return 4;
            case DOWNLEFT: return 5;
            case UPLEFT: return 6;
            default: return 0;
        }
    }

    public Game getGame() { return game; }
    public Player getCurrentPlayer() { return currentPlayer; }
}