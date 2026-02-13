package context;

import game.Board;
import game.Game;
import player.Player;
import player.Minion;
import enums.InfoType;
import enums.Direction;

public class GameContext {
    private final Game game;
    private final Board board;
    private final Player currentPlayer;

    public GameContext(Game game, Board board, Player currentPlayer) {
        this.game = game;
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

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
        // TODO: implement info evaluation
        return 0;
    }
}
