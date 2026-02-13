package strategy.Statements;

import context.ExecutionContext;
import strategy.Core.Expression;
import enums.Direction;

public class ShootCommand extends ActionCommand {

    private final Direction direction;
    private final Expression expenditure;

    public ShootCommand(Direction direction, Expression expenditure) {
        this.direction = direction;
        this.expenditure = expenditure;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        long cost = expenditure.evaluate(ctx);
        ctx.getMinion().shoot(direction, cost);
    }
}
