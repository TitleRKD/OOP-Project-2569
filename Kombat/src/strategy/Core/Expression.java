package strategy.Core;

import context.ExecutionContext;

public abstract class Expression {
    public abstract long evaluate(ExecutionContext context);
}
