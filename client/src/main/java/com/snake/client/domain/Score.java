package com.snake.client.domain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.snake.client.utils.StringUtils;

import lombok.Getter;

/**
 * The Score class is responsible for managing the player's score and high
 * scores.
 * It provides functionality to increase, reset, and retrieve the current score,
 * as well as manage a list of high scores. High scores are currently stored in
 * a file,
 * but future implementations will read from a database.
 *
 * <p>
 * Key Features:
 * - Tracks the current score of the player.
 * - Maintains a list of high scores, sorted in descending order.
 * - Reads high scores from a file during initialization.
 * - Writes updated high scores to a file after a new score is saved.
 * - Limits the high score list to a maximum of 10 entries.
 *
 * <p>
 * Future Enhancements:
 * - Replace file-based storage with database integration for high scores.
 *
 * <p>
 * Usage:
 * - Use {@link #increaseScore()} to increment the current score.
 * - Use {@link #resetScore()} to reset the score to zero.
 * - Use {@link #getScore()} to retrieve the current score.
 * - Use {@link #getHighScore()} to retrieve the top 10 high scores as a list.
 * - Use {@link #saveNewScore()} to save the current score to the high score
 * list.
 */
@Getter
public class Score {
    private int score;
    private List<Integer> highScoreList;
    private String scoreFilePath = "client/src/main/java/com/snake/client/resources/highscore.dat";
    private final int MAX_VALUES = 10;
    private static final Logger logger = LoggerFactory.getLogger(Score.class);

    public Score(){
        this.score=0;
        this.highScoreList = new ArrayList<Integer>();
        readFile();
    }

    public void increaseScore() {
        this.score++;
    }

    public void resetScore() {
        this.score = 0;
    }

    public List<Integer> getHighScore() {
        highScoreList.sort((a, b) -> b - a);
        if (highScoreList.size() == 0) {
            readFile();
            return highScoreList.subList(0, 10);
        }
        return highScoreList.subList(0, 10);
    }

    public String getHighscore() {
        highScoreList.sort((a, b) -> b - a);
        if (highScoreList.size() == 0) {
            readFile();
            return StringUtils.stringFormat(highScoreList.subList(0, 10));
        }
        return StringUtils.stringFormat(highScoreList.subList(0, 10));
    }

    // TODO: Implement database integration for high scores
    private void readFile() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader(scoreFilePath);
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            highScoreList.add(Integer.parseInt(line));
            String allLines = line;

            int i = 1;
            while (line != null && i < MAX_VALUES) {
                line = reader.readLine();

                if (line == null)
                    break;
                highScoreList.add(Integer.parseInt(line));
                allLines = allLines.concat("\n" + line);
                i++;
            }
        } catch (Exception e) {
            highScoreList = new ArrayList<Integer>(Collections.nCopies(MAX_VALUES, 0));

            logger.error("there was a problem reading the file", e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // TODO: Implement database integration for high scores
    private void writeFile() {

        FileWriter writeFile = null;
        BufferedWriter writer = null;
        try {
            writeFile = new FileWriter(scoreFilePath);
            writer = new BufferedWriter(writeFile);

            int size = highScoreList.size();

            for (int i = 0; i < MAX_VALUES; i++) {
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else {
                    writer.write(String.valueOf(highScoreList.get(i)));
                }
                if (i < 9) {
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            logger.error("there was a problem writing the file"+ e);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (IOException e) {
                return;
            }
        }

    }

    public void saveNewScore() {
        highScoreList.add(this.getScore());
        highScoreList.sort((a, b) -> b - a);
        writeFile();
    }
}