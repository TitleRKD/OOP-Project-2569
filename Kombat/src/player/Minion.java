package player;

import game.Board;
import game.Hex;
import strategy.Core.Strategy;
import enums.Direction;
import context.GameContext;

public class Minion {

    //private final MinionType type;
    private final Player owner;
    private int row, col;
    private long hp;

    public Minion(Player owner, long hp) {
        //this.type = type;
        this.owner = owner;
        this.hp = hp;
    }

//    public Minion(MinionType type, Player owner, int row, int col, long hp) {
//        this.type = type;
//        this.owner = owner;
//        this.row = row;
//        this.col = col;
//        this.hp = hp;
//    }

//    public void executeStrategy(GameContext ctx) {
//        type.getStrategy().execute(this, ctx);
//    }

    public void move(Direction direction,GameContext context) {
        Board board = context.getBoard(); //เอาข้อมูล board จาก context
        Hex current = board.getHex(row, col);
        Hex next = board.getHexDirection(current, direction);

        if (next == null){ return; }       // ออกนอกกระดาน
        if (next.hasMinion()) { return; }  // ช่องเต็ม

        // remove จากช่องเก่า
        board.removeMinion(this);

        // update position
        this.row = next.getRow();
        this.col = next.getCol();

        // place ช่องใหม่
        board.placeMinion(this, row, col);
    }
    public void shoot(Direction dir, long expenditure) {

    }
    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public Player getOwner() { return owner; }
    public long getHp() { return hp; }

    public void takeDamage(long dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
