package strategy.Expressions;

import strategy.Core.Expression;
import context.ExecutionContext;
import enums.Operator;

public class BinaryExpression extends Expression {

    private final Expression left;
    private final Expression right;
    private final Operator op;

    public BinaryExpression(Expression left, Expression right, Operator op) {
        this.left = left;
        this.right = right;
        this.op = op;
    }

    @Override
    public long evaluate(ExecutionContext ctx) {
        long l = left.evaluate(ctx);
        long r = right.evaluate(ctx);

        switch (op) {
            case ADD: return l + r;
            case SUB: return l - r;
            case MUL: return l * r;
            case DIV:
                if (r == 0) {
                    ctx.markDone();
                    return 0;
                }
                return l / r;
            case MOD: return l % r;
            case POW: return (long) Math.pow(l, r);
            default: return 0;
        }
    }
}
