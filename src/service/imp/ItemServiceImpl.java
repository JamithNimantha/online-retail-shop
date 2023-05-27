package service.imp;

import exception.ItemNotFoundException;
import model.Item;
import service.ItemService;

import java.util.*;

/**
 * This class is responsible to perform item related functionalities
 *
 * @author Jamith Nimantha
 */
public class ItemServiceImpl implements ItemService {
    private final Set<Item> items = new HashSet<>();

    /**
     * Get item by item id
     * @param item item to be added
     */
    @Override
    public synchronized void addItem(Item item) {
        if (items.contains(item)) {
            System.out.printf("\tItem with ID %s already exists..%n", item.getItemID());
            return;
        }
        items.add(item);
        System.out.printf("%n%n\tItem with ID %s added successfully..%n", item.getItemID());
    }

    /**
     * Update item quantity
     *
     * @param itemID item id
     * @param quantity  quantity
     */
    @Override
    public synchronized void updateItemQuantity(String itemID, int quantity) {
        Item item = getItem(itemID);
        item.setItemQuantity(quantity);
        System.out.printf("\tItem with ID %s updated successfully..%n", itemID);
    }


    /**
     * Delete item
     *
     * @param item item to be deleted
     */
    @Override
    public synchronized void deleteItem(Item item) {
        items.remove(item);
        System.out.printf("\tItem with ID %s deleted successfully..%n", item.getItemID());
    }

    /**
     * Get item by item id
     *
     * @param itemID item id
     */
    @Override
    public synchronized Item getItem(String itemID) throws ItemNotFoundException {
        return items.stream().filter(item -> item.getItemID().equals(itemID)).findFirst().orElseThrow(() -> new ItemNotFoundException(itemID));
    }

    /**
     * Get all items
     *
     * @return set of items
     */
    @Override
    public synchronized Set<Item> getItems()  {
        return items;
    }
}
