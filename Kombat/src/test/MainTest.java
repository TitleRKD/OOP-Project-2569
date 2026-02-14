package test;

import config.Config;
import config.GameConfig;
import context.GameContext;
import enums.Direction;
import enums.GameMode;
import game.Board;
import game.Game;
import game.Hex;
import player.Minion;
import player.Player;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {


        GameConfig gameConfig = GameConfig.getInstance();
        gameConfig.loadConfig("Kombat/src/config/Config.txt");
        Config config = gameConfig.toConfig();
        Board board = new Board(8,8);
        Player p1 = new Player("P1", false);
        Player p2 = new Player("P2", false);

        Game game = new Game(config, p1, p2, GameMode.SOLITAIRE);
        //game.showConfig();

        GameContext context = new GameContext(game, board,p1);

        Minion minion1 = new Minion(p1,100);
        Minion minion2 = new Minion(p1,100);
        Minion minion3 = new Minion(p1,100);
        System.out.println(p1.getMinions());

        Hex hex = board.getHex(1,1);

//        board.placeMinion(minion1,1,1);
//        board.placeMinion(minion2,1,1);
//        System.out.println(hex.getMinion());
//        System.out.println(minion1.getRow()+" "+minion1.getCol());
//        System.out.println(minion1.getOwner());
//        System.out.println(minion2.getOwner());

//        System.out.println(hex.getRow() + "," + hex.getCol());
//        System.out.println(board.getAdjacentHexes(board.getHex(2,3)));
//        List<Hex> ad = board.getAdjacentHexes(board.getHex(2,3));
//        for ( int i = 0; i < ad.size(); i++ ) {
//            Hex h = ad.get(i);
//            System.out.println(h.getRow() + "," + h.getCol());
//        }
//        board.printBoard();
//        minion1.move(Direction.UPRIGHT, context);
//        System.out.println("__________________________________");
//        board.printBoard();
//        System.out.println(minion1.getRow()+" "+minion1.getCol());
    }
}
