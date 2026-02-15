package player;

import enums.Direction;
import game.Board;
import game.Hex;
import strategy.Core.Strategy;
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

    public void move(Direction dir) {
        Player player = owner;
        Board board = player.getGame().getBoard();
        if (!player.spendBudget(1)) {
            return; // not enough budget – turn ends (handled by caller)
        }
        Hex current = board.getHex(row, col);
        Hex target = board.getHexDirection(current, dir);
        if (target != null && !target.hasMinion()) {
            board.removeMinion(this);
            board.placeMinion(this, target.getRow(), target.getCol());
        }
        // if invalid, no move but budget already spent
    }

    public void shoot(Direction dir, long expenditure) {
        Player player = owner;
        Board board = player.getGame().getBoard();
        long totalCost = expenditure + 1;
        if (!player.spendBudget(totalCost)) {
            return;
        }
        Hex current = board.getHex(row, col);
        Hex target = board.getHexDirection(current, dir);
        if (target == null || !target.hasMinion()) {
            return; // nothing to shoot
        }
        Minion victim = target.getMinion();
        long defense = victim.type.getDefenseFactor();
        long damage = Math.max(1, expenditure - defense);
        victim.takeDamage(damage);
        if (!victim.isAlive()) {
            board.removeMinion(victim);
            victim.getOwner().getMinions().remove(victim); // remove from owner's list
        }
    }

    public void setPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() { return row; }
    public int getCol() { return col; }
    public Player getOwner() { return owner; }
    public long getHp() { return hp; }
    public MinionType getType() { return type; }

    public void takeDamage(long dmg) {
        hp = Math.max(0, hp - dmg);
    }

    public boolean isAlive() {
        return hp > 0;
    }
}