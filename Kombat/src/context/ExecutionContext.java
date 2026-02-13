package context;

import java.util.HashMap;
import java.util.Map;
import player.Minion;

public class ExecutionContext {

    private final Minion minion;
    private final GameContext gameContext;
    private final Map<String, Long> variables = new HashMap<>();
    private boolean done = false;

    public ExecutionContext(Minion minion, GameContext gameContext) {
        this.minion = minion;
        this.gameContext = gameContext;
    }

    public long getVariable(String name) {
        return variables.getOrDefault(name, 0L);
    }

    public void setVariable(String name, long value) {
        variables.put(name, value);
    }

    public boolean isDone() {
        return done;
    }

    public void markDone() {
        done = true;
    }

    public GameContext getGameContext() {
        return gameContext;
    }

    public Minion getMinion() {
        return minion;
    }
}
