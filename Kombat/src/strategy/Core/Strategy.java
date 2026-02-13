package strategy.Core;

import java.util.List;
import context.ExecutionContext;
import context.GameContext;
import player.Minion;

public class Strategy {

    private final List<Statement> statements;

    public Strategy(List<Statement> statements) {
        this.statements = statements;
    }

    public void execute(Minion minion, GameContext gameContext) {
        ExecutionContext ctx = new ExecutionContext(minion, gameContext);
        for (Statement stmt : statements) {
            if (ctx.isDone()) break;
            stmt.execute(ctx);
        }
    }
}
