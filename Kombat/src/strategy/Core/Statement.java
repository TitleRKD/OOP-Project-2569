package strategy.Core;

import context.ExecutionContext;

public abstract class Statement {
    public abstract void execute(ExecutionContext context);
}
