package strategy.Statements;

import strategy.Core.Statement;
import strategy.Core.Expression;
import context.ExecutionContext;

public class WhileStatement extends Statement {

    private final Expression condition;
    private final Statement body;

    public WhileStatement(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        int count = 0;
        while (condition.evaluate(ctx) > 0 && !ctx.isDone() && count < 10000) {
            body.execute(ctx);
            count++;
        }
    }
}
