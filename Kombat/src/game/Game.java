package game;

import config.Config;
import enums.GameMode;
import player.Player;
import player.Minion;
import java.util.List;

public class Game {
    private final Config config;
    private final Player p1;
    private final Player p2;
    private final GameMode mode;
    private Board board;
    private int turnCount = 0;
    private Player currentPlayer;
    private boolean gameOver = false;
    private Player winner = null;

    public Game(Config config, Player p1, Player p2, GameMode mode) {
        this.config = config;
        this.p1 = p1;
        this.p2 = p2;
        this.mode = mode;
        this.board = new Board(8, 8);
        p1.setGame(this);
        p2.setGame(this);
    }

    public void start() {
        // initial setup: give initial budget, set spawns left, define starting territories
        p1.setBudget(config.initBudget);
        p2.setBudget(config.initBudget);
        p1.setSpawnsLeft((int) config.maxSpawns);
        p2.setSpawnsLeft((int) config.maxSpawns);

        // Player1: top-left (rows 1-1)
        p1.getOwnedHexes().add(board.getHex(1,1));

        // Player2: bottom-right (8,8)
        p2.getOwnedHexes().add(board.getHex(8,8));

        turnCount = 1;
        currentPlayer = p1; // p1 starts
    }

    public void nextTurn() {
        if (gameOver) return;

        // 1. Add turn budget
        currentPlayer.addBudget(config.turnBudget);

        // 2. Calculate interest
        double budgetVal = currentPlayer.getBudget();
        if (budgetVal >= 1) {
            double b = config.interestPct;
            double r = b * Math.log10(budgetVal) * Math.log(turnCount);
            double interest = budgetVal * r / 100.0;
            currentPlayer.addBudget(interest);
        }

        // Cap budget
        if (currentPlayer.getBudget() > config.maxBudget) {
            currentPlayer.setBudget(config.maxBudget);
        }

        // 3. Opportunity to spawn – this is done by the UI/controller, not automatically

        // 4. Execute all minions of current player (in order of creation)
        for (Minion minion : currentPlayer.getMinions()) {
            if (!minion.isAlive()) continue;
            context.GameContext ctx = new context.GameContext(this, board, currentPlayer);
            minion.executeStrategy(ctx);
        }

        // 5. Switch player
        currentPlayer = (currentPlayer == p1) ? p2 : p1;
        turnCount++;

        // Check game over conditions
        checkGameOver();
    }

    private void checkGameOver() {
        if (p1.getMinions().stream().noneMatch(Minion::isAlive)) {
            gameOver = true;
            winner = p2;
        } else if (p2.getMinions().stream().noneMatch(Minion::isAlive)) {
            gameOver = true;
            winner = p1;
        } else if (turnCount > config.maxTurns) {
            gameOver = true;
            int p1Count = (int) p1.getMinions().stream().filter(Minion::isAlive).count();
            int p2Count = (int) p2.getMinions().stream().filter(Minion::isAlive).count();
            if (p1Count > p2Count) winner = p1;
            else if (p2Count > p1Count) winner = p2;
            else {
                long p1Hp = p1.getMinions().stream().filter(Minion::isAlive).mapToLong(Minion::getHp).sum();
                long p2Hp = p2.getMinions().stream().filter(Minion::isAlive).mapToLong(Minion::getHp).sum();
                if (p1Hp > p2Hp) winner = p1;
                else if (p2Hp > p1Hp) winner = p2;
                else {
                    if (p1.getBudget() > p2.getBudget()) winner = p1;
                    else if (p2.getBudget() > p1.getBudget()) winner = p2;
                    else winner = null;
                }
            }
        }
    }

    public boolean isGameOver() { return gameOver; }
    public Player getWinner() { return winner; }
    public Config getConfig() { return config; }
    public Board getBoard() { return board; }
    public Player getCurrentPlayer() { return currentPlayer; }
    public int getTurnCount() { return turnCount; }

    public void showConfig() {
        System.out.println("spawncost = " + config.spawnCost);
        System.out.println("hex_purchasecost = " + config.hexPurchaseCost);
        System.out.println("initbudget = " + config.initBudget);
        System.out.println("inithp = " + config.initHp);
        System.out.println("turnBudget = " + config.turnBudget);
        System.out.println("maxBudget = " + config.maxBudget);
        System.out.println("interestPct = " + config.interestPct);
        System.out.println("maxTurns = " + config.maxTurns);
        System.out.println("maxSpawns = " + config.maxSpawns);
    }
}