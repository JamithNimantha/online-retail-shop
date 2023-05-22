package ui;

import controller.ItemController;
import exception.ItemNotFoundException;
import model.Item;
import util.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static util.Utility.clearConsole;
import static util.Utility.pressAnyKeyToContinue;

/**
 * @author Jamith Nimantha
 */
public final class SystemAdminUserInterface {

    private SystemAdminUserInterface() {
    }

    public static void loginAsSystemAdmin(Scanner input) {
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.printf("\t|%63s %22s %1s%n", "Online Retail Shop - Administrator", "","|");
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("\t1. View all items in the shop");
        System.out.println("\t2. Add a new item to the shop");
        System.out.println("\t3. Update an existing item in the shop");
        System.out.println("\t4. Delete an existing item from the shop");
        System.out.println("\t5. Logout from the system");
        System.out.println("\t6. Press 0 to exit");
        System.out.print("\n\tEnter an option to continue > ");
        try {
            int option = input.nextInt();
            switch (option) {
                case 1:
                    System.out.println("\tView all items in the shop");
                    SystemAdminUserInterface.viewAllItems(input);
                    break;
                case 2:
                    System.out.println("\tAdd a new item to the shop");
                    SystemAdminUserInterface.addNewItem(input);
                    break;
                case 3:
                    System.out.println("\tUpdate the quantity of an existing item in the shop");
                    SystemAdminUserInterface.updateItem(input);
                    break;
                case 4:
                    System.out.println("\tDelete an existing item from the shop");
                    SystemAdminUserInterface.deleteItem(input);
                    break;
                case 5:
                    System.out.println("\tLogout from the system");
                    clearConsole();
                    LogInUI.logIn(input);
                    break;
                case 6:
                    System.out.println("\tExiting the program...");
                    clearConsole();
                    System.exit(0);
                    break;
                default:
                    System.out.println("\tInvalid option. Please try again.");
                    SystemAdminUserInterface.loginAsSystemAdmin(input);
                    break;
            }
        } catch (Exception e) {
            System.out.println("\tInvalid option. Please try again.");
            input.nextLine();
            loginAsSystemAdmin(input);
        }
    }

    private static void deleteItem(Scanner input) {
        System.out.print("\t-----------------------------------------------------------------------------------------");
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            Item item = ItemController.getItem(itemId);// check whether the item exists
            Thread thread = ItemController.deleteItem(item);
            Utility.waitTillThreadDie(thread);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            deleteItem(input);
        }
        System.out.println("\n\t-----------------------------------------------------------------------------------------");
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

    private static void viewAllItems(Scanner input) {
        Set<Item> items = ItemController.getItems();
        printItemTable(new ArrayList<>(items));
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

    private static void updateItem(Scanner input) {
        System.out.print("\t-----------------------------------------------------------------------------------------");
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            ItemController.getItem(itemId); // check whether the item exists
            System.out.print("\n\tEnter the Item Quantity >");
            int itemQuantity = input.nextInt();
            Thread thread = ItemController.updateItemQuantity(itemId, itemQuantity);
            Utility.waitTillThreadDie(thread);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            updateItem(input);
        }
        System.out.println("\n\t-----------------------------------------------------------------------------------------");
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

    public static void addNewItem(Scanner input) {
        System.out.print("\t-----------------------------------------------------------------------------------------");
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        System.out.print("\n\tEnter the Item Name >");
        String itemName = input.next();
        System.out.print("\n\tEnter the Item Quantity >");
        int itemQuantity = input.nextInt();
        System.out.print("\n\tEnter the Item Price >");
        double itemPrice = input.nextDouble();
        System.out.print("\t-----------------------------------------------------------------------------------------");

        Thread thread = ItemController.addNewItem(new Item(itemId, itemName, itemQuantity, itemPrice));
        Utility.waitTillThreadDie(thread);
        loginAsSystemAdmin(input);
    }

    private static void printItemTable(List<Item> items) {
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
