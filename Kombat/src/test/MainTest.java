package test;

import enums.Direction;
import game.Board;
import game.Hex;
import player.Minion;
import player.Player;

import java.util.List;

public class MainTest {
    public static void main(String[] args) {

//        GameConfig gameConfig = GameConfig.getInstance();
//        gameConfig.loadConfig("Kombat/src/config/Config.txt");
//        Config config = gameConfig.toConfig();
//        Game game = new Game(config, p1, p2, GameMode.SOLITAIRE);
//        game.showConfig();

        Player p1 = new Player("P1", false);
        Player p2 = new Player("P2", false);
        Minion minion1 = new Minion(p1,100);
        Minion minion2 = new Minion(p2,100);

        Board board = new Board(8,8);
        Hex hex = board.getHex(2,3);

//        board.placeMinion(minion1,1,1);
//        board.placeMinion(minion2,1,1);
//        System.out.println(hex.getMinion());
//        System.out.println(minion1.getOwner());
//        System.out.println(minion2.getOwner());

        System.out.println(hex.getRow() + "," + hex.getCol());
        System.out.println(board.getAdjacentHexes(board.getHex(2,3)));
        List<Hex> ad = board.getAdjacentHexes(board.getHex(2,3));
        for ( int i = 0; i < ad.size(); i++ ) {
            Hex h = ad.get(i);
            System.out.println(h.getRow() + "," + h.getCol());
        }
//        board.printBoard();
    }
}
