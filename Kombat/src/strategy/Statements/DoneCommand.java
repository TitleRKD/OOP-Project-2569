package strategy.Statements;

import context.ExecutionContext;

public class DoneCommand extends ActionCommand {

    @Override
    public void execute(ExecutionContext ctx) {
        ctx.markDone();
    }
}
