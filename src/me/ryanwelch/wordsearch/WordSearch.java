package me.ryanwelch.wordsearch;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright 2015 (C) Ryan Welch
 *
 * @author Ryan Welch
 */
public class WordSearch {

    private static final Logger log = Logger.getLogger( WordSearch.class.getName() );

    // https://s-media-cache-ak0.pinimg.com/736x/1a/4c/ea/1a4cea8e24fd3b4308783cbaf54dd1e5.jpg
    private final String TEST_GRID_FILE = "wordsearch2.txt";
    private final String TEST_WORD_FILE = "wordsearch2_words.txt";
    public static boolean DEBUG = false;
    public static boolean ADV_SEARCH = false; // Flag to apply extra parsing to words, used when brute-force finding words

    public static void main(String[] args) {
        // Parse command line arguments
        if(args.length > 0 && (args[0].equalsIgnoreCase("-dev"))) {
            DEBUG = true;
        } else if(args.length < 2) {
            System.out.println("Usage: WordSearch [grid file] [word file]");
            return;
        }

        //ADV_SEARCH = true;

        new WordSearch(args);
    }

    public WordSearch(String[] args) {

        Solver solver;

        Handler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel( Level.FINER );
        log.addHandler( consoleHandler );

        log.setLevel( Level.ALL );
        // Stop logging by default handler
        log.setUseParentHandlers( false );

        if(DEBUG) {
            // If debug mode use test files
            consoleHandler.setLevel( Level.INFO );
            solver = getSolver(TEST_GRID_FILE, TEST_WORD_FILE);
        } else {
            consoleHandler.setLevel( Level.INFO );
            solver = getSolver(args[0], args[1]);
        }

        if(solver == null) {
            return;
        }

        final long startTime = System.currentTimeMillis();
        List<Word> res = solver.solve();
        final long endTime = System.currentTimeMillis();

        log.log(Level.INFO, String.format("Found %d out of %d words", res.size(), solver.getWordCount()));
        log.log(Level.INFO, String.format("Finished in: %dms", endTime - startTime));
    }

    /**
     * Load solver for wordsearch
     * @param gridFile FIle name for grid
     * @param wordFile File name for word file
     * @return solver instance
     */
    public Solver getSolver(String gridFile, String wordFile) {
        String gridString = Util.readFile(gridFile);
        String wordString = Util.readFile(wordFile);

        if(wordString == null || gridString == null) {
            return null;
        }

        String[] words = wordString
                .trim()
                .replaceAll("[\r\n]+", " ") // Replace new lines with spaces
                .replaceAll("[^A-Za-z ]", " ") // Replace non alphanumeric characters with spaces
                .replaceAll("( )+", " ") // Replace multiple spaces with one space
                // Regex for repeating words based on http://stackoverflow.com/a/6790786
                .replaceAll("(\\b\\w+\\b) (?=.*\\b\\1\\b)", "")  // Use regex lookahead to remove repeating words
                .toLowerCase()
                .split("[\\W]");

        Solver solver;
        try {
            Grid grid = new Grid(gridString);

            solver = new Solver(grid, words);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to load grid");
            return null;
        }

        return solver;
    }
}
