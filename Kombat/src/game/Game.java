package game;

import config.Config;
import enums.GameMode;
import player.Player;

public class Game {

    private final Config config;
    private final Player p1;
    private final Player p2;
    private final GameMode mode;
    private int turn = 0;

    public Game(Config config, Player p1, Player p2, GameMode mode) {
        this.config = config;
        this.p1 = p1;
        this.p2 = p2;
        this.mode = mode;
    }

    public void start() {
        turn = 1;
    }

    public void nextTurn() {
        turn++;
    }

    public boolean isGameOver() {
        return false;
    }

    public Player getWinner() {
        return null;
    }

    public Config getConfig() {
        return config;
    }
}
