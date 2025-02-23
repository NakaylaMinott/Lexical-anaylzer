/*
this program reads source code.
performs lexical analysis using the lexer class and prints the generated tokens
it then uses recursive parsing to analyze the grammatical structure of the source code
*/

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        try {
            // Read the source code from a file
            String sourceCode = new String(Files.readAllBytes(Paths.get("source1.txt")));

            // Create lexer and analyze the code
            Lexer Lexer = new Lexer(sourceCode);
            List<Token> tokens = Lexer.analyze();

            // Print all tokens
            System.out.println("Lexical Analysis Results:");
            for (Token token : tokens) {
                System.out.println(token);
            }

            //pass tokens to the parser
            Parser parser = new Parser(tokens);
            parser.parse();

        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error during lexical analysis: " + e.getMessage());
        }

    }
}
