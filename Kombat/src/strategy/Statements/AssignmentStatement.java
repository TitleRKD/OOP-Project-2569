package strategy.Statements;

import strategy.Core.Statement;
import strategy.Core.Expression;
import context.ExecutionContext;

public class AssignmentStatement extends Statement {

    private final String name;
    private final Expression expr;

    public AssignmentStatement(String name, Expression expr) {
        this.name = name;
        this.expr = expr;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        long value = expr.evaluate(ctx);
        ctx.setVariable(name, value);
    }
}
