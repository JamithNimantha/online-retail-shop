package exception;

/**
 * This is a custom exception class for item not found
 *
 * @author Jamith Nimantha
 */
public class ItemNotFoundException extends RuntimeException {


    public ItemNotFoundException(String itemId) {
        super("\tItem with ID " + itemId + " not found!");
    }

}
