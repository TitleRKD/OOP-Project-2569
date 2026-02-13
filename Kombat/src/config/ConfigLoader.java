package config;

import java.io.*;
import java.util.Properties;

public class ConfigLoader {

    public static Config load(String path) throws IOException {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
        }

        Config c = new Config();
        c.spawnCost = Long.parseLong(props.getProperty("spawn_cost"));
        c.hexPurchaseCost = Long.parseLong(props.getProperty("hex_purchase_cost"));
        c.initBudget = Long.parseLong(props.getProperty("init_budget"));
        c.initHp = Long.parseLong(props.getProperty("init_hp"));
        c.turnBudget = Long.parseLong(props.getProperty("turn_budget"));
        c.maxBudget = Long.parseLong(props.getProperty("max_budget"));
        c.interestPct = Long.parseLong(props.getProperty("interest_pct"));
        c.maxTurns = Long.parseLong(props.getProperty("max_turns"));
        c.maxSpawns = Long.parseLong(props.getProperty("max_spawns"));
        return c;
    }
}
