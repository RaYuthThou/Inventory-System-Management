package ui;
import java.util.InputMismatchException;
import java.util.Scanner;
import service.Services;
import dao.*;
import Dashboard.*;


public class Menu {

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
    private static final Scanner scanner = new Scanner(System.in);
    private static final Services ps = new Services();
    private static  final UserDAO ds = new UserDAO();


//  Head manu
    public  void Menu() {

        System.out.print(" \n\n\t\t\t\t\t\t[ 1 ] "); System.out.print( YELLOW + "\tRegister" + RESET);
        System.out.print(" \n\n\t\t\t\t\t\t[ 2 ] "); System.out.print( GREEN + "\tLogin" + RESET);
        System.out.print(" \n\n\t\t\t\t\t\t[ 3 ] "); System.out.print( RED + "\tQuite System\n\n" + RESET);

    }

    public void loadingAnimation() throws InterruptedException {

        String green = "\u001B[32m";
        String reset = "\u001B[0m";

        String[] frames = {
                "[=    ]",
                "[ =   ]",
                "[  =  ]",
                "[   = ]",
                "[    =]",
                "[   = ]",
                "[  =  ]",
                "[ =   ]"
        };

        for (int i = 0; i < 16; i++) {

            System.out.print(
                    "\r\t\t\t\t\t\t\t\t\t" +
                            green +
                            frames[i % frames.length] +
                            reset
            );

            System.out.flush();

            Thread.sleep(120);
        }

        System.out.print("\r\t\t\t\t\t\t\t\t        \r");
        System.out.flush();
    }


//    main Logic
public void mainLogic(){
    String title = " INVENTORY SYSTEM MANAGEMENT ";
    int boxWidth = 70;
    int id_user;
    String role;
    int newChoose;

    AdminDashboard adminDashboard = new AdminDashboard();
    UserDashboard userDashboard = new UserDashboard();

    printBorder('╔', '╗', title, boxWidth);
    Menu();

    while (true) {
        try {
            System.out.print(YELLOW + "\t\t\t\t\t\t\t (Choose 1 - 3) >> " + RESET);
            newChoose = scanner.nextInt();
            printBorder('╚', '╝', null, boxWidth);
            switch (newChoose) {
                case 1 -> {
                    ps.register();
                    // after registering, show menu again
                    Menu();
                }
                case 2 -> {
                    // login() should return the logged-in user's id
                    id_user = ps.login();

                    if (id_user != -1) { // -1 (or whatever) = failed login
                        role = ds.getRole(id_user); // fetch role for that id

                        if ("Admin".equals(role)) {
                            clearScreen();
                            adminDashboard.uiMenuAdmin();
                            adminDashboard.chooseFunction(role, id_user);
                        } else if ("user".equals(role)) {
                            clearScreen();
                            userDashboard.uiMenuUser();
                            userDashboard.chooseFunction(role, id_user);
                        }
                    } else {
                        System.out.println(RED + "\n\t\t\t Login failed, try again." + RESET);
                    }
                    clearScreen();
                    Menu(); // show head menu again after dashboard exits
                }
                case 3 -> {
                    System.out.println(RED + "\n\t\t\t Exiting System..." + RESET);
                    return;
                }
                default -> System.out.println("\n\t\t\t Wrong Choose, please try again....");
            }
        } catch (InputMismatchException e) {
            System.out.println(RED + "\n\t\t\t Invalid input! Please enter a number." + RESET);
            scanner.nextLine();
        }
    }
}




    public static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
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
