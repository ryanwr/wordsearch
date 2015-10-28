package me.ryanwelch.wordsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Copyright 2015 (C) Ryan Welch
 *
 * @author Ryan Welch
 */
public class Solver {

    private static final Logger log = Logger.getLogger( WordSearch.class.getName() );

    private Grid grid;
    private String[] words;

    private Direction currentDirection;
    //private int nextWord = 0;

    public Solver(Grid grid, String[] words) {
        this.grid = grid;
        this.words = words;
    }

    /**
     * Check if a word actually exists at a specified position in the grid
     *
     * <p>The method moves in the direction given using recursion to make sure all characters
     * in that direction match the word given</p>
     * @param word
     * @param x
     * @param y
     * @param dir
     * @param position
     * @return word exists
     */
    private boolean checkBranch(char[] word, int x, int y, Direction dir, int position) {
        // We somehow got to the end of the word so the branch must be true
        if (position > word.length - 1) {
            return true;
        }

        int newX = x;
        int newY = y;

        switch(dir) {
            case NORTH:
                newY = y - 1;
                break;
            case EAST:
                newX = x + 1;
                break;
            case SOUTH:
                newY = y + 1;
                break;
            case WEST:
                newX = x - 1;
                break;
            case NORTH_EAST:
                newX = x + 1;
                newY = y - 1;
                break;
            case SOUTH_EAST:
                newX = x + 1;
                newY = y + 1;
                break;
            case SOUTH_WEST:
                newX = x - 1;
                newY = y + 1;
                break;
            case NORTH_WEST:
                newX = x - 1;
                newY = y - 1;
                break;
        }

        if (newX < 0 || newX >= grid.getWidth() || newY < 0 || newY >= grid.getHeight()) {
            // Oops we reached the edge
            return false;
        }

        if (grid.getCharacter(newX, newY) == word[position]) {
            // This branch is still going strong

            // Move to the next position
            position++;

            if(position > word.length - 1) {
                log.log(Level.FINEST, "Branch for word " + word + " in direction " + dir.toString() + " successful");
                // We are at the end and all characters matched
                return true;
            } else {
                // Check the next character in the branch
                return checkBranch(word, newX, newY, dir, position);
            }
        } else {
            // This branch was not successful, the following character did not match the next in the word
            return false;
        }
    }

    /**
     * Find a word by searching for all instances of the first character in the grid
     * @param word
     * @return
     */
    private Word findWord(String word) {
        log.log(Level.FINER, "Finding word: {0}", word);

        char[] charArr = word.toCharArray();
        char currentChar;

        for(int i = 0; i < grid.getHeight(); i++) {
            for(int j = 0; j < grid.getWidth(); j++) {
                currentChar = grid.getCharacter(j, i);

                // If we have found the first character of the word check all branches
                if(currentChar == charArr[0]) {
                    log.log(Level.FINEST, "Checking branches for word " + word + " at " + j + " - " + i);
                    // Enumerate all posible directions
                    for(Direction dir : Direction.values()) {
                        // We start at position 1 since we have already found the first character of the word
                        if(checkBranch(charArr, j, i, dir, 1)) {
                            return new Word(word, j,i, dir);
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Solve the wordsearch
     * @return list of word instances containing their position and direction
     */
    public List<Word> solve() {
        List res = new ArrayList<Word>();
        String wordStr;

        for(int i = 0; i < words.length; i++) {
            wordStr = words[i];

            if(WordSearch.ADV_SEARCH && wordStr.length() <= 3) {
                continue;
            }

            Word word = findWord(wordStr);
            if(word != null) {
                res.add(word);
                log.log(Level.INFO, "Found word: {0}", word.toString());
            } else {
                log.log(Level.FINE, "Word does not exist: {0}", wordStr);
            }
        }

        return res;
    }

    /**
     * Get number of words to find
     * @return
     */
    public int getWordCount() {
        return words.length;
    }

}
