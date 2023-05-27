package util;

import model.Item;

import java.util.List;
import java.util.Scanner;

/**
 * This class contains the utility methods
 *
 * @author Jamith Nimantha
 */
public class Utility {
    private Utility() {
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String CLEAR_CONSOLE = "\033[H\033[2J";
    private static final String WINDOWS = "Windows";
    private static final String MACOS = "Mac OS X";
    public static final String IDEA = "IDEA";
    public static boolean DEBUG_ENABLE = false;
    public static boolean INIT_ITEM = false;
    public static boolean INIT_SHOPPER = false;


    /**
     * This method is used to clear the console
     */
    public static void clearConsole() {
        try {
            if (isIDEA()) {
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            } else {
                if (OS.contains(WINDOWS)) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else if (OS.contains(MACOS)) {
                    new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
                } else {
                    System.out.print(CLEAR_CONSOLE);
                    System.out.flush();
                }
            }


        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the IDE is IntelliJ IDEA
     *
     * @return true if the IDE is IntelliJ IDEA
     */
    private static boolean isIDEA() {
        return System.getProperty("ide.name", IDEA).equals(IDEA);
    }

    /**
     * This method is used to wait till a thread die
     *
     * @param thread the thread to wait till die
     */
    public static void waitTillThreadDie(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to wait till the user press any key
     *
     * @param scanner the Scanner object
     */
    public static void pressAnyKeyToContinue(Scanner scanner) {
        System.out.print("\n\t Press any key to continue >");
        scanner.next();
    }


    /**
     * This method is used to print the item table
     *
     * @param items the list of items to print
     */
    public static void printItemTable(List<Item> items) {
        final String itemID = "Item ID";
        final String itemName = "Item Name";
        final String itemQuantity = "Item Quantity";
        final String itemPrice = "Item Price";
        // Determine maximum length for each column
        int idLength = Math.max(itemID.length(), items.stream().map(Item::getItemID).map(String::length).max(Integer::compare).orElse(0));
        int nameLength = Math.max(itemName.length(), items.stream().map(Item::getItemName).map(String::length).max(Integer::compare).orElse(0));
        int quantityLength = Math.max(itemQuantity.length(), items.stream().map(Item::getItemQuantity).mapToInt(Integer::valueOf).mapToObj(String::valueOf).map(String::length).max(Integer::compare).orElse(0));
        int priceLength = Math.max(itemPrice.length(), items.stream().map(Item::getItemPrice).mapToDouble(Double::valueOf).mapToObj(String::valueOf).map(String::length).max(Integer::compare).orElse(0));

        // Print header
        String divider = "+" + "-".repeat(idLength + 2) + "+" + "-".repeat(nameLength + 2) + "+" + "-".repeat(quantityLength + 2) + "+" + "-".repeat(priceLength + 2) + "+";
        System.out.println(divider);
        System.out.printf("| %-" + idLength + "s | %-" + nameLength + "s | %-" + quantityLength + "s | %-" + priceLength + "s | \n", itemID, itemName, itemQuantity, itemPrice);
        System.out.println(divider);

        // Print rows
        for (Item item : items) {
            System.out.printf("| %-" + idLength + "s | %-" + nameLength + "s | %-" + quantityLength + "d | %,-" + priceLength + ".2f |\n", item.getItemID(), item.getItemName(), item.getItemQuantity(), item.getItemPrice());
        }
        // Print footer
        System.out.println(divider);
    }
}
