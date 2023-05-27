package service;

/**
 * @author Jamith Nimantha
 */
public class UpdateItemRunner implements Runnable{

    private final ItemService itemService;

    private final String itemID;

    private final int quantity;
    public UpdateItemRunner(ItemService itemService, String itemID, int quantity) {
        this.itemService = itemService;
        this.itemID = itemID;
        this.quantity = quantity;
    }


    @Override
    public void run() {
        itemService.updateItemQuantity(itemID, quantity);
    }
}
