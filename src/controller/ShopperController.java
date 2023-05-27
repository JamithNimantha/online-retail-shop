package controller;

import exception.CheckOutException;
import model.Item;
import model.Shopper;
import service.ItemService;
import service.ServiceManager;
import service.ShopperService;
import util.Utility;

import java.util.ArrayList;
import java.util.List;

import static util.Utility.clearConsole;

/**
 * @author Jamith Nimantha
 */
public class ShopperController {


    private static final ItemService itemService = (ItemService) ServiceManager.getService(ServiceManager.ServiceType.ITEM_SERVICE);
    private static final ShopperService shopperService = (ShopperService) ServiceManager.getService(ServiceManager.ServiceType.SHOPPER_SERVICE);

    private ShopperController() {
    }


    /**
     * This method is responsible to return all items list
     *
     * @return List of Items object
     */
    public static List<Item> getAllItems() {
        return new ArrayList<>(itemService.getItems());
    }

    /**
     * This method is responsible to add item to the cart
     *
     * @param shopperId shopper id
     * @param itemId   item id
     * @param quantity quantity
     */
    public static void addItemToCard(String shopperId, String itemId, int quantity) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shopperService.addItemToCart(shopperId, itemId, quantity);
            }
        };
        Thread addItemThread = new Thread(runnable);
        addItemThread.start();
        Utility.waitTillThreadDie(addItemThread);
    }

    /**
     * This method is responsible to checkout
     *
     * @return shopper id
     */
    public static String addShopperNewShopper() {
        Shopper shopper = new Shopper();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shopperService.addNewShopper(shopper);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Utility.waitTillThreadDie(thread);
        return shopper.getShopperId();
    }

    /**
     * This method is responsible to print item in the cart
     *
     * @param shopperId shopper id
     */
    public static void printItemInTheCart(String shopperId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shopperService.viewCart(shopperId);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Utility.waitTillThreadDie(thread);
    }

    /**
     * This method is responsible to remove item from the cart
     *
     * @param shopperId shopper id
     * @param itemId   item id
     */
    public static void removeItemFromCart(String shopperId, String itemId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shopperService.removeItemFromCart(shopperId, itemId);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        Utility.waitTillThreadDie(thread);
    }

    /**
     * This method is responsible to checkout
     *
     * @param shopperId shopper id
     * @throws CheckOutException checkout exception
     */
    public static void checkout(String shopperId) throws CheckOutException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                shopperService.checkout(shopperId);
            }
        };
        Thread thread = new Thread(runnable);
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        Utility.waitTillThreadDie(thread);
    }

    /**
     * This method is responsible to initialize shopper
     *
     * @param shopperId shopper id
     */
    public static void initializeShopper(String shopperId) {
        shopperService.addItemToCart(shopperId, "ITM1", 1);
        shopperService.addItemToCart(shopperId, "ITM2", 2);
        shopperService.addItemToCart(shopperId, "ITM3", 4);
        shopperService.addItemToCart(shopperId, "ITM4", 1);
        shopperService.addItemToCart(shopperId, "ITM5", 1);
        clearConsole();
    }
}
