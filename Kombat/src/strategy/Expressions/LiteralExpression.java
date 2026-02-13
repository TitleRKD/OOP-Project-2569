package strategy.Expressions;

import strategy.Core.Expression;
import context.ExecutionContext;

public class LiteralExpression extends Expression {

    private final long value;

    public LiteralExpression(long value) {
        this.value = value;
    }

    @Override
    public long evaluate(ExecutionContext ctx) {
        return value;
    }
}
