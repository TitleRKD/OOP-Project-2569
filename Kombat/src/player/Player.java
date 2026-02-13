package player;

import java.util.ArrayList;
import java.util.List;
import game.Hex;

public class Player {

    private final String name;
    private final boolean isBot;
    private double budget;
    private int spawnsLeft;
    private final List<Minion> minions = new ArrayList<>();

    public Player(String name, boolean isBot) {
        this.name = name;
        this.isBot = isBot;
    }

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
    public int getSpawnsLeft() { return spawnsLeft; }

    public void spawnMinion(MinionType type, Hex hex) {}
    public void purchaseHex(Hex hex) {}

    public List<Minion> getMinions() {
        return minions;
    }
}
