package context;

import player.Minion;
import player.Player;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ExecutionContext {
    private final Minion minion;
    private final GameContext gameContext;
    private final Map<String, Long> localVars = new HashMap<>();
    private boolean done = false;

    public ExecutionContext(Minion minion, GameContext gameContext) {
        this.minion = minion;
        this.gameContext = gameContext;
    }

    public long getVariable(String name) {
        switch (name) {
            case "row": return minion.getRow();
            case "col": return minion.getCol();
            case "Budget": return (long) minion.getOwner().getBudget();
            case "Int": {
                double budget = minion.getOwner().getBudget();
                if (budget < 1) return 0;
                double baseRate = gameContext.getInterestRate();
                double r = baseRate * Math.log10(budget) * Math.log(gameContext.getGame().getTurnCount());
                return (long) r;
            }
            case "MaxBudget": return gameContext.getMaxBudget();
            case "SpawnsLeft": return gameContext.getSpawnsLeft();
            case "random": return new Random().nextInt(1000);
            default:
                if (Character.isUpperCase(name.charAt(0))) {
                    return minion.getOwner().getGlobalVar(name);
                } else {
                    return localVars.getOrDefault(name, 0L);
                }
        }
    }

    public void setVariable(String name, long value) {
        switch (name) {
            case "row": case "col": case "Budget": case "Int": case "MaxBudget": case "SpawnsLeft": case "random":
                return;
            default:
                if (Character.isUpperCase(name.charAt(0))) {
                    minion.getOwner().setGlobalVar(name, value);
                } else {
                    localVars.put(name, value);
                }
        }
    }

    public boolean isDone() { return done; }
    public void markDone() { done = true; }
    public GameContext getGameContext() { return gameContext; }
    public Minion getMinion() { return minion; }
}