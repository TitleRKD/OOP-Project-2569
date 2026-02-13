package player;

import strategy.Core.Strategy;

public class MinionType {

    private final String name;
    private final long defenseFactor;
    private final Strategy strategy;

    public MinionType(String name, long defenseFactor, Strategy strategy) {
        this.name = name;
        this.defenseFactor = defenseFactor;
        this.strategy = strategy;
    }

    public String getName() { return name; }
    public long getDefenseFactor() { return defenseFactor; }
    public Strategy getStrategy() { return strategy; }
}
