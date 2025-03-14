package src;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Score {
    private int score;

    //konstruktor
    public Score(){
        this.score=0;
    }

    //menambah score
    public void increaseScore(){
        this.score++;
    }

    //reset Score
    public void resetScore(){
        this.score=0;
    }

    //untuk mengembalikan nilai score ke tampilan Gameplay
    public int getScore(){
        return this.score;
    }

    // Fungsi buat ambil HighScore
    public String getHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        try {
            // ReadFile highscore.dat
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();
            String allLines = line;

            while (line != null) {
                // Baca per baris
                line = reader.readLine();
                // Ini ada untuk error handling
                if (line == null)
                    break;
                // Gabunging barisnya
                allLines = allLines.concat("\n" + line);
            }

            // return String yang persis seperti isi dari highscore.dat
            return allLines;
        }
        // Kalau highscore.dat nya gaada
        catch (Exception e) {
            return "0\n0\n0\n0\n0\n0\n0\n0\n0\n0";
        } finally {
            try {
                // Tutup readernya
                if (reader != null)
                    reader.close();
            } // Kalau terjadi exception
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //fungsi untuk mengurutkan high score
    public void sortHighScore() {
        FileReader readFile = null;
        BufferedReader reader = null;
        FileWriter writeFile = null;
        BufferedWriter writer = null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            readFile = new FileReader("highscore.dat");
            reader = new BufferedReader(readFile);

            String line = reader.readLine();

            // Pindahkan isi dari highscore.dat ke sebuah array List
            while (line != null) {
                list.add(Integer.parseInt(line));
                line = reader.readLine();
            }

            // Sort array listnya
            Collections.sort(list);

            // Balikin biar jadi descending
            Collections.reverse(list);

            // Tulis ke highscore.dat, score yang udah diurutin
            writeFile = new FileWriter("highscore.dat");
            writer = new BufferedWriter(writeFile);

            int size = list.size();

            // Nantinya akan hanya 10 skor teratas yang ditulis kembali
            for (int i = 0; i < 10; i++) {
                // Ini untuk mengisi nilai lainnya 0
                if (i > size - 1) {
                    String def = "0";
                    writer.write(def);
                } else { // Ambil satu satu dari list
                    String str = String.valueOf(list.get(i));
                    writer.write(str);
                }
                if (i < 9) {// This prevent creating a blank like at the end of the file**
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            return;
        } finally {
            try {
                // Tutup readernya
                if (reader != null)
                    reader.close();
                // Tutup writer
                if (writer != null)
                    writer.close();
            } // Kalau terjadi exception
            catch (IOException e) {
                return;
            }
        }

    }

    // Fungsi buat nulis score baru di file
    public void saveNewScore() {
        String newScore = String.valueOf(this.getScore());

        // Buat file untuk simpan highscorenya
        File scoreFile = new File("highscore.dat");

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
            // Tulis new scorenya di file
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
}