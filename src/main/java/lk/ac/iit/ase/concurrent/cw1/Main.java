package lk.ac.iit.ase.concurrent.cw1;

import java.util.Scanner;

/**
 * @author Jamith Nimantha
 */// Press ⇧ twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        printMenu();
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void printMenu() {
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.printf("\t|%63s %22s %1s%n", "Online Retail Shop", "","|");
        System.out.println("\t-----------------------------------------------------------------------------------------");
        System.out.println();
        System.out.println("\t1. Add a new item to the shop");
        System.out.print("\n\tEnter an option to continue > ");
        int op = input.nextInt();
        clearConsole();
    }

}
