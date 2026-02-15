package parser;

public enum TokenType {
    // literals
    NUMBER,
    IDENT,

    // keywords
    IF,
    ELSE,
    WHILE,
    MOVE,
    SHOOT,
    DONE,

    // operators
    PLUS,      // +
    MINUS,     // -
    STAR,      // *
    SLASH,     // /
    MOD,       // %
    POW,       // ^

    ASSIGN,    // =
    LPAREN,    // (
    RPAREN,    // )
    LBRACE,    // {
    RBRACE,    // }
    SEMICOLON, // ;

    //New
    ALLY,
    OPPONENT,
    NEARBY,

    DIRECTION, // UP DOWN ...

    EOF
}
