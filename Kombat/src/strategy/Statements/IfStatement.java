package strategy.Statements;

import strategy.Core.Statement;
import strategy.Core.Expression;
import context.ExecutionContext;

public class IfStatement extends Statement {

    private final Expression condition;
    private final Statement thenStmt;
    private final Statement elseStmt;

    public IfStatement(Expression condition, Statement thenStmt, Statement elseStmt) {
        this.condition = condition;
        this.thenStmt = thenStmt;
        this.elseStmt = elseStmt;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        if (condition.evaluate(ctx) > 0) {
            thenStmt.execute(ctx);
        } else if (elseStmt != null) {
            elseStmt.execute(ctx);
        }
    }
}
