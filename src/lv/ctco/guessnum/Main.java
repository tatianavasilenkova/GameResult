package lv.ctco.guessnum;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    static Scanner scan = new Scanner(System.in);             // Skanirujet potok dannix vvoda uzerom
    static Random rand = new Random(20);                       // Sozdajet novij generator 4isel

    public static void main(String[] args) {
        System.out.println("What is your name?");
        String name = scan.next();


        int myNum = rand.nextInt(100) + 1;         // ot 1 do 100
        System.out.println("Hello, " + name + "!");


        for (int i = 0; i < 10; i++) {
            System.out.println("Guess number from 1 to 100");
            int guessNumber;
            try {
                guessNumber = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("You are Cheater!!!");
                return;
            }

            if (guessNumber == myNum) {
                System.out.println("Your guess is correct. Quized number is: " + guessNumber);
                break;
            } else if (i == 9 && guessNumber != myNum) {
                System.out.println("You lost. Quized number was: " + myNum);
            } else {
                System.out.println("Your guess number " + guessNumber + " is wrong. Please try again.");
            }
        }
    }
}
