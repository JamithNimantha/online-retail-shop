package service.imp;

import exception.CheckOutException;
import exception.ShopperNotFoundException;
import model.Item;
import model.Shopper;
import service.ItemService;
import service.ShopperService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author Jamith Nimantha
 */
public class ShopperServiceImpl implements ShopperService {

    private final List<Shopper> shoppers = new ArrayList<>();
    private final ItemService itemService;
    private final ReentrantLock lock = new ReentrantLock();


    public ShopperServiceImpl(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Add item to cart
     *
     * @param shopperId shopper id
     * @param itemId    item id
     * @param quantity  quantity
     */
    @Override
    public synchronized void addItemToCart(String shopperId, String itemId, int quantity) {
        shoppers.stream()
                .filter(shopper -> shopper.getShopperId().equals(shopperId))
                .findFirst()
                .ifPresent(shopper -> {
                    shopper.getCart().put(itemService.getItem(itemId).getItemID(), quantity);
                    System.out.printf("\tItem with ID %s added to cart successfully..%n", itemId);
                });
    }

    /**
     * Remove item from cart
     *
     * @param shopperId shopper id
     * @param itemId    item id
     */
    @Override
    public synchronized void removeItemFromCart(String shopperId, String itemId) {
        shoppers.stream()
                .filter(shopper -> shopper.getShopperId().equals(shopperId))
                .findFirst()
                .ifPresent(shopper -> {
                    shopper.getCart().remove(itemId);
                    System.out.printf("\tItem with ID %s removed from cart successfully..%n", itemId);
                });
    }

    /**
     * Update item quantity
     *
     * @param shopperId shopper id
     * @param itemId    item id
     * @param quantity  quantity
     */
    @Override
    public synchronized void updateItemQuantity(String shopperId, String itemId, int quantity) {
        if (quantity < 1) {
            System.out.println("\tQuantity cannot be less than 1..");
            return;
        }
        shoppers.stream()
                .filter(shopper -> shopper.getShopperId().equals(shopperId))
                .findFirst()
                .ifPresent(shopper -> {
                    shopper.getCart().put(itemId, quantity);
                    System.out.printf("\tItem with ID %s updated successfully..%n", itemId);
                });
    }

    /**
     * View cart
     *
     * @param shopperId shopper id
     */
    @Override
    public synchronized void viewCart(String shopperId) {
        shoppers.stream()
                .filter(shopper -> shopper.getShopperId().equals(shopperId))
                .findFirst()
                .ifPresent(this::printCart);
    }

    /**
     * Checkout
     *
     * @param shopperId shopper id
     */
    @Override
    public synchronized void checkout(String shopperId) throws CheckOutException {
        try {
            // TODO: Remove the lock
            while (lock.isLocked()) {
                System.out.println("\tAnother shopper is checking out..");
                wait();
            }
            lock.lock();
            Shopper shopper = getShopper(shopperId);
            shopper.getCart().forEach((itemId, quantity) -> {
                Item item = itemService.getItem(itemId);
                if (item.getItemQuantity() < quantity) {
                    throw new CheckOutException(String.format("\tItem with ID %s is out of stock..%n", itemId));
                }
                item.setItemQuantity(item.getItemQuantity() - quantity);
            });
            System.out.println("\tCheckout:");
            printCart(shopper);
            shopper.getCart().clear();
            System.out.println("\tCart checkout completed successfully..");
            notifyAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Add new shopper
     *
     * @param shopper shopper
     * @return shopper id
     */
    @Override
    public synchronized String addNewShopper(Shopper shopper) {
        shoppers.add(shopper);
        return shopper.getShopperId();
    }

    /**
     * Get shopper
     *
     * @param shopperId shopper id
     * @return shopper
     */
    @Override
    public Shopper getShopper(String shopperId) {
        return shoppers.stream()
                .filter(shopper -> shopper.getShopperId().equals(shopperId))
                .findFirst()
                .orElseThrow(() -> new ShopperNotFoundException(shopperId));
    }

    /**
     * Get shoppers
     *
     * @return shoppers
     */
    @Override
    public List<Shopper> getShoppers() {
        return this.shoppers;
    }


    /**
     * Print cart
     *
     * @param shopper shopper
     */
    private synchronized void printCart(Shopper shopper) {
        System.out.println("Cart:");
        final String itemID = "Item ID";
        final String itemName = "Item Name";
        final String itemQuantity = "Quantity";
        final String itemPrice = "Price";

        // Determine maximum length for each column
        int idLength = Math.max(itemID.length(), shopper.getCart().keySet().stream()
                .map(String::length)
                .max(Integer::compare)
                .orElse(0));
        int nameLength = Math.max(itemName.length(), shopper.getCart().keySet().stream()
                .map(itemId -> itemService.getItem(itemId).getItemName()).map(String::length).max(Integer::compare).orElse(0));
        int quantityLength = Math.max(itemQuantity.length(), shopper.getCart().values().stream()
                .mapToInt(Integer::valueOf)
                .mapToObj(String::valueOf)
                .map(String::length)
                .max(Integer::compare)
                .orElse(0));
        int priceLength = Math.max(itemPrice.length(), shopper.getCart().entrySet().stream()
                .map( itemIdByQuantity -> itemService.getItem(itemIdByQuantity.getKey()).getItemPrice() * itemIdByQuantity.getValue())
                .mapToDouble(Double::valueOf)
                .mapToObj(String::valueOf)
                .map(String::length)
                .max(Integer::compare)
                .orElse(0));

        // Print header
        String dividerLine = String.format("+%s+%s+%s+%s+", "-".repeat(idLength + 2), "-".repeat(nameLength + 2), "-".repeat(quantityLength + 2), "-".repeat(priceLength + 2));
        System.out.println(dividerLine);
        System.out.printf(String.format("| %%-%ds | %%-%ds | %%-%ds | %%-%ds |\n", idLength, nameLength, quantityLength, priceLength), itemID, itemName, itemQuantity, itemPrice);
        System.out.println(dividerLine);

        // Print rows
        shopper.getCart().forEach((itemId, quantity) -> {
            Item item = itemService.getItem(itemId);
            System.out.printf("| %-" + idLength + "s | %-" + nameLength + "s | %-" + quantityLength + "d | %,-" + priceLength + ".2f |\n", item.getItemID(), item.getItemName(), quantity, item.getItemPrice() * quantity);
        });
        // Print footer
        System.out.println(dividerLine);
        System.out.println("\tTotal: " + shopper.getCart().entrySet().stream()
                .map(itemIdByQuantity -> itemService.getItem(itemIdByQuantity.getKey()).getItemPrice() * itemIdByQuantity.getValue())
                .reduce(0.0, Double::sum));
    }
}
