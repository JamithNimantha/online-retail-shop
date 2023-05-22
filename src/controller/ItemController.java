package controller;

import model.Item;
import service.ItemService;
import service.ServiceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Jamith Nimantha
 */
public final class ItemController {

    private static final ItemService itemService;

    static {
        itemService = (ItemService) ServiceManager.getService(ServiceManager.ServiceType.ITEM_SERVICE);
    }

    private ItemController() {
    }

    public static Thread addNewItem(Item item) {

        Runnable addItemTask = new Runnable() {
            @Override
            public void run() {
                itemService.addItem(item);
            }
        };
        Thread addItemThread = new Thread(addItemTask);
        addItemThread.start();
        return addItemThread;
    }

    public static Item getItem(String itemID) {
        return itemService.getItem(itemID);
    }

    public static Set<Item> getItems() {
        return itemService.getItems();
    }

    public static Thread updateItemQuantity(String itemId, int itemQuantity) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                itemService.updateItemQuantity(itemId, itemQuantity);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }

    public static Thread deleteItem(Item item) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                itemService.deleteItem(item);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        return thread;
    }
}
