package ui;

import java.util.Scanner;

/**
 * @author Jamith Nimantha
 */
public final class LogInUI {

    private LogInUI() {
    }

    public static void logIn(Scanner input) {
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.printf("\t|%63s %22s %1s%n", "Login to Online Retail Shop", "","|");
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("\t1. Login as System Administrator");
        System.out.println("\t2. Login as Shopper");
        System.out.println("\t3. Press 0 to exit");
        System.out.print("\n\tEnter an option to continue > ");
        try {
            int option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.println("\tLogin as System Administrator");
                    SystemAdminUserInterface.loginAsSystemAdmin(input);
                    break;
                case 2:
                    System.out.println("\tLogin as Shopper");
//                    loginAsShopper();
                    break;
                case 0:
                    System.out.println("\tExiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\tInvalid option. Please try again.");
                    logIn(input);
                    break;
            }
        } catch (Exception e) {
            System.out.println("\tInvalid option. Please try again.");
            input.nextLine();
            logIn(input);
        }
    }
}
