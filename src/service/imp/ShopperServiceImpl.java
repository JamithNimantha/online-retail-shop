package service.imp;

import model.Item;
import model.Shopper;
import service.ItemService;
import service.ShopperService;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jamith Nimantha
 */
public class ShopperServiceImpl implements ShopperService {

    private final List<Shopper> shoppers = new ArrayList<>();
    private final ItemService itemService;

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
                    System.out.printf("Item with ID %s added to cart successfully..%n", itemId);
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
                    System.out.printf("Item with ID %s removed from cart successfully..%n", itemId);
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
            System.out.println("Quantity cannot be negative..");
            return;
        }
        shoppers.stream()
                .filter(shopper -> shopper.getShopperId().equals(shopperId))
                .findFirst()
                .ifPresent(shopper -> {
                    shopper.getCart().put(itemId, quantity);
                    System.out.printf("Item with ID %s updated successfully..%n", itemId);
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
    public synchronized void checkout(String shopperId) {

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
     * Print cart
     *
     * @param shopper shopper
     */
    private synchronized void printCart(Shopper shopper) {
        System.out.println("Cart:");
        final String itemID = "Item ID";
        final String itemName = "Item Name";
        final String itemQuantity = "Quantity";
        // Determine maximum length for each column
        int idLength = Math.max(itemID.length(), shopper.getCart().keySet().stream().map(String::length).max(Integer::compare).orElse(0));
        int nameLength = Math.max(itemName.length(), shopper.getCart().keySet().stream().map(itemId -> itemService.getItem(itemID).getItemName()).map(String::length).max(Integer::compare).orElse(0));
        int quantityLength = Math.max(itemQuantity.length(), shopper.getCart().values().stream().mapToInt(Integer::valueOf).mapToObj(String::valueOf).map(String::length).max(Integer::compare).orElse(0));

        // Print header
        String dividerLine = String.format("+%s+%s+%s+", "-".repeat(idLength + 2), "-".repeat(nameLength + 2), "-".repeat(quantityLength + 2));
        System.out.println(dividerLine);
        System.out.printf(String.format("| %%-%ds | %%-%ds | %%-%ds |\n", idLength, nameLength, quantityLength), itemID, itemName, itemQuantity);
        System.out.println(dividerLine);

        // Print rows
        shopper.getCart().forEach((itemId, quantity) -> {
            Item item = itemService.getItem(itemId);
            System.out.printf("| %-" + idLength + "s | %-" + nameLength + "s | %-" + quantityLength + "d |\n", item.getItemID(), item.getItemName(), quantity);
        });
        // Print footer
        System.out.println(dividerLine);
    }
}
