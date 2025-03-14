package com.snake.client.application.src;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.snake.client.utils.StringUtils;

public class Score {
    private int score;
    private List<Integer> highScoreList;
    private String scoreFilePath = "client\\src\\main\\java\\com\\snake\\client\\resources\\highscore.dat";
    
    public Score(){
        this.score=0;
        this.highScoreList = new ArrayList<Integer>();
        readFile();
    }

    public void increaseScore(){
        this.score++;
    }

    public void resetScore(){
        this.score=0;
    }

    public int getScore(){
        return this.score;
    }

    public List<Integer> getHighScore() {
        if(highScoreList.size() == 0){
            readFile();
            return highScoreList;
        }
        return highScoreList;
    }

    public String getHighscore(){
        highScoreList.sort((a,b)->b-a);
        if(highScoreList.size() == 0){
            readFile();
            return StringUtils.stringFormat(highScoreList);
        }
        return StringUtils.stringFormat(highScoreList);
    }

    private void readFile(){
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            readFile = new FileReader(scoreFilePath);
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            highScoreList.add(Integer.parseInt(line));
            String allLines = line;

            while (line != null) {
                line = reader.readLine();
                
                if (line == null)
                    break;
                highScoreList.add(Integer.parseInt(line));
                allLines = allLines.concat("\n" + line);
            }

        }
        catch (Exception e) {
            System.out.println("there was a problem reading the file"+ e);
        } finally {
            try {
                if (reader != null)
                    reader.close();
            } 
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile() {
        
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            

            writeFile = new FileWriter("client\\src\\main\\java\\com\\snake\\client\\resources\\highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

            for (int i = 0; i < 10; i++) {
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else { 
                    String str = String.valueOf(list.get(i));
                    writer.write(str);
                }
                if (i < 9) {
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println("there was a problem writing the file"+ e);
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } 
            catch (IOException e) {
                return;
            }
        }

    }

    public void saveNewScore() {
        String newScore = String.valueOf(this.getScore());

        // Buat file untuk simpan highscorenya
        File scoreFile = new File("client\\src\\main\\java\\com\\snake\\client\\resources\\highscore.dat");

        // Jika file highscore.datnya tidak ada
        if (!scoreFile.exists()) {
            try {
                // Buat file baru
                scoreFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        FileWriter writeFile = null;
        BufferedWriter writer = null;

        try {
            writeFile = new FileWriter(scoreFile, true);
            writer = new BufferedWriter(writeFile);
            writer.write(newScore);
        } catch (Exception e) {
            return;
        } finally {
            try {
                if (writer != null)
                    writer.close();
            } catch (Exception e) {
                return;
            }
        }

    }

    public static void main(String[] args){
        Score score = new Score();
        System.out.println(score.getHighScore());
    }
}