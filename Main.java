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
        analyzeFile("source1.txt");
        analyzeFile("source2.txt");
    }

    // Method to read, tokenize, and parse a file
    private static void analyzeFile(String filename) {
        try {
            // Read the source code from a file
            String sourceCode = new String(Files.readAllBytes(Paths.get(filename)));

            // Create lexer and analyze the code
            Lexer lexer = new Lexer(sourceCode);
            List<Token> tokens = lexer.analyze();

            // Print all tokens
            System.out.println("\n🔍 Lexical Analysis Results for: " + filename);
            for (Token token : tokens) {
                System.out.println(token);
            }

            // Pass tokens to the parser
            Parser parser = new Parser(tokens);
            parser.parse();

        } catch (IOException e) {
            System.err.println(" Error reading file " + filename + ": " + e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Error during lexical analysis or parsing for " + filename + ": " + e.getMessage());
        }
    }
}
