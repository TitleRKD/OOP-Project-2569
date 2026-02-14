package test;
import static org.junit.jupiter.api.Assertions.*;

import game.Board;
import org.junit.jupiter.api.Test;

public class BoardTest {
    Board board = new Board(8,8);

    @Test
    void HexnotinBoard() throws Exception {
        assertEquals(null, board.getHex(0,0));
    }// test บวกเลข

    @Test
    void HexisinBoard() throws Exception {
        assertEquals(null, board.getHex(1,1));
    }
    @Test
    void adjacentHex() throws Exception {
        assertEquals(null, board.getHex(0,0));
    }// test บวกเลข
}


