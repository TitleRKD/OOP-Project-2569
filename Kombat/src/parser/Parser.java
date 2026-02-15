package parser;

import enums.InfoType;
import strategy.Core.Strategy;
import strategy.Core.Statement;
import strategy.Core.Expression;
import strategy.Expressions.*;
import strategy.Statements.*;
import enums.Direction;
import enums.Operator;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int pos = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Strategy parseStrategy() {
        List<Statement> stmts = new ArrayList<>();
        while (!check(TokenType.EOF)) {
            stmts.add(parseStatement());
        }
        return new Strategy(stmts);
    }

    private Statement parseStatement() {
        if (match(TokenType.IF)) return parseIf();
        if (match(TokenType.WHILE)) return parseWhile();
        if (match(TokenType.MOVE)) return parseMove();
        if (match(TokenType.SHOOT)) return parseShoot();
        if (match(TokenType.DONE)) {
            consume(TokenType.SEMICOLON);
            return new DoneCommand();
        }
        if (match(TokenType.LBRACE)) return parseBlock();

        Token name = consume(TokenType.IDENT);
        consume(TokenType.ASSIGN);
        Expression expr = parseExpression();
        consume(TokenType.SEMICOLON);
        return new AssignmentStatement(name.lexeme, expr);
    }

    private Statement parseIf() {
        Expression cond = parseExpression();
        Statement thenStmt = parseStatement();
        Statement elseStmt = null;
        if (match(TokenType.ELSE)) {
            elseStmt = parseStatement();
        }
        return new IfStatement(cond, thenStmt, elseStmt);
    }

    private Statement parseWhile() {
        Expression cond = parseExpression();
        Statement body = parseStatement();
        return new WhileStatement(cond, body);
    }

    private Statement parseBlock() {
        List<Statement> stmts = new ArrayList<>();
        while (!check(TokenType.RBRACE)) {
            stmts.add(parseStatement());
        }
        consume(TokenType.RBRACE);
        return new BlockStatement(stmts);
    }

    private Statement parseMove() {
        Direction dir = Direction.valueOf(consume(TokenType.DIRECTION).lexeme.toUpperCase());
        consume(TokenType.SEMICOLON);
        return new MoveCommand(dir);
    }

    private Statement parseShoot() {
        Direction dir = Direction.valueOf(consume(TokenType.DIRECTION).lexeme.toUpperCase());
        Expression cost = parseExpression();
        consume(TokenType.SEMICOLON);
        return new ShootCommand(dir, cost);
    }

    private Expression parseExpression() {
        Expression expr = parseTerm();
        while (match(TokenType.PLUS, TokenType.MINUS)) {
            Operator op = previous().type == TokenType.PLUS ? Operator.ADD : Operator.SUB;
            Expression right = parseTerm();
            expr = new BinaryExpression(expr, right, op);
        }
        return expr;
    }

    private Expression parseTerm() {
        Expression expr = parseFactor();
        while (match(TokenType.STAR, TokenType.SLASH, TokenType.MOD)) {
            Operator op = switch (previous().type) {
                case STAR -> Operator.MUL;
                case SLASH -> Operator.DIV;
                default -> Operator.MOD;
            };
            Expression right = parseFactor();
            expr = new BinaryExpression(expr, right, op);
        }
        return expr;
    }

    private Expression parseFactor() {
        if (match(TokenType.NUMBER)) {
            return new LiteralExpression(Long.parseLong(previous().lexeme));
        }
        if (match(TokenType.IDENT)) {
            return new VariableExpression(previous().lexeme);
        }
        if (match(TokenType.ALLY)) {
            return new InfoExpression(InfoType.ALLY, null);
        }
        if (match(TokenType.OPPONENT)) {
            return new InfoExpression(InfoType.OPPONENT, null);
        }
        if (match(TokenType.NEARBY)) {
            Direction dir = Direction.valueOf(consume(TokenType.DIRECTION).lexeme.toUpperCase());
            return new InfoExpression(InfoType.NEARBY, dir);
        }
        if (match(TokenType.LPAREN)) {
            Expression expr = parseExpression();
            consume(TokenType.RPAREN);
            return expr;
        }
        throw new RuntimeException("Unexpected token: " + peek());
    }

    private boolean match(TokenType... types) {
        for (TokenType t : types) {
            if (check(t)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TokenType type) {
        if (check(type)) return advance();
        throw new RuntimeException("Expected " + type);
    }

    private boolean check(TokenType type) {
        return peek().type == type;
    }

    private Token advance() {
        return tokens.get(pos++);
    }

    private Token previous() {
        return tokens.get(pos - 1);
    }

    private Token peek() {
        return tokens.get(pos);
    }
}