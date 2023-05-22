package service.imp;

import exception.ItemNotFoundException;
import model.Item;
import service.ItemService;

import java.util.*;

/**
 * @author Jamith Nimantha
 */
public class ItemServiceImpl implements ItemService {
    private final Set<Item> items = new HashSet<>();

    /**
     *
     * @param item item to be added
     */
    @Override
    public synchronized void addItem(Item item) {
        if (items.contains(item)) {
            System.out.printf("Item with ID %s already exists..%n", item.getItemID());
            return;
        }
        items.add(item);
        System.out.printf("%n%n\tItem with ID %s added successfully..%n", item.getItemID());
    }

    /**
     * @param itemID
     * @param quantity
     */
    @Override
    public synchronized void updateItemQuantity(String itemID, int quantity) {
        Item item = getItem(itemID);
        item.setItemQuantity(quantity);
        System.out.printf("Item with ID %s updated successfully..%n", itemID);
    }


    @Override
    public synchronized void deleteItem(Item item) {
        items.remove(item);
        System.out.printf("Item with ID %s deleted successfully..%n", item.getItemID());
    }

    /**
     * @param itemID
     */
    @Override
    public Item getItem(String itemID) throws ItemNotFoundException {
        return items.stream().filter(item -> item.getItemID().equals(itemID)).findFirst().orElseThrow(() -> new ItemNotFoundException(itemID));
    }

    /**
     * @return
     */
    @Override
    public Set<Item> getItems()  {
        return items;
    }
}
