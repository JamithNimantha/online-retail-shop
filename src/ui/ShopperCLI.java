package ui;

import controller.ItemController;
import controller.ShopperController;
import exception.CheckOutException;
import model.Item;
import util.Utility;

import java.util.List;
import java.util.Scanner;

/**
 * ShopperCLI class is the command line interface for the shopper.
 * This class is responsible for handling all the user inputs and outputs.
 *
 * @author Jamith Nimantha
 */
public class ShopperCLI {

    private static String shopperId;
    public static void loginAsShopper(Scanner input) {
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.printf("\t|%63s %22s %1s%n", "Online Retail Shop - Shopper", "", "|");
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("\t1. View all items in the shop");
        System.out.println("\t2. Add an item to the shopping cart");
        System.out.println("\t3. Remove an item from the shopping cart");
        System.out.println("\t4. View items in the shopping cart");
        System.out.println("\t5. Checkout");
        System.out.println("\t6. Logout from the system");
        System.out.println("\t7. Press 0 to exit");
        System.out.print("\n\tEnter an option to continue > ");
        try {
            int option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.println("\tView all items in the shop");
                    ShopperCLI.viewAllItems(input);
                    break;
                case 2:
                    System.out.println("\tAdd an item to the shopping cart");
                    ShopperCLI.addItemToCart(input);
                    break;
                case 3:
                    System.out.println("\tRemove an item from the shopping cart");
                    ShopperCLI.removeItemFromCart(input);
                    break;
                case 4:
                    System.out.println("\tView items in the shopping cart");
                    ShopperCLI.viewItemsInCart(input);
                    break;
                case 5:
                    System.out.println("\tCheckout");
                    ShopperCLI.checkout(input);
                    break;
                case 6:
                    System.out.println("\tLogout from the system");
                    LogInUI.logIn(input);
                    break;
                case 0:
                    System.out.println("\tExiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\tInvalid option. Please try again.");
                    ShopperCLI.loginAsShopper(input);
                    break;
            }
        } catch (Exception e) {
            System.out.println("\tInvalid option. Please try again.");
            input.nextLine();
            ShopperCLI.loginAsShopper(input);
        }
    }

    private static void checkout(Scanner input) {
        try {
            ShopperController.checkout(getShopperId());
        } catch (CheckOutException e) {
            System.out.println(e.getMessage());
            System.out.println("\tCheckout failed. Please update you cart and try again.");
        }
        Utility.pressAnyKeyToContinue(input);
        ShopperCLI.loginAsShopper(input);
    }

    private static void viewItemsInCart(Scanner input) {
        ShopperController.printItemInTheCart(getShopperId());
        Utility.pressAnyKeyToContinue(input);
        ShopperCLI.loginAsShopper(input);
    }

    private static void removeItemFromCart(Scanner input) {
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            ItemController.getItem(itemId);
            ShopperController.removeItemFromCart(getShopperId(), itemId);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            ShopperCLI.removeItemFromCart(input);
        }

        Utility.pressAnyKeyToContinue(input);
        ShopperCLI.loginAsShopper(input);
    }

    private static void addItemToCart(Scanner input) {
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            Item item = ItemController.getItem(itemId);
            System.out.print("\n\tEnter the Quantity >");
            int itemQuantity = input.nextInt();
            if (item.getItemQuantity() < 1) {
                System.out.println("\tItem is out of stock. Please try again.");
                Utility.pressAnyKeyToContinue(input);
                ShopperCLI.addItemToCart(input);
            } else if (item.getItemQuantity() < itemQuantity) {
                System.out.println("\tItem quantity is not enough. Please try again.");
                ShopperCLI.addItemToCart(input);
            }
            ShopperController.addItemToCard(getShopperId(), itemId, itemQuantity);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            ShopperCLI.addItemToCart(input);
        }

        Utility.pressAnyKeyToContinue(input);
        ShopperCLI.loginAsShopper(input);
    }

    private static void viewAllItems(Scanner input) {
        List<Item> itemList = ItemController.getItems();
        Utility.printItemTable(itemList);
        Utility.pressAnyKeyToContinue(input);
        ShopperCLI.loginAsShopper(input);
    }

    public static String getShopperId() {
        return shopperId;
    }

    public static void setShopperId(String shopperId) {
        ShopperCLI.shopperId = shopperId;
    }
}
