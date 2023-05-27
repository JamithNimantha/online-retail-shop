package exception;

/**
 * This is a custom exception class for checkout
 *
 * @author Jamith Nimantha
 */
public class CheckOutException extends RuntimeException{

        public CheckOutException(String message) {
            super(message);
        }
}
