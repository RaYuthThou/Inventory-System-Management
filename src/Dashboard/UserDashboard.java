package Dashboard;

import service.Services;

import java.util.Scanner;
import java.util.InputMismatchException;

public class UserDashboard {

    static final String RESET   = "\u001B[0m";
    static final String BOLD    = "\u001B[1m";
    static final String DIM     = "\u001B[2m";

    static final String CYAN    = "\u001B[36m";
    static final String GREEN   = "\u001B[32m";
    static final String YELLOW  = "\u001B[33m";
    static final String RED     = "\u001B[31m";
    static final String BLUE    = "\u001B[34m";
    static final String MAGENTA = "\u001B[35m";
    static final String WHITE   = "\u001B[37m";
    public static final String PURPLE = "\u001B[35m";
    private static final Scanner scanner = new Scanner(System.in);
    private static final Services ps = new Services();
    private static int boxWidth = 70; // total width between ╔ and ╗

    public void uiMenuUser() {
        String title = " USER DASHBOARD ";


        printBorder('╔', '╗', title, boxWidth);

        printMenuItem(1, "Show all Product",       CYAN);
        printMenuItem(2, "Purchase",         GREEN);
        printMenuItem(3, "Search Product By ID ",         YELLOW);
        printMenuItem(4, "Search Product By Name",        YELLOW);
        printMenuItem(5, "Check Account",      CYAN);;
        printMenuItem(6, "View Order",  RED);
        printMenuItem(0, "Quit",                   PURPLE);

        printBorder('╚', '╝', null, boxWidth);
    }

    public void chooseFunction(String role , int id){
        if(!"user".equals(role)){
            return;
        }
        int numChoose;
        System.out.println("\n");


        while (true) {
            try {
                System.out.print(YELLOW + "\t\t\t\t\t\t\t (Choose 0 - 6) >> " + RESET);
                numChoose = scanner.nextInt();
                printBorder('╚', '╝', null, boxWidth);
                switch (numChoose) {
                    case 1 -> {
                        ps.showAllProduct();
                    }
                    case 2 -> {
                        ps.Purchase(id);
                    }
                    case 3 -> {
                        ps.getProductByid();
                    }
                    case 4 -> {
                        ps.getProductByName();
                    }
                    case 5 -> {
                        ps.checkProfile(id);
                    }
                    case 6 -> {
                        ps.showAllOrder(id);
                    }
                    case 0 -> {
                        System.out.println(RED + "\n\t\t\t Exiting..." + RESET);
                        return;
                    }
                    default -> {
                        System.out.println(
                                "\n\t\t\t Wrong Choose, please try again...."
                        );
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println(
                        RED + "\n\t\t\t Invalid input! Please enter a number." + RESET
                );

                scanner.nextLine(); // clear invalid input
            }
        }
    }





    private void printBorder(char left, char right, String title, int width) {
        StringBuilder sb = new StringBuilder();
        sb.append(BLUE).append('\t').append(left);

        if (title != null) {
            int pad = (width - title.length()) / 2;
            for (int i = 0; i < pad; i++) sb.append('═');
            sb.append(RESET).append(BOLD).append(title).append(RESET).append(BLUE);
            for (int i = 0; i < width - pad - title.length(); i++) sb.append('═');
        } else {
            for (int i = 0; i < width; i++) sb.append('═');
        }

        sb.append(right).append(RESET);
        System.out.println(sb);
    }

    private void printMenuItem(int number, String label, String color) {
        System.out.printf(
                "\t\t\t\t\t\t%s[ %2d ]%s %s%s%s%n%n",
                BLUE, number, RESET, color, "\t" + label, RESET
        );
    }
}
