package me.ryanwelch.wordsearch;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Copyright 2015 (C) Ryan Welch
 *
 * @author Ryan Welch
 */
public class Util {

    /**
     * Reads a file and returns it as a string, includes line break characters
     * @param file File path to read
     * @return contents
     */
    public static String readFile(String file) {
        StringBuilder res = new StringBuilder();
        String temp;

        BufferedReader in;

        // Attempt to load file
        try  {
            in = new BufferedReader(new FileReader(file));
        } catch(FileNotFoundException e) {
            return null;
        }

        // Attempt to read file
        try {
            while ((temp = in.readLine()) != null) {
                res.append(temp + "\n");
            }
        } catch(IOException e) {
            return null;
        }

        return res.toString();
    }

    /**
     * Get the length of the longest string in an array
     * @param array
     * @return Length of longest string
     */
    public static int getLongestString(String[] array) {
        int maxSize = 0;

        for(String str : array) {
            if(str.length() > maxSize) {
                maxSize = str.length();
            }
        }

        return maxSize;
    }
}
