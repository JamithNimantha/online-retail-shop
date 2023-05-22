package util;

import java.util.Scanner;

/**
 * This class contains the utility methods
 *
 * @author Jamith Nimantha
 */
public class Utility {
    private Utility() {
    }

    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String CLEAR_CONSOLE = "\033[H\033[2J";
    private static final String WINDOWS = "Windows";
    private static final String MACOS = "Mac OS X";
    public static final String IDEA = "IDEA";


    /**
     * This method is used to clear the console
     */
    public static void clearConsole() {
        try {
            if (isIDEA()) {
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            } else {
                if (OS.contains(WINDOWS)) {
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else if (OS.contains(MACOS)) {
                    new ProcessBuilder("sh", "-c", "clear").inheritIO().start().waitFor();
                } else {
                    System.out.print(CLEAR_CONSOLE);
                    System.out.flush();
                }
            }


        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the IDE is IntelliJ IDEA
     *
     * @return true if the IDE is IntelliJ IDEA
     */
    private static boolean isIDEA() {
        return System.getProperty("ide.name", IDEA).equals(IDEA);
    }

    public static void waitTillThreadDie(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void pressAnyKeyToContinue(Scanner scanner) {
        System.out.print("\n\t Press any key to continue >");
        scanner.nextLine();
    }
}
