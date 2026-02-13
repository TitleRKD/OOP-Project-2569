package player;

import strategy.Core.Strategy;
import enums.Direction;
import context.GameContext;

public class Minion {

    private final MinionType type;
    private final Player owner;
    private int row, col;
    private long hp;

    public Minion(MinionType type, Player owner, int row, int col, long hp) {
        this.type = type;
        this.owner = owner;
        this.row = row;
        this.col = col;
        this.hp = hp;
    }

    public void executeStrategy(GameContext ctx) {
        type.getStrategy().execute(this, ctx);
    }

    public void move(Direction dir) {}
    public void shoot(Direction dir, long expenditure) {}

    public int getRow() { return row; }
    public int getCol() { return col; }
    public long getHp() { return hp; }

    public void takeDamage(long dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
