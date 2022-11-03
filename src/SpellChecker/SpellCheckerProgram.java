package SpellChecker;

import SpellChecker.src.SpellChecker;

import java.io.IOException;
import java.util.HashSet;
import java.util.Scanner;

public class SpellCheckerProgram {

    // This runs the interactive program that users
    // can use to run our code
    public static void main(String[] args) throws IOException {
        // Get stuff set up
        SpellChecker speller = new SpellChecker();
        speller.load("src/SpellChecker/dictionaries/words.txt");
        Scanner scan = new Scanner(System.in);

        HashSet<String> correctCommands = new HashSet<>();
        correctCommands.add("spellcheck");
        correctCommands.add("autocomplete");
        correctCommands.add("suggest");
        correctCommands.add("exit");

        while (true) { // Keep repeating until the user wants to exit
            String command;

            // Determine which functionality the user wants to carry out
            while (true) {
                System.out.println("What functionality would you like to run? (spellcheck/autocomplete/suggest/exit)");
                command = scan.nextLine();

                if(correctCommands.contains(command)) {
                    break;
                } else {
                    boolean wasClose = false;
                    for(String validCommand: correctCommands){
                        if(speller.levenshteinDistance(command, validCommand)<=2){
                            System.out.println("Did you mean " + validCommand + "? (y/n)");
                            String suggestCorrect = scan.nextLine();
                            while(!suggestCorrect.equals("y")&&!suggestCorrect.equals("n")){
                                System.out.println("Not a valid response. \nDid you mean " + validCommand + "? (y/n)");
                                suggestCorrect = scan.nextLine();
                            }
                            if(suggestCorrect.equals("y")){
                                command=validCommand;
                                wasClose=true;
                            }
                            break;
                        }
                    }
                    if(wasClose){
                        break;
                    }
                    System.out.println("Invalid command. Please try again.");
                }
            }

            if(command.equals("exit")) {
                break;
            }
            switch (command) {
                case "spellcheck" -> {
                    System.out.println("Would you like to spellcheck a word or a document? (word/document)");
                    String spellcheckType = scan.nextLine();

                    if(speller.levenshteinDistance(spellcheckType, "word")<=2){
                        System.out.println("Did you mean word? (y/n)");
                        String suggestCorrect = scan.nextLine();
                        while(!suggestCorrect.equals("y")&&!suggestCorrect.equals("n")){
                            System.out.println("Not a valid response. \nDid you mean word? (y/n)");
                            suggestCorrect = scan.nextLine();
                        }
                        if(suggestCorrect.equals("y")){
                            spellcheckType="word";
                        }
                    }

                    if(speller.levenshteinDistance(spellcheckType, "document")<=2){
                        System.out.println("Did you mean document? (y/n)");
                        String suggestCorrect = scan.nextLine();
                        while(!suggestCorrect.equals("y")&&!suggestCorrect.equals("n")){
                            System.out.println("Not a valid response. \nDid you mean document? (y/n)");
                            suggestCorrect = scan.nextLine();
                        }
                        if(suggestCorrect.equals("y")){
                            spellcheckType="document";
                        }
                    }

                    if (spellcheckType.equals("document")) {
                        System.out.println("What is the file location of the document?");
                        String document = scan.nextLine();
                        speller.check(document);
                    } else if (spellcheckType.equals("word")) {
                        System.out.println("Enter the word");
                        String word = scan.nextLine();
                        boolean isValid = speller.checkWord(word);
                        System.out.println("This is a " + (isValid ? "valid" : "misspelled") + " word");
                    }
                }
                case "autocomplete" -> {
                    System.out.println("Enter the word");
                    String word = scan.nextLine();
                    speller.autocomplete(word);
                }
                case "suggest" -> {
                    System.out.println("Enter the word");
                    String word = scan.nextLine();
                    speller.suggest(word);
                }
            }
        }
    }
}
