package service;

import exception.ItemNotFoundException;
import model.Item;

import java.util.Set;

/**
 * @author Jamith Nimantha
 */
public interface ItemService extends SuperService{

    void addItem(Item item);
    void updateItemQuantity(String itemID, int quantity);
    void deleteItem(Item item);
    Item getItem(String itemID) throws ItemNotFoundException;
    Set<Item> getItems();
}
