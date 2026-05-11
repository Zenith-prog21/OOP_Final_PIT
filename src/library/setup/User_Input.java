package library.setup;

import java.util.Scanner;

public class User_Input {
    Scanner scan = new Scanner(System.in);


    int input_integer(String prompt, int min, int max) {
        int input;
        while (true) {
            System.out.printf(prompt);
            if (scan.hasNextInt()) {
                input = scan.nextInt();
                scan.nextLine();
                if (input >= min && input <= max) {
                    return input;
                } else {
                    System.out.printf("\nInvalid input! Please enter between %d and %d\n", min, max);
                    return 0;
                }
            } else {
                scan.next();
                continue;
            }
        }
    }

    String input_string(String prompt, int max_amount_of_text) {
        String input;
        while (true) {
            System.out.printf(prompt);
            input = scan.nextLine();
            if (input.isEmpty()) {
                System.out.println("Please Enter a valid Number!");
                continue;
            } else if (input.length() > max_amount_of_text) {
                System.out.println("Please Enter only " + max_amount_of_text + " Amount of Texts");
                continue;
            } else {
                return input;
            }
        }
    }
}