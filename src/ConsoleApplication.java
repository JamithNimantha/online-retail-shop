
import controller.ItemController;
import exception.ItemNotFoundException;
import model.Item;
import service.ItemService;
import service.ServiceManager;
import service.ShopperService;
import service.imp.ItemServiceImpl;
import service.imp.ShopperServiceImpl;
import ui.LogInUI;
import ui.SystemAdminUserInterface;

import java.util.Scanner;

import static util.Utility.IDEA;
import static util.Utility.clearConsole;


/**
 * @author Jamith Nimantha
 */
public class ConsoleApplication {
    public static Scanner input = new Scanner(System.in);
    private static ItemService itemService = null;
    private static ShopperService shopperService = (ShopperService) ServiceManager.getService(ServiceManager.ServiceType.SHOPPER_SERVICE);

    /**
     * This is the main method which is the entry point of the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        checkArgs(args);
        itemService = (ItemService) ServiceManager.getService(ServiceManager.ServiceType.ITEM_SERVICE);
        initDB();
        LogInUI.logIn(input);
    }

    private static void initDB() {
        initializeItems();
        clearConsole();
    }

    private static void initializeItems() {
        itemService.addItem(new Item("ITM1", "Roomba 675 Robot Vacuum", 1, 10.50));
        itemService.addItem(new Item("ITM2", "Apple AirPods Pro", 2, 3400.43));
        itemService.addItem(new Item("ITM3", "Samsung Galaxy Watch", 8, 120.00));
        itemService.addItem(new Item("ITM4", "Sony WH-1000XM4 Wireless Headphones", 3, 45.00));
        itemService.addItem(new Item("ITM5", "Instant Pot Duo Nova Pressure Cooker", 1, 12.00));
        itemService.addItem(new Item("ITM6", "Fitbit Charge 4 Fitness Tracker", 5, 23.00));
        itemService.addItem(new Item("ITM7", "JBL Flip 5 Waterproof Bluetooth Speaker", 6, 56.00));
        itemService.addItem(new Item("ITM8", "Fitbit Charge 4", 4, 78.00));
        itemService.addItem(new Item("ITM9", "Philips Sonicare ProtectiveClean 6500 Toothbrush", 1, 34.00));
    }

    /**
     *  This method is used to check the arguments passed to the program
     *
     * @param args the command line arguments
     */
    private static void checkArgs(String[] args) {
        for (String arg : args) {
            if (IDEA.equals(arg)) {
                System.setProperty("ide.name", IDEA);
            }
        }
    }


//    public static void logIn() {
//        System.out.println("\t-----------------------------------------------------------------------------------------");
//        System.out.printf("\t|%63s %22s %1s%n", "Login to Online Retail Shop", "","|");
//        System.out.println("\t-----------------------------------------------------------------------------------------");
//        System.out.println();
//        System.out.println("\t1. Login as System Administrator");
//        System.out.println("\t2. Login as Shopper");
//        System.out.println("\t3. Press 0 to exit");
//        System.out.print("\n\tEnter an option to continue > ");
//        try {
//            int option = input.nextInt();
//            switch (option) {
//                case 1:
//                    System.out.println("\tLogin as System Administrator");
//                    SystemAdminUserInterface.loginAsSystemAdmin(input);
//                    break;
//                case 2:
//                    System.out.println("\tLogin as Shopper");
//                    loginAsShopper();
//                    break;
//                case 0:
//                    System.out.println("\tExiting the program...");
//                    System.exit(0);
//                    break;
//                default:
//                    System.out.println("\tInvalid option. Please try again.");
//                    logIn();
//                    break;
//            }
//        } catch (Exception e) {
//            System.out.println("\tInvalid option. Please try again.");
//            input.nextLine();
//            logIn();
//        }
//    }

