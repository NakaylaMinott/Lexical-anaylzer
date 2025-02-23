/*
* token class for the lexical tokens
* breaks down input source code into tokens
* each token contains info about the type of lexeme, its value and its position in the source code
*/


public class Token {
    private String type; // stores the category of the token keyword, id, operator
    private String value;// stores the actual lexeme (float, data, =)
    private int line; // stores the line number

    //constructor  initializes token objects
    public Token(String type, String value, int line){
        this.type = type;
        this.value = value;
        this.line = line;
    }
    //getter methods allow other parts of the program to get token data
    public String getValue(){return value;}
    public int getLine(){return line;}

    //overrides javas toString method to properly represent tokens
    public String toString(){
        return "Token{type='" + type + "', value='" + value + "', line=" + line + "}";
    }
}
