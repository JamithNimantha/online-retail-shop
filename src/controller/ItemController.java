package controller;

import model.Item;
import service.ItemService;
import service.ServiceManager;
import util.Utility;

import java.util.ArrayList;
import java.util.List;

import static util.Utility.clearConsole;

/**
 * This class is responsible to perform item related functionalities
 *
 * @author Jamith Nimantha
 */
public final class ItemController {

    private static final ItemService itemService = (ItemService) ServiceManager.getService(ServiceManager.ServiceType.ITEM_SERVICE);

    private ItemController() {
    }

    /**
     * This method is responsible to add new item
     *
     * @param item newly created item
     */
    public static void addNewItem(Item item) {

        Runnable addItemTask = new Runnable() {
            @Override
            public void run() {
                itemService.addItem(item);
            }
        };
        Thread addItemThread = new Thread(addItemTask);
        addItemThread.start();
        Utility.waitTillThreadDie(addItemThread);
    }

    /**
     * This method is responsible to return item by item id
     *
     * @param itemID item id
     * @return  item
     */
    public static Item getItem(String itemID) {
        return itemService.getItem(itemID);

    }

    /**
     * This method is responsible to return all items list
     *
     * @return List of Items object
     */
    public static List<Item> getItems() {
        return new ArrayList<>(itemService.getItems());
    }

    /**
     * This method is responsible to update item quantity
     *
     * @param itemId item id
     * @param itemQuantity item quantity
     */
    public static void updateItemQuantity(String itemId, int itemQuantity) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                itemService.updateItemQuantity(itemId, itemQuantity);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Utility.waitTillThreadDie(thread);
    }

    /**
     * This method is responsible to delete item
     *
     * @param item item
     */
    public static void deleteItem(Item item) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                itemService.deleteItem(item);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Utility.waitTillThreadDie(thread);
    }

    /**
     * This method is responsible to initialize items
     */
    public static void initializeItems() {
        itemService.addItem(new Item("ITM1", "Roomba 675 Robot Vacuum", 1, 10.50));
        itemService.addItem(new Item("ITM2", "Apple AirPods Pro", 2, 3400.43));
        itemService.addItem(new Item("ITM3", "Samsung Galaxy Watch", 8, 120.00));
        itemService.addItem(new Item("ITM4", "Sony WH-1000XM4 Wireless Headphones", 3, 45.00));
        itemService.addItem(new Item("ITM5", "Instant Pot Duo Nova Pressure Cooker", 1, 12.00));
        itemService.addItem(new Item("ITM6", "Fitbit Charge 4 Fitness Tracker", 5, 23.00));
        itemService.addItem(new Item("ITM7", "JBL Flip 5 Waterproof Bluetooth Speaker", 6, 56.00));
        itemService.addItem(new Item("ITM8", "Fitbit Charge 4", 4, 78.00));
        itemService.addItem(new Item("ITM9", "Philips Sonicare ProtectiveClean 6500 Toothbrush", 1, 34.00));
        clearConsole();
    }
}