    private static void loginAsShopper() {
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
                    itemService.viewAllItems();
                    loginAsShopper();
                    break;
                case 2:
                    System.out.println("\tAdd an item to the shopping cart");
                    addItemToCart();
                    loginAsShopper();
                    break;
                case 3:
                    System.out.println("\tRemove an item from the shopping cart");
                    removeItemFromCart();
                    loginAsShopper();
                    break;
                case 4:
                    System.out.println("\tView items in the shopping cart");
                    viewItemsInCart();
                    loginAsShopper();
                    break;
                case 5:
                    System.out.println("\tCheckout");
                    checkout();
                    loginAsShopper();
                    break;
                case 6:
                    System.out.println("\tLogout from the system");
                    logIn();
                    break;
                case 0:
                    System.out.println("\tExiting the program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\tInvalid option. Please try again.");
                    loginAsShopper();
                    break;
            }
        } catch (Exception e) {
            System.out.println("\tInvalid option. Please try again.");
            input.nextLine();
            loginAsShopper();
        }
    }

    private static void checkout() {

    }

    private static void viewItemsInCart() {

    }

    private static void removeItemFromCart() {

    }

    private static void addItemToCart() {
        System.out.print("\t-----------------------------------------------------------------------------------------");
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        System.out.print("\n\tEnter the Quantity >");
        int itemQuantity = input.nextInt();
//        itemService.addItem(new Item(itemId, itemName, itemQuantity));itemQuantity
        System.out.println("\n\t-----------------------------------------------------------------------------------------");
    }

    private static void loginAsSystemAdmin() {
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
                    itemService.viewAllItems();
                    loginAsSystemAdmin();
                    break;
                case 2:
                    System.out.println("\tAdd a new item to the shop");
                    SystemAdminUserInterface.addNewItem(input);
                    loginAsSystemAdmin();
                    break;
                case 3:
                    System.out.println("\tUpdate the quantity of an existing item in the shop");
                    updateItem();
                    loginAsSystemAdmin();
                    break;
                case 4:
                    System.out.println("\tDelete an existing item from the shop");
                    deleteItem();
                    loginAsSystemAdmin();
                    break;
                case 5:
                    System.out.println("\tLogout from the system");
                    logIn();
                    clearConsole();
                    break;
                case 6:
                    System.out.println("\tExiting the program...");
                    clearConsole();
                    System.exit(0);
                    break;
                default:
                    System.out.println("\tInvalid option. Please try again.");
                    loginAsSystemAdmin();
                    break;
            }
        } catch (Exception e) {
            System.out.println("\tInvalid option. Please try again.");
            input.nextLine();
            loginAsSystemAdmin();
        }
    }

    private static void deleteItem() {
        System.out.print("\t-----------------------------------------------------------------------------------------");
        System.out.print("\n\tEnter the Item ID >");
        String itemId = input.next();
        try {
            Item item = itemService.getItem(itemId);// check whether the item exists
            itemService.deleteItem(item);
        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("\tItem not found. Please try again.");
            deleteItem();
        }
        System.out.println("\n\t-----------------------------------------------------------------------------------------");
    }

//    private static void updateItem() {
//        System.out.print("\t-----------------------------------------------------------------------------------------");
//        System.out.print("\n\tEnter the Item ID >");
//        String itemId = input.next();
//        try {
//            itemService.getItem(itemId); // check whether the item exists
//            System.out.print("\n\tEnter the Item Quantity >");
//            int itemQuantity = input.nextInt();
//            itemService.updateItemQuantity(itemId, itemQuantity);
//        } catch (ItemNotFoundException e) {
//            System.out.println(e.getMessage());
//            System.out.println("\tItem not found. Please try again.");
//            updateItem();
//        }
//        System.out.println("\n\t-----------------------------------------------------------------------------------------");
//    }

//    private static void addNewItem() {
//        System.out.print("\t-----------------------------------------------------------------------------------------");
//        System.out.print("\n\tEnter the Item ID >");
//        String itemId = input.next();
//        System.out.print("\n\tEnter the Item Name >");
//        String itemName = input.next();
//        System.out.print("\n\tEnter the Item Quantity >");
//        int itemQuantity = input.nextInt();
//        System.out.print("\n\tEnter the Item Price >");
//        double itemPrice = input.nextDouble();
//        itemService.addItem(new Item(itemId, itemName, itemQuantity, itemPrice));
//        System.out.println("\n\t-----------------------------------------------------------------------------------------");
//    }

}
