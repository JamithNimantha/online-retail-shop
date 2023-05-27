package ui;

import controller.ItemController;
import controller.ShopperController;
import util.Utility;

import java.util.Scanner;

/**
 * LogInUI class is the command line interface for the login menu.
 *
 * @author Jamith Nimantha
 */
public final class LogInUI {

    private LogInUI() {
    }

    /**
     * This method is used to display the login menu
     *
     * @param input the Scanner object
     */
    public static void logIn(Scanner input) {
        if (Utility.INIT_ITEM) {
            ItemController.initializeItems();
        }
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
                    SystemAdminUserCLI.loginAsSystemAdmin(input);
                    break;
                case 2:
                    System.out.println("\tLogin as Shopper");
                    String shopperId = ShopperController.addShopperNewShopper();
                    ShopperCLI.setShopperId(shopperId);
                    if (Utility.INIT_SHOPPER) {
                        ShopperController.initializeShopper(shopperId);
                    }
                    ShopperCLI.loginAsShopper(input);
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
