package strategy.Expressions;

import strategy.Core.Expression;
import context.ExecutionContext;
import enums.InfoType;
import enums.Direction;

public class InfoExpression extends Expression {

    private final InfoType type;
    private final Direction direction;

    public InfoExpression(InfoType type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }

    @Override
    public long evaluate(ExecutionContext ctx) {
        return ctx.getGameContext()
                .evaluateInfoExpression(ctx.getMinion(), type, direction);
    }
}
