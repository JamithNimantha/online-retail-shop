package service;

import model.Shopper;

/**
 * @author Jamith Nimantha
 */
public interface ShopperService extends SuperService{

    /**
     * Add item to cart
     *
     * @param shopperId shopper id
     * @param itemId item id
     * @param quantity quantity
     */
    void addItemToCart(String shopperId, String itemId, int quantity);

    /**
     * Remove item from cart
     *
     * @param shopperId shopper id
     * @param itemId item id
     */
    void removeItemFromCart(String shopperId, String itemId);

    /**
     * Update item quantity
     *
     * @param shopperId shopper id
     * @param itemId item id
     * @param quantity quantity
     */
    void updateItemQuantity(String shopperId, String itemId, int quantity);

    /**
     * View cart
     *
     * @param shopperId shopper id
     */
    void viewCart(String shopperId);

    /**
     * Checkout
     *
     * @param shopperId shopper id
     */
    void checkout(String shopperId);


    /**
     * Add new shopper
     *
     * @param shopper shopper
     * @return shopper id
     */
    String addNewShopper(Shopper shopper);
}
