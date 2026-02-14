package config;

public class Config {
    public long spawnCost;
    public long hexPurchaseCost;
    public long initBudget;
    public long initHp;
    public long turnBudget;
    public long maxBudget;
    public long interestPct;
    public long maxTurns;
    public long maxSpawns;

    public void showConfig() {
        System.out.println("spawncost = " + spawnCost);
        System.out.println("hex_purchasecost = " + hexPurchaseCost);
        System.out.println("initbudget = " + initBudget);
        System.out.println("inithp = " + initHp);
        System.out.println("turnBudget = " + turnBudget);
        System.out.println("maxBudget = " + maxBudget);
        System.out.println("interestPct = " + interestPct);
        System.out.println("maxTurns = " + maxTurns);
        System.out.println("maxSpawns = " + maxSpawns);
    }
}
