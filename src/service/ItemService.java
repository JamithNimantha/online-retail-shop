package service;

import exception.ItemNotFoundException;
import model.Item;

import java.util.Set;

/**
 * @author Jamith Nimantha
 */
public interface ItemService extends SuperService{

    /**
     * Add item
     *
     * @param item item
     */
    void addItem(Item item);

    /**
     * Update item quantity
     *
     * @param itemID item id
     * @param quantity  quantity
     */
    void updateItemQuantity(String itemID, int quantity);

    /**
     * Delete item
     * @param item item
     */
    void deleteItem(Item item);

    /**
     * Get item by item id
     * @param itemID item id
     * @return item
     * @throws ItemNotFoundException
     */
    Item getItem(String itemID) throws ItemNotFoundException;

    /**
     * Get all items
     *
     * @return set of items
     */
    Set<Item> getItems();
}
