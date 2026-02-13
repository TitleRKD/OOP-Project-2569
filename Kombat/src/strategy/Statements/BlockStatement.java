package strategy.Statements;

import strategy.Core.Statement;
import context.ExecutionContext;
import java.util.List;

public class BlockStatement extends Statement {

    private final List<Statement> statements;

    public BlockStatement(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        for (Statement s : statements) {
            if (ctx.isDone()) break;
            s.execute(ctx);
        }
    }
}
