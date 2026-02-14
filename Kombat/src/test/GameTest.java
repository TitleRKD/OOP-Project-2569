package test;

import config.Config;
import config.GameConfig;
import enums.GameMode;
import game.Game;
import player.Player;

public class GameTest {

    public static void main(String[] args) {

        GameConfig gameConfig = GameConfig.getInstance();
        gameConfig.loadConfig("Kombat/src/config/Config.txt");
        Config config = gameConfig.toConfig();

        Player p1 = new Player("P1", false);
        Player p2 = new Player("P2", false);

        Game game = new Game(config, p1, p2, GameMode.SOLITAIRE);
        game.getConfig().showConfig();
    }
}
