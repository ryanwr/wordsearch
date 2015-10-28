package me.ryanwelch.wordsearch;

/**
 * Copyright 2015 (C) Ryan Welch
 *
 * @author Ryan Welch
 */
public class Word {

    private int x;
    private int y;
    private Direction direction;
    private int length;
    private String word;

    public Word(String word, int x, int y, Direction dir) {
        this.word = word;
        this.x = x;
        this.y = y;
        this.direction = dir;
        this.length = word.length();
    }

    /**
     * toString method
     * @return
     */
    public String toString() {
        return word + " found at x:" + (x + 1) + " y:" + (y + 1) + " dir:" + direction.toString();
    }
}
