package Dashboard;
import java.util.Scanner;
import service.Services;
import ui.Menu;
import java.util.InputMismatchException;

public class AdminDashboard {
//    Function for user

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

    public static   int boxWidth = 70; // total width between ╔ and ╗

    public void uiMenuAdmin() {
        String title = " ADMIN DASHBOARD ";

        printBorder('╔', '╗', title, boxWidth);

        printMenuItem(1, "Show all Product",       CYAN);
        printMenuItem(2, "Insert Product",         GREEN);
        printMenuItem(3, "Update Product",         YELLOW);
        printMenuItem(4, "Update Quantity",        YELLOW);
        printMenuItem(5, "Search Product by ID",      CYAN);
        printMenuItem(6, "Search Product by Name",    CYAN);
        printMenuItem(7, "Check Account",          CYAN);
        printMenuItem(8, "Delete Product by ID",  RED);
        printMenuItem(0, "Quit",                   PURPLE);

        printBorder('╚', '╝', null, boxWidth);
    }

//    Choose the function
    public void chooseFunction(String role , int id ){
               if(!"Admin".equals(role)){
                   return;
               }
               int numChoose;
               System.out.println("\n");


        while (true) {
            try {
                System.out.print(YELLOW + "\t\t\t\t\t\t\t (Choose 0 - 8) >> " + RESET);
                numChoose = scanner.nextInt();
                printBorder('╚', '╝', null, boxWidth);

                switch (numChoose) {
                    case 1 -> {
                        ps.showAllProduct();
                    }
                    case 2 -> {
                        ps.addProduct();
                    }
                    case 3 -> {
                        ps.updateProduct();
                    }
                    case 4 -> {
                        ps.updateQuantity();
                    }
                    case 5 -> {
                        ps.getProductByid();
                    }
                    case 6 -> {
                        ps.getProductByName();
                    }
                    case 7 -> {
                        ps.checkProfile(id);
                    }
                    case 8 -> {
                        ps.deleteProduct();
                    }
                    case 0 -> {
                        System.out.println(RED + "\n\t\t\t Exiting..." + RESET);
                        return;
                    }
                    default -> {
                        System.out.println("\n\t\t\t Wrong Choose, please try again....");
                    }
                }

            } catch (InputMismatchException e) {
                System.out.println(RED + "\n\t\t\t Invalid input! Please enter a number." + RESET);
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
