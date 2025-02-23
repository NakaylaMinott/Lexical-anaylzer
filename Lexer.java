/*
lexer processes raw source code as a string of characters
breaks it down into tokens using data structures such as HashSet, HashMap, and ArrayList
to label each part according to its token type
*/

import java.util.*;

class Lexer {
    private String input;
    private int position; //character index
    private int line; // current line number

    // reserved keywords for quick lookup
    private static final HashSet<String> reservedWords = new HashSet<>(Arrays.asList(
            "float", "while", "if", "else", "return", "for", "break", "continue", "int", "double", "char"
    ));

    // map of operators to their corresponding token types
    private static final HashMap<String, String> operators = new HashMap<>();

    static {
        //operator token
        operators.put("=", "ASSIGNMENT_OPERATOR");
        operators.put("+", "PLUS_OPERATOR");
        operators.put("-", "MINUS_OPERATOR");
        operators.put("*", "MULTIPLY_OPERATOR");
        operators.put("/", "DIVIDE_OPERATOR");
        operators.put(">=", "GREATER_THAN_EQUAL_OPERATOR");
        operators.put(">", "GREATER_THAN_OPERATOR");
        operators.put("<=", "LESS_THAN_EQUAL_OPERATOR");
        operators.put("<", "LESS_THAN_OPERATOR");
    }

    //constructor
    public Lexer(String input) {
        this.input = input;
        this.position = 0;
        this.line = 1;
    }
    // return list of tokens
    public List<Token> analyze() {
        List<Token> tokens = new ArrayList<>();

        // loop through each character in the input
        while (position < input.length()) {
            char currentChar = input.charAt(position);
            //skip whitespace and track newlines
            if (Character.isWhitespace(currentChar)) {
                if (currentChar == '\n') line++;
                position++;
                continue;
            }
            // deals with the identifiers and reserved keywords
            if (Character.isLetter(currentChar)) {
                String word = readIdentifier();
                //check if word is a reserved keyword and adds to keyword or identifier
                if (reservedWords.contains(word)) {
                    tokens.add(new Token("KEYWORD", word, line));
                } else {
                    tokens.add(new Token("IDENTIFIER", word, line));
                }
                continue;
            }
            // deals with operators
            if (isOperator(currentChar)) {
                String operator = readOperator();
                String operatorType = operators.getOrDefault(operator, "OPERATOR");
                tokens.add(new Token(operatorType, operator, line));
                continue;
            }
            //deals with special characters
            if (isSpecialChar(currentChar)) {
                tokens.add(new Token("SPECIAL", String.valueOf(currentChar), line));
                position++;
                continue;
            }
            // deals with numbers
            if (Character.isDigit(currentChar)) {
                String number = readNumber();
                tokens.add(new Token("NUMBER", number, line));
                continue;
            }
            // error handling for characters that aren't recognized
            throw new RuntimeException("Invalid character: " + currentChar + " at line " + line);
        }

        return tokens;
    }
    // reads identifiers
    private String readIdentifier() {
        StringBuilder builder = new StringBuilder();
        while (position < input.length() &&
                (Character.isLetterOrDigit(input.charAt(position)) || input.charAt(position) == '_')) {
            builder.append(input.charAt(position));
            position++;
        }
        return builder.toString();
    }
    // reads numbers
    private String readNumber() {
        StringBuilder builder = new StringBuilder();
        while (position < input.length() && Character.isDigit(input.charAt(position))) {
            builder.append(input.charAt(position));
            position++;
        }
        return builder.toString();
    }
    //reads operators with multiple characters
    private String readOperator() {
        if (position + 1 < input.length()) {
            char nextChar = input.charAt(position + 1);
            String potentialOperator = "" + input.charAt(position) + nextChar;
            if (operators.containsKey(potentialOperator)) {
                position += 2;
                return potentialOperator;
            }
        }
        return String.valueOf(input.charAt(position++));
    }
    //checks if a character is an operator
    private boolean isOperator(char c) {
        return "=+-*/><".indexOf(c) != -1;
    }
    // checks if a character is a special character
    private boolean isSpecialChar(char c) {
        return "(){};,.".indexOf(c) != -1;
    }
}
