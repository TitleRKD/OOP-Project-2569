package player;

import config.Config;
import game.Board;
import game.Game;
import game.Hex;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Player {
    private final String name;
    private final boolean isBot;
    private double budget;
    private int spawnsLeft;
    private final List<Minion> minions = new ArrayList<>();
    private final Set<Hex> ownedHexes = new HashSet<>();
    private final java.util.Map<String, Long> globalVars = new java.util.HashMap<>();
    private Game game;

    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
    }

    public String getName() {
        return name;
    }

    public void setGame(Game game) { this.game = game; }
    public Game getGame() { return game; }

    public void startTurn() {}

    public void endTurn() {}

    public void addBudget(double amt) {
        budget += amt;
    }

    public boolean spendBudget(double amt) {
        if (budget < amt) return false;
        budget -= amt;
        return true;
    }

    public double getBudget() { return budget; }
    public void setBudget(double budget) { this.budget = budget; }

    public int getSpawnsLeft() { return spawnsLeft; }
    public void setSpawnsLeft(int spawnsLeft) { this.spawnsLeft = spawnsLeft; }

    public void spawnMinion(MinionType type, Hex hex) {
        if (spawnsLeft <= 0) return;
        if (!ownedHexes.contains(hex)) return;
        if (hex.hasMinion()) return;
        long cost = game.getConfig().spawnCost;
        if (!spendBudget(cost)) return;

        Minion m = new Minion(type, this, hex.getRow(), hex.getCol(), game.getConfig().initHp);
        minions.add(m);
        game.getBoard().placeMinion(m, hex.getRow(), hex.getCol());
        spawnsLeft--;
    }

    public void purchaseHex(Hex hex) {
        if (ownedHexes.contains(hex)) return;
        long cost = game.getConfig().hexPurchaseCost;
        if (!spendBudget(cost)) return;
        ownedHexes.add(hex);
    }

    public List<Minion> getMinions() { return minions; }
    public Set<Hex> getOwnedHexes() { return ownedHexes; }

    public long getGlobalVar(String name) {
        return globalVars.getOrDefault(name, 0L);
    }

    public void setGlobalVar(String name, long value) {
        globalVars.put(name, value);
    }
}