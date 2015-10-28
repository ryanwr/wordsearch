package me.ryanwelch.wordsearch;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright 2015 (C) Ryan Welch
 *
 * @author Ryan Welch
 */
public class Grid {

    private static final Logger log = Logger.getLogger( WordSearch.class.getName() );

    private char[][] grid;
    private int width;
    private int height;

    public Grid(String data) throws Exception {
        parseString(data);
    }

    /**
     * Parses data in string format into the grid array
     * @param data The grid in string format
     * @throws Exception
     */
    public void parseString(String data) throws Exception {
        // Sanitize data
        data = data
                .trim()
                .replaceAll("[\r\n]+", "\n")
                .replaceAll(" ", "")
                .toLowerCase();

        String lines[] = data.split("\\n");

        height = lines.length;
        width = Util.getLongestString(lines);

        grid = new char[height][width];

        String line;

        for(int i = 0; i < height; i++) {
            line = lines[i];

            log.log(Level.FINER, "Parsing line {0}", i);
            log.log(Level.FINEST, "{0}", line);

            if(line.length() < width) {
                log.log( Level.SEVERE, "Line {0} is too short", i );
                throw new Exception("Cannot make grid");
            }

            for(int j = 0; j < width; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
    }

    /**
     * The height of the grid
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * The width of the grid
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the character at position x and y in the grid
     * @param x
     * @param y
     * @return character
     */
    public char getCharacter(int x, int y) {
        if(x >= 0 && x < width && y >= 0 && y < height) {
            return grid[y][x];
        }
        return '\0';
    }

}
