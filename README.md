# Wordsearch

## Build

Run `ant` on the command line to build the project

The jar will be built in out/artifacts/wordsearch_jar/wordsearch.jar

### Usage

java -jar wordsearch.jar [grid file] [word file]

The grid file must be a grid of letters of equal length lines, see wordsearch1.txt
The word file is any file of words, all non alpha characters will be removed, see wordsearch1_words.txt

Example: java -jar wordsearch.jar wordsearch2.txt wordsearch2_words.txt