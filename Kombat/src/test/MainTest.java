package test;

import config.Config;
import config.GameConfig;
import enums.GameMode;
import game.Board;
import game.Game;
import game.Hex;
import parser.Parser;
import parser.Tokenizer;
import player.Minion;
import player.MinionType;
import player.Player;
import strategy.Core.Strategy;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {

        // โหลด Config
        GameConfig gameConfig = GameConfig.getInstance();
        gameConfig.loadConfig("Kombat/src/config/Config.txt");
        Config config = gameConfig.toConfig();
        // สร้างผู้เล่นสองคน
        Player p1 = new Player("P1", false);
        Player p2 = new Player("P2", false);
        Game game = new Game(config, p1, p2, GameMode.SOLITAIRE);
        game.start();
        game.showConfig();

        // Create real strategies
        String code1 = "move up; done;";
        Strategy strat1 = new Parser(new Tokenizer(code1).tokenize()).parseStrategy(); // คำสั่ง: เลื่อนขึ้น
        String code2 = "shoot down 10; done;";
        Strategy strat2 = new Parser(new Tokenizer(code2).tokenize()).parseStrategy(); // คำสั่ง: ยิงลงด้วยเงิน 10

        // Create MinionTypes with these strategies
        MinionType type1 = new MinionType("Mover", 2, strat1); // ป้องกัน 2
        MinionType type2 = new MinionType("Shooter", 3, strat2); // ป้องกัน 3

        // Create minions at valid owned hexes
        Minion minion1 = new Minion(type1, p1, 1, 1, config.initHp);
        Minion minion2 = new Minion(type2, p2, 8, 8, config.initHp);

        Board board = game.getBoard(); // use game's board

        board.placeMinion(minion1, 1, 1); // อาณาเขต P1
        board.placeMinion(minion2, 8, 8); // อาณาเขต P2

        // Add minions to players' lists (ปกติ spawnMinion จะทำส่วนนี้ให้)
        p1.getMinions().add(minion1);
        p2.getMinions().add(minion2);

        // Test board info
        Hex hex = board.getHex(2, 3);
        System.out.println("Hex (2,3) minion: " + hex.getMinion());
        System.out.println("Minion1 owner: " + minion1.getOwner().getName());
        System.out.println("Minion2 owner: " + minion2.getOwner().getName());

        System.out.println("Hex (2,3) row,col: " + hex.getRow() + "," + hex.getCol());
        System.out.println("Adjacent hexes to (2,3):");
        List<Hex> ad = board.getAdjacentHexes(board.getHex(2,3));
        for (Hex h : ad) {
            System.out.println(h.getRow() + "," + h.getCol());
        }

        System.out.println("\nBoard before turns:");
        board.printBoard();

        // เทิร์นของผู้เล่น P1
        System.out.println("\n--- P1's turn ---");
        game.nextTurn(); // เพิ่มงบ, คำนวณดอกเบี้ย, และรันกลยุทธ์ของมินเนี่ยน P1

        System.out.println("After turn, minion1 at: (" + minion1.getRow() + "," + minion1.getCol() + ")");
        System.out.println("P1 budget: " + p1.getBudget());

        System.out.println("\nBoard after p1's turn:");
        board.printBoard();

        // เทิร์นของผู้เล่น P2
        System.out.println("\n--- P2's turn ---");
        game.nextTurn();

        System.out.println("After turn, minion2 at: (" + minion2.getRow() + "," + minion2.getCol() + ")");
        System.out.println("P2 budget: " + p2.getBudget());

        System.out.println("\nBoard after p2's turn:");
        board.printBoard();
    }
}