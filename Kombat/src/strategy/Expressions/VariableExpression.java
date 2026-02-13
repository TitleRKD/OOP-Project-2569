package strategy.Expressions;

import strategy.Core.Expression;
import context.ExecutionContext;

public class VariableExpression extends Expression {

    private final String name;

    public VariableExpression(String name) {
        this.name = name;
    }

    @Override
    public long evaluate(ExecutionContext ctx) {
        return ctx.getVariable(name);
    }
}
