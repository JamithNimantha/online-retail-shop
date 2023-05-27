package ui;

import controller.ItemController;
import exception.ItemNotFoundException;
import model.Item;
import util.Utility;

import java.util.List;
import java.util.Scanner;

import static util.Utility.clearConsole;

/**
 * @author Jamith Nimantha
 */
public final class SystemAdminUserCLI {

    private SystemAdminUserCLI() {
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
                    SystemAdminUserCLI.viewAllItems(input);
                    break;
                case 2:
                    System.out.println("\tAdd a new item to the shop");
                    SystemAdminUserCLI.addNewItem(input);
                    break;
                case 3:
                    System.out.println("\tUpdate the quantity of an existing item in the shop");
                    SystemAdminUserCLI.updateItem(input);
                    break;
                case 4:
                    System.out.println("\tDelete an existing item from the shop");
                    SystemAdminUserCLI.deleteItem(input);
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
                    Utility.pressAnyKeyToContinue(input);
                    SystemAdminUserCLI.loginAsSystemAdmin(input);
                    break;
            }
        } catch (Exception e) {
            System.out.println("\tInvalid option. Please try again.");
            input.nextLine();
            Utility.pressAnyKeyToContinue(input);
            loginAsSystemAdmin(input);
        }
    }

    private static void deleteItem(Scanner input) {
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            Item item = ItemController.getItem(itemId);// check whether the item exists
            ItemController.deleteItem(item);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            Utility.pressAnyKeyToContinue(input);
            deleteItem(input);
        }
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

    private static void viewAllItems(Scanner input) {
        List<Item> items = ItemController.getItems();
        Utility.printItemTable(items);
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

    private static void updateItem(Scanner input) {
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            ItemController.getItem(itemId); // check whether the item exists
            System.out.print("\n\tEnter the Item Quantity >");
            int itemQuantity = input.nextInt();
            ItemController.updateItemQuantity(itemId, itemQuantity);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            Utility.pressAnyKeyToContinue(input);
            updateItem(input);
        }
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

    public static void addNewItem(Scanner input) {
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        System.out.print("\n\tEnter the Item Name >");
        String itemName = input.next();
        System.out.print("\n\tEnter the Item Quantity >");
        int itemQuantity = input.nextInt();
        System.out.print("\n\tEnter the Item Price >");
        double itemPrice = input.nextDouble();

        ItemController.addNewItem(new Item(itemId, itemName, itemQuantity, itemPrice));
        Utility.pressAnyKeyToContinue(input);
        loginAsSystemAdmin(input);
    }

}
