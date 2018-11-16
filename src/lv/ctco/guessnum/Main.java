package lv.ctco.guessnum;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

public class Main {
    static Scanner scan = new Scanner(System.in);             // Skanirujet potok dannix vvoda uzerom
    static Random rand = new Random(20);                       // Sozdajet novij generator 4isel
    static List<GameResult> results = new ArrayList<>();
    public static final File RESULTS_FILE = new File("results.txt");

    public static void main(String[] args) {
        loadResults();
        do {
            System.out.println("What is your name?");
            String name = scan.next();

            int myNum = rand.nextInt(100) + 1;         // ot 1 do 100
            System.out.println("Hello, " + name + "!");
            long t1 = System.currentTimeMillis();               // time before


            for (int i = 1; i < 10; i++) {
                System.out.println("Guess number from 1 to 100");
                int guessNumber = readUserNum();

                if (guessNumber == myNum) {
                    System.out.println("Your guess is correct. Quized number is: " + guessNumber);
                    GameResult r = new GameResult();
                    r.name = name;
                    r.triesCount = i;
                    r.duration = System.currentTimeMillis() - t1;    // time after - time before
                    results.add(r);
                    break;
                } else if (i == 9) {
                    System.out.println("You lost. Quized number was: " + myNum);
                } else {
                    System.out.println("Your guess number " + guessNumber + " is wrong. Please try again.");
                }
            }
            System.out.println("Wanna play again? Y/N");
        }
        while ("y".equals(scan.next()));      //ToDo: DZ sdelatj validaciju na ograni4enije vvoda Y/N
        displayResult();
        saveResults();
    }








    /*
    private static void wantToContinue (String value) {
        if (value.equals(scan.next())) {
            continue;
        } else if ("n".equals(scan.next())) {displayResult();}
        else {System.out.println("Please type 'y' OR 'n'");}
    }*/

    private static void displayResult() {

        // sortirovka rezultatov po koli4estvu popitok:
        results.sort(Comparator.comparingInt(r -> r.triesCount));

        //sortirovka rezultatov 2:
        results.stream()
                .sorted(Comparator.<GameResult>comparingInt(r -> r.triesCount)
                .<GameResult>thenComparingLong(r -> r.duration))
                .limit(3)                                                                                  // pervije 3
                .forEach(r -> System.out.printf("Player %s has done %d tries and it took %.2f sec\n",     //display sorted 3 results
                        r.name,
                        r.triesCount,
                        r.duration / 1000.0));                                                             // vremja dlja usera vivodim v sekundax);
    }


    private static int readUserNum() {
        while (true) {
            try {
                int guessNumber = scan.nextInt();
                if (guessNumber < 1 || guessNumber > 100) {
                    System.out.println("Please try again, your number goes over set number range");
                    continue;                                        // vozvra6ajemsja na cikl while
                }
                return guessNumber;
            } catch (InputMismatchException e) {
                scan.next();                                         // pro4itatj esli  user vvel 'abc' i zabitj. tak mozno izbavitsja ot cikli4nogo vivoda soob6enija eksepsena
                System.out.println("You are Cheater!!!");
            }
        }
    }

    private static void saveResults() {
        try (PrintWriter fileOut = new PrintWriter(RESULTS_FILE)) {    // zakrivajem fail 4tobi nebilo ute4ki pamjati. Tak nuzno delatj posle otkritija ljubogo resursa. E.g.: BD. T.e. fileOut.close();

            int skipCount = results.size() - 5; // t.e. 5 zapisej ostavitj, a ostalnoje skip (ne zapisivajem v fail vsje ostalnije, starije zapisi)

            // toze 4to i v 2. soxranenije rezultatov igri v fail:
            results.stream()
                    .skip(skipCount)
                    .forEach(r -> fileOut.printf("%S %d %d\n", r.name, r.triesCount, r.duration));

     /*  //1. Sortirovka
           boolean have5 = results.stream()
                    .filter(r -> r.name.equals("Dima"))
                    .anyMatch(r -> r.triesCount == 5);
     */



         /*
            // 2. soxranenije rezultatov igri v fail:
            for (GameResult r : results) {
                if (skipCount <= 0) {
                    fileOut.printf("%S %d %d\n", r.name, r.triesCount, r.duration);    // tut vremja xranitsja v mili sekundax
                }
                skipCount--;
            }
        */


        } catch (FileNotFoundException e) {
            e.printStackTrace(); // zakritije faila proisxodit v eksep6ene
        }
    }


    private static void loadResults() {
        try (Scanner in = new Scanner(RESULTS_FILE)) {
            while (in.hasNext()) {
                GameResult gr = new GameResult();
                // 4tenija potoka dannix dolzno sovpadatj s potokom soxranjonix rezultatov v peremennije (name, tries, duration)
                gr.name = in.next();
                gr.triesCount = in.nextInt();
                gr.duration = in.nextLong();

                results.add(gr);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
