package exception;

/**
 * @author Jamith Nimantha
 */
public class ItemNotFoundException extends RuntimeException {


    public ItemNotFoundException(String itemId) {
        super("\tItem with ID " + itemId + " not found!");
    }

}
