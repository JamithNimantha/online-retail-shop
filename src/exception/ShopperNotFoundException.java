package exception;

/**
 * This is a custom exception class for shopper not found
 *
 * @author Jamith Nimantha
 */
public class ShopperNotFoundException extends RuntimeException  {

    public ShopperNotFoundException(String shopperId) {
        super("\tShopper with ID " + shopperId + " not found!");
    }
}
