package com.zetcode;
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors

import javax.swing.JOptionPane;

import java.io.FileWriter;   // Import the FileWriter class
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;



public class Score {
	
	public static void createHighscoreFile() {
	    try {
	      File fileObj = new File("Highscore.txt");
	      if (fileObj.createNewFile()) {
	        System.out.println("File created: " + fileObj.getName());
	      } else {
	        System.out.println("File already exists.");
	      }
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	  }
	public static void setHighscore(int highscore) {
		try {
		      FileWriter myWriter = new FileWriter("Highscore.txt");
		      myWriter.write("Hightscore:" + highscore);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	public static String getHighScore()

	{
		String line = "";
		FileReader file = null;
		BufferedReader reader = null;
		try {
			file = new FileReader("Highscore.dat");
			reader = new BufferedReader(file);
			line = reader.readLine();
			reader.close();
			return line;
		}
		catch (Exception e){
			return "Name:0";
		}
		
		
	}
	public static void checkScore(int currentScore,int highScore) {
		String newHighScore;
		if (currentScore > highScore) {
			String name = JOptionPane.showInputDialog("You set a new highScore!! What's your name?");
			newHighScore = name + ":" + currentScore;
			File scoreFile = new File("Highscore.dat");
			if(!scoreFile.exists()) {
				try {
					scoreFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter writer = null;
			try {
				writeFile = new FileWriter(scoreFile);
				writer = new BufferedWriter(writeFile);
				writer.write(newHighScore);
			}catch (Exception e) {}
			finally {
				if (writer != null) {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			
		}
	}
}
