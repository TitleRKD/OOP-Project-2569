package strategy.Statements;

import context.ExecutionContext;
import enums.Direction;

public class MoveCommand extends ActionCommand {

    private final Direction direction;

    public MoveCommand(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void execute(ExecutionContext ctx) {
        ctx.getMinion().move(direction);
    }
}
