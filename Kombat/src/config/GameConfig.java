package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class GameConfig {

    public long spawnCost = 100;
    public long hexPurchaseCost = 1000;
    public long initBudget = 10000;
    public long initHp = 100;
    public long turnBudget = 90;
    public long maxBudget = 23456;
    public long interestPct = 5;
    public long maxTurns = 69;
    public long maxSpawns = 47;

    private static GameConfig instance;

    private GameConfig() {}

    public static GameConfig getInstance() {
        if (instance == null) {
            instance = new GameConfig();
        }
        return instance;
    }

    public void loadConfig(String filePath) {
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream(filePath)) {
            prop.load(input);

            spawnCost = Long.parseLong(prop.getProperty("spawn_cost", "100"));
            hexPurchaseCost = Long.parseLong(prop.getProperty("hex_purchase_cost", "1000"));
            initBudget = Long.parseLong(prop.getProperty("init_budget", "10000"));
            initHp = Long.parseLong(prop.getProperty("init_hp", "100"));
            turnBudget = Long.parseLong(prop.getProperty("turn_budget", "90"));
            maxBudget = Long.parseLong(prop.getProperty("max_budget", "23456"));
            interestPct = Long.parseLong(prop.getProperty("interest_pct", "5"));
            maxTurns = Long.parseLong(prop.getProperty("max_turns", "69"));
            maxSpawns = Long.parseLong(prop.getProperty("max_spawns", "47"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    public Config toConfig() {
        Config c = new Config();
        c.spawnCost = spawnCost;
        c.hexPurchaseCost = hexPurchaseCost;
        c.initBudget = initBudget;
        c.initHp = initHp;
        c.turnBudget = turnBudget;
        c.maxBudget = maxBudget;
        c.interestPct = interestPct;
        c.maxTurns = maxTurns;
        c.maxSpawns = maxSpawns;
        return c;
    }
}