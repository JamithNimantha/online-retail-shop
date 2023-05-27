import model.Item;
import model.Shopper;
import service.ItemService;
import service.ServiceManager;
import service.ShopperService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Test simulation application
 *
 * @author Jamith Nimantha
 */
public class TestSimulationApplication {

    private final static int NUMBER_OF_ITEMS = 25;
    private final static int NUMBER_OF_SHOPPERS = 25;

    private final static boolean ENABLE_THREAD_LOG = true;

    private static final ItemService itemService = (ItemService) ServiceManager.getService(ServiceManager.ServiceType.ITEM_SERVICE);
    private static final ShopperService shopperService = (ShopperService) ServiceManager.getService(ServiceManager.ServiceType.SHOPPER_SERVICE);

    public static void main(String[] args) {

        ThreadGroup shopperTheadGroup = new ThreadGroup("Shopper Group");
        ThreadGroup itemThreadGroup = new ThreadGroup("Item Group");
        List<Thread> threads = new ArrayList<>();

        // Create items
        for (int i = 1; i <= NUMBER_OF_ITEMS; i++) {
            int id = i;
            Thread thread = new Thread(
                    itemThreadGroup,
                    new Runnable() {
                        @Override
                        public void run() {
                            printThreadInfoLog();
                            itemService.addItem(new Item("ITM" + id, "Test Item " + id, getRandomQuantity(300, 400), getRandomPrice(10, 300)));
                        }
                    },
                    "Add Item " + i
            );
            thread.start();
        }

        while (itemThreadGroup.activeCount() > 0) {
            System.out.println("Active Threads in Item Group: " + itemThreadGroup.activeCount());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Create shoppers
        for (int i = 1; i <= NUMBER_OF_SHOPPERS; i++) {
            Thread thread = new Thread(
                    shopperTheadGroup,
                    () -> {
                        printThreadInfoLog();
                        shopperService.addNewShopper(new Shopper());
                    },
                    "Add Shopper " + i
            );
            thread.start();
        }

        while (shopperTheadGroup.activeCount() > 0) {
            System.out.println("Active Threads in Shopper Group: " + itemThreadGroup.activeCount());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // update stock
        for (int i = 1; i <= NUMBER_OF_ITEMS; i++) {
            int id = i;
            Thread thread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            printThreadInfoLog();
                            itemService.updateItemQuantity("ITM" + id, getRandomQuantity(500, 1000));
                        }
                    },
                    "Update Stock " + i
            );
            threads.add(thread);
        }

        // checkout
        for (int i = 1; i <= NUMBER_OF_SHOPPERS; i++) {
            int id = i;
            Thread thread = new Thread(
                    shopperTheadGroup,
                    new Runnable() {
                        @Override
                        public void run() {
                            printThreadInfoLog();
                            shopperService.checkout(shopperService.getShoppers().get(id - 1).getShopperId());
                        }
                    },
                    "Checkout " + i
            );
            threads.add(thread);
        }

        // Add items to cart
        for (int i = 1; i <= NUMBER_OF_SHOPPERS; i++) {
            int id = i;
            Thread thread = new Thread(
                    shopperTheadGroup,
                    new Runnable() {
                        @Override
                        public void run() {
                            Shopper shopper = shopperService.getShoppers().get(id - 1);
                            for (int j = 1; j <= NUMBER_OF_ITEMS; j++) {
                                printThreadInfoLog();
                                shopperService.addItemToCart(shopper.getShopperId(), "ITM" + j, getRandomQuantity(1, 10));
                            }
                        }
                    },
                    "Add Items to Cart " + i
            );
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }

        // print active thread count in each thread group
        if (ENABLE_THREAD_LOG) {
            while (itemThreadGroup.activeCount() > 0 || shopperTheadGroup.activeCount() > 0) {
                System.out.println("Active Threads in Item Group: " + itemThreadGroup.activeCount());
                System.out.println("Active Threads in Shopper Group: " + shopperTheadGroup.activeCount());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     *  This method responsible to print the thread information
     */
    private static void printThreadInfoLog(){
        // print thread group and thread name
        System.out.println("Thread Group Name: " + Thread.currentThread().getThreadGroup().getName() + " Thread Name: " + Thread.currentThread().getName() + " is running");
    }

    /**
     * This method responsible to generate random quantity
     *
     * @param min min value
     * @param max max value
     * @return random quantity
     */
    private static int getRandomQuantity(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    /**
     * This method responsible to generate random price
     *
     * @param min min value
     * @param max max value
     * @return random price
     */
    private static double getRandomPrice(int min, int max) {
        return ThreadLocalRandom.current().nextDouble(min, max + 1);
    }
}
