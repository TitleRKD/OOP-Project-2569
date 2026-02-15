package parser;

import java.util.ArrayList;
import java.util.List;

public class Tokenizer {

    private final String src;
    private int pos = 0;

    public Tokenizer(String src) {
        this.src = src;
    }

    public List<Token> tokenize() {
        List<Token> tokens = new ArrayList<>();

        while (!isAtEnd()) {
            char c = advance();

            switch (c) {
                case '+': tokens.add(new Token(TokenType.PLUS, "+")); break;
                case '-': tokens.add(new Token(TokenType.MINUS, "-")); break;
                case '*': tokens.add(new Token(TokenType.STAR, "*")); break;
                case '/': tokens.add(new Token(TokenType.SLASH, "/")); break;
                case '%': tokens.add(new Token(TokenType.MOD, "%")); break;
                case '^': tokens.add(new Token(TokenType.POW, "^")); break;
                case '=': tokens.add(new Token(TokenType.ASSIGN, "=")); break;
                case '(': tokens.add(new Token(TokenType.LPAREN, "(")); break;
                case ')': tokens.add(new Token(TokenType.RPAREN, ")")); break;
                case '{': tokens.add(new Token(TokenType.LBRACE, "{")); break;
                case '}': tokens.add(new Token(TokenType.RBRACE, "}")); break;
                case ';': tokens.add(new Token(TokenType.SEMICOLON, ";")); break;

                case ' ':
                case '\n':
                case '\t':
                case '\r':
                    break;

                default:
                    if (Character.isDigit(c)) {
                        tokens.add(number(c));
                    } else if (Character.isLetter(c)) {
                        tokens.add(identifier(c));
                    } else {
                        throw new RuntimeException("Unexpected character: " + c);
                    }
            }
        }

        tokens.add(new Token(TokenType.EOF, ""));
        return tokens;
    }

    private Token number(char first) {
        StringBuilder sb = new StringBuilder();
        sb.append(first);

        while (!isAtEnd() && Character.isDigit(peek())) {
            sb.append(advance());
        }
        return new Token(TokenType.NUMBER, sb.toString());
    }

    private Token identifier(char first) {
        StringBuilder sb = new StringBuilder();
        sb.append(first);

        while (!isAtEnd() && Character.isLetterOrDigit(peek())) {
            sb.append(advance());
        }

        String word = sb.toString();

        switch (word) {
            case "if": return new Token(TokenType.IF, word);
            case "else": return new Token(TokenType.ELSE, word);
            case "while": return new Token(TokenType.WHILE, word);
            case "move": return new Token(TokenType.MOVE, word);
            case "shoot": return new Token(TokenType.SHOOT, word);
            case "done": return new Token(TokenType.DONE, word);
            case "ally": return new Token(TokenType.ALLY, word);
            case "opponent": return new Token(TokenType.OPPONENT, word);
            case "nearby": return new Token(TokenType.NEARBY, word);
            case "up":
            case "down":
            case "upleft":
            case "upright":
            case "downleft":
            case "downright":
                return new Token(TokenType.DIRECTION, word);
            default:
                return new Token(TokenType.IDENT, word);
        }
    }

    private boolean isAtEnd() {
        return pos >= src.length();
    }

    private char advance() {
        return src.charAt(pos++);
    }

    private char peek() {
        return src.charAt(pos);
    }
}