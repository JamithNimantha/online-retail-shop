import ui.LogInUI;
import util.Utility;

import java.util.Scanner;

import static util.Utility.IDEA;


/**
 * @author Jamith Nimantha
 */
public class ConsoleApplication {
    public final static Scanner input = new Scanner(System.in);

    /**
     * This is the main method which is the entry point of the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        checkArgs(args);
        initApp();
        LogInUI.logIn(input);
    }

    private static void initApp() {
        Utility.DEBUG_ENABLE = true;
        Utility.INIT_SHOPPER = true;
        Utility.INIT_ITEM = true;
    }

    /**
     *  This method is used to check the arguments passed to the program
     *
     * @param args the command line arguments
     */
    private static void checkArgs(String[] args) {
        for (String arg : args) {
            if (IDEA.equals(arg)) {
                System.setProperty("ide.name", IDEA);
            }
        }
    }

}
