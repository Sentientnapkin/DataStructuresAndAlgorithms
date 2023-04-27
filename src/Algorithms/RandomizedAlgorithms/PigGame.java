package Algorithms.RandomizedAlgorithms;

import java.util.Scanner;

public class PigGame {
	public static void main (String[] args) {
		boolean gameOver = false;
		
		Scanner s = new Scanner(System.in);
		while (!gameOver) {
			System.out.println("What is the game played to: ");
			int winScore = s.nextInt();
			System.out.println("What is your score: ");
			int myScore = s.nextInt();
			System.out.println("What is your opponents score: ");
			int opponentScore = s.nextInt();
			System.out.println("Your target should be: " + Pig.getOptimalTarget(winScore, myScore, opponentScore));
			System.out.println("Is the game over (Y/N): ");
			String gameEnd = s.nextLine();
			if (gameEnd.equalsIgnoreCase("y")) {
				gameOver = true;
			}
		}
	}
}