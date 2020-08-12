import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class playerScoreboard {

    public String highscore = ""; // field variable called highscore
    public int score = 0; // score field variable

    public String getHighScoreValue()  {
        //format:  Eric:100


        FileReader readFile = null; // file reader used to read the string of text of the highscore

        BufferedReader reader = null; // buffered reader was used to read the file line by line efficiently

        // if the file encounters an error occurs in the file the highscore will return nobody
        try {
            readFile = new FileReader("highscore.txt"); // file highscore created to store the highscore after each game

            reader = new BufferedReader(readFile);
            return reader.readLine(); // reads first line of the program
        } catch (Exception e) {
            return "Nobody:0";

            // close the reader
        } finally {
            try {
                if (reader != null)
                    reader.close(); // closes reader after line is read
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //move back to score board class
	public void checkScore(){

		if (score > Integer.parseInt((highscore.split(":")[1]))) {
		    // JOPtionPane will insert a popup window for the player to enter there name when a new highscore is achieved
			String name = JOptionPane.showInputDialog("You Set a New HighScore. What is your name?");
			highscore = name + ":" + score;

			File scoreFile = new File("highscore.txt");


			if (!scoreFile.exists()) {

				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			FileWriter writeFile = null; // will write data to the file

			BufferedWriter writer = null; // extends writer class to write text to file more efficiently

			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(this.highscore);
			} catch (Exception e) {

			} finally {
				try {
					if (writer != null)
						writer.close();
				} catch (Exception e) {
				}
			}
		}
	}

    public void render () {
        // if there is no high score at the game beginning the game will pu;; the highscore from the highscore file as the current highscore
        if (highscore.equals("")) {
            //init high score
            highscore = this.getHighScoreValue();
        }
        Graphics.drawTextLeftAligned(25, 620, "Player Score: " + score);
        Graphics.drawText(400, 620, "Current High Score: " + highscore);
        //draws the score and highscore onto the board using the graphics class
    }
}
