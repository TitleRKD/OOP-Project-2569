package test;

import config.Config;
import context.GameContext;
import enums.Direction;
import enums.GameMode;
import enums.InfoType;
import game.Board;
import game.Game;
import game.Hex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.Parser;
import parser.Tokenizer;
import player.Minion;
import player.MinionType;
import player.Player;
import strategy.Core.Strategy;

import static org.junit.jupiter.api.Assertions.*;

public class StrategyTest {
    private Game game;
    private Board board;
    private Player p1;
    private Player p2;
    private Config config;

    @BeforeEach
    void setUp() {
        // Create config with test values (no file needed)
        config = new Config();
        config.spawnCost = 10;
        config.hexPurchaseCost = 100;
        config.initBudget = 200;
        config.initHp = 100;
        config.turnBudget = 20;
        config.maxBudget = 500;
        config.interestPct = 5;
        config.maxTurns = 100;
        config.maxSpawns = 10;

        p1 = new Player("P1", false);
        p2 = new Player("P2", false);
        game = new Game(config, p1, p2, GameMode.SOLITAIRE);
        board = game.getBoard();
        game.start(); // sets budgets and initial owned hexes (only (1,1) and (8,8))
    }

    @Test
    void testMoveCommand() {
        String code = "move up; done;";
        Strategy strat = new Parser(new Tokenizer(code).tokenize()).parseStrategy();
        MinionType type = new MinionType("Mover", 1, strat);

        Hex hex = board.getHex(5, 5);
        p1.getOwnedHexes().add(hex); // make it spawnable
        p1.spawnMinion(type, hex);
        Minion minion = p1.getMinions().get(0);
        assertEquals(5, minion.getRow());
        assertEquals(5, minion.getCol());

        GameContext ctx = new GameContext(game, board, p1);
        minion.executeStrategy(ctx);

        // After move up, should be at (4,5)
        assertEquals(4, minion.getRow());
        assertEquals(5, minion.getCol());
        // Budget: init 200 - spawn 10 - move 1 = 189
        assertEquals(200 - 10 - 1, p1.getBudget());
    }

    @Test
    void testShootCommand() {
        String code = "shoot down 50; done;";
        Strategy strat = new Parser(new Tokenizer(code).tokenize()).parseStrategy();
        MinionType shooterType = new MinionType("Shooter", 2, strat);
        MinionType targetType = new MinionType("Target", 3, null);

        Hex hexShooter = board.getHex(5, 5);
        p1.getOwnedHexes().add(hexShooter);
        p1.spawnMinion(shooterType, hexShooter);
        Minion shooter = p1.getMinions().get(0);

        Hex hexTarget = board.getHex(6, 5);
        p2.getOwnedHexes().add(hexTarget);
        p2.spawnMinion(targetType, hexTarget);
        Minion target = p2.getMinions().get(0);
        long initialHp = target.getHp();

        // Give extra budget to p1 to afford shoot
        p1.addBudget(100); // now budget = 200 - 10 (spawn) + 100 = 290
        // Shoot cost = 50 + 1 = 51
        GameContext ctx = new GameContext(game, board, p1);
        shooter.executeStrategy(ctx);

        // Damage = max(1, 50 - 3) = 47
        assertEquals(initialHp - 47, target.getHp());
        assertEquals(290 - 51, p1.getBudget());
    }

    @Test
    void testInfoExpressions() {
        // Test nearby function (indirectly via evaluateInfoExpression)
        String code = "z = nearby up; done;";
        Strategy strat = new Parser(new Tokenizer(code).tokenize()).parseStrategy();
        MinionType scoutType = new MinionType("Scout", 1, strat);
        MinionType enemyType = new MinionType("Enemy", 5, null);

        Hex hexScout = board.getHex(5, 5);
        p1.getOwnedHexes().add(hexScout);
        p1.spawnMinion(scoutType, hexScout);
        Minion scout = p1.getMinions().get(0);

        Hex hexEnemy = board.getHex(3, 5);
        p2.getOwnedHexes().add(hexEnemy);
        p2.spawnMinion(enemyType, hexEnemy);

        GameContext ctx = new GameContext(game, board, p1);
        // Directly test the info evaluation
        long result = ctx.evaluateInfoExpression(scout, InfoType.NEARBY, Direction.UP);
        // Enemy HP = 100 (initHp), defense = 5, distance = 2
        // HP digits = 3, defense digits = 1, value = 100*3 + 10*1 + 2 = 312
        assertEquals(312, result);
    }

    @Test
    void testWhileLoop() {
        // Uses global variable X (uppercase) to check after execution
        String code = "X = 0; while (5 - X) { X = X + 1; } done;";
        Strategy strat = new Parser(new Tokenizer(code).tokenize()).parseStrategy();
        MinionType type = new MinionType("Looper", 1, strat);

        Hex hex = board.getHex(1, 1);
        p1.getOwnedHexes().add(hex);
        p1.spawnMinion(type, hex);
        Minion looper = p1.getMinions().get(0);

        GameContext ctx = new GameContext(game, board, p1);
        looper.executeStrategy(ctx);

        // X is global, so accessible from player
        assertEquals(5, p1.getGlobalVar("X"));
    }
}