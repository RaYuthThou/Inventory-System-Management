package service;
import dao.ProductDAO;
import dao.UserDAO;
import model.Product;
import model.User;
import model.Order;
import ui.Menu;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class Services {

    // Color Syntax
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


    private Scanner scanner = new Scanner(System.in);
    private ProductDAO dao = new ProductDAO();
    private UserDAO dao1 = new UserDAO();
    private Menu ui = new Menu();

    private String role = "user";

    public int boxWidth = 70; // total width between ╔ and ╗




     // AddProduct only For Admin only
    public void addProduct(){
        String title = " Insert Product ";
          String Name;
          String Id;
          float Price;
          int Quantity;


          try{

              printBorder('╔', '╗', title, boxWidth);
              System.out.println();
              System.out.print( YELLOW + "\t\t\t[ 1 ] ==> ID : " + RESET); Id = scanner.next();
              scanner.nextLine(); // Clear Buffer
              System.out.print( YELLOW + "\t\t\t[ 2 ] ==> Name : " + RESET); Name = scanner.nextLine();
              System.out.print(YELLOW + "\t\t\t[ 3 ] ==> Price : " + RESET); Price = scanner.nextFloat();
              System.out.print(YELLOW + "\t\t\t[ 4 ] ==> Quantity : " + RESET); Quantity = scanner.nextInt();

              if(dao.insertProduct(Id,Name,Price,Quantity)){
                  System.out.println( GREEN + "\n\t\t\t Product Added....!" + RESET);
              }else{
                  System.out.println("\n\t Can't Add Product System Error please Check......");
              }
              printBorder('╚', '╝', null, boxWidth);

          } catch (InputMismatchException e){
              System.out.println("❌ Wrong input type! (Example: price must be number)");
              printBorder('╚', '╝', null, boxWidth);

          }
    }
    // Delete Product Only Admin
    public void  deleteProduct(){
        String Id;
        int width = 25;
        String title = "Delete Product ";
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();
        try{
              System.out.print( YELLOW + "\n\t\t\t [ 1 ] ==> ID : " + RESET); Id = scanner.next();

              if(dao.deleteProductById(Id)){
                  System.out.println( GREEN + "\n\t\t\t\t\t\t Product delete Successfully" + RESET);
              }else{
                  System.out.println( RED + "\n\t\t\t\t\t\t\t Can't delete product");
              }

              printBorder('╚', '╝', null, boxWidth);


        }catch (InputMismatchException e){
            System.out.println("❌ Wrong input type! (Example: price must be number)");
            printBorder('╚', '╝', null, boxWidth);
        }

    }

    // Purchase Admin and User in the same Time
    public void Purchase(int id_user){
        String title = " PURCHASE ";
        String id_product;
        int Quantity;
        double Amount;
        float price_product;
        double balance;
        String nameProduct;
        int idProduct;
        int width = 25;


        printBorder('╔', '╗', title, boxWidth);
        System.out.println();

        try{
            System.out.print("\n\t\t\t [ 1 ] ==> ID Product : "); id_product = scanner.next();
            System.out.print("\n\t\t\t [ 2 ] ==> Quantity : "); Quantity = scanner.nextInt();
            balance = dao1.getBalance(id_user);  // Take the balance user
            price_product = dao.getPriceProduct(id_product); // get Price Product
            nameProduct = dao.getNameProduct(id_product);
            Amount = price_product * Quantity;
            idProduct = Integer.parseInt(id_product);
            // Compare it to balance
            if(balance >= Amount){
                System.out.println( GREEN + "\n\t\t\t\t\t\t Purchase Successfully" + RESET);
                balance -= Amount;
                // Decrease to Balance user
                dao1.decreaseBalance(id_user , balance);
                // Decrease to Quantity user
                dao.decreaseQuantity(id_product , Quantity);
                dao1.InsertOrder(id_user, nameProduct , price_product , Quantity , Amount );


            }else{
                System.out.println("❌ You don't have engouh monney");
                printBorder('╚', '╝', null, boxWidth);
                return;
            }

            printBorder('╚', '╝', null, boxWidth);

        } catch(InputMismatchException e){
            System.out.println("❌ Wrong input type! (Example: Amount must be number)");
            printBorder('╚', '╝', null, boxWidth);

        }
    }

//    login or Register First
    public int register(){
        String email;
        String password;
        String location;
        String telephone;
        String fullname;
        int id_user = 0;
        double money;
        int width = 25;
        String title = " REGISTER ";
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();

        try{
            System.out.print("\n\t\t\t [ 1 ] ==> Email : "); email = scanner.next();
            scanner.nextLine(); // clear buffer
            System.out.print("\n\t\t\t [ 2 ] ==> Password : "); password = scanner.next();
            scanner.nextLine(); // Clear Buffer
            System.out.print("\n\t\t\t [ 3 ] ==> Fullname : "); fullname = scanner.nextLine();
            System.out.print("\n\t\t\t [ 4 ] ==> Telephone : "); telephone = scanner.nextLine();
            System.out.print("\n\t\t\t [ 5 ] ==> location : "); location = scanner.nextLine();
            System.out.print("\n\t\t\t [ 6 ] ==>  Money for Deposit : "); money = scanner.nextDouble();

            id_user = dao1.createAccount(email , password , fullname ,telephone, location,role);

            if(dao1.Deposit(id_user , money)){
                ui.loadingAnimation();
                System.out.println( GREEN + "\n\t\t\t\t\t\t Account Register Successfully" + RESET);
                return id_user;
            }else{
                System.out.println(RED+ "❌ Wrong input type! (Example: Amount must be number)"+RESET);
            }
            printBorder('╚', '╝', null, boxWidth);

        } catch (InputMismatchException | InterruptedException e){
            printBorder('╚', '╝', null, boxWidth);
        }
        return -1;
    }
//    login
    public  int login(){
        String title = " LOGIN ";
        List<Product> products = dao.getAllProduct();
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();
        String email;
        String password;
        int user_id = -1;
        int width = 25;


        try{
            System.out.print("\n\t\t\t [ 1 ] ==> Email : "); email = scanner.next();
            scanner.nextLine(); // clear buffer
            System.out.print("\n\t\t\t [ 2 ] ==> Password : "); password = scanner.next();

            user_id = dao1.login(email,password);

            if(user_id > 0){


                ui.loadingAnimation();
                System.out.println( GREEN + "\n\t\t\t\t\t\t Account Login Successfully" + RESET);

                printBorder('╚', '╝', null, boxWidth);

                return user_id;

            }



        }catch (InputMismatchException | InterruptedException e){
            System.out.println(RED+ "❌ Wrong input type! (Example: Amount must be number)"+RESET);
            printBorder('╚', '╝', null, boxWidth);
        }

        return user_id;
    }

//    Show All product


    public void showAllProduct() {
        String title = " SHOW ALL PRODUCT ";
        List<Product> products = dao.getAllProduct();
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();
        System.out.println("\t\t\t+------+----------------------+------------+----------+");
        System.out.printf("\t\t\t| %-4s | %-20s | %-10s | %-8s |%n",
                "ID", "Name", "Price", "Quantity");
        System.out.println("\t\t\t+------+----------------------+------------+----------+");

        if (products == null || products.isEmpty()) {
            System.out.printf("\t\t\t| %-46s |%n", "No products found");
        } else {

            for (Product ps : products) {
                System.out.printf(
                        "\t\t\t| %-4s | %-20s | %-10.2f | %-8d |%n",
                        ps.getId(),
                        ps.getUsername(),
                        ps.getPrice(),
                        ps.getQuntity()
                );
            }
        }

        System.out.println("\t\t\t+------+----------------------+------------+----------+");
        System.out.println();

        printBorder('╚', '╝', null, boxWidth);
    }

//    Update Product
    public void updateProduct(){
        String title = " UPDATE PRODUCT ";
        printBorder('╔', '╗', title, boxWidth);
        String name;
        String newId;
        String oldId;
        float price;
        int quantity;


        try{
            System.out.print("\n\t\t\t [ 1 ] ==> New ID : "); newId = scanner.next();
            System.out.print("\n\t\t\t [ 2 ] ==> Name Product : ");  name = scanner.nextLine();
            System.out.print("\n\t\t\t [ 3 ] ==> Price : "); price = scanner.nextFloat();
            System.out.print("\n\t\t\t [ 4 ] ==> Quantity : "); quantity = scanner.nextInt();
            System.out.print("\n\t\t\t [ 5 ] ==> Old ID : "); oldId= scanner.next();

            if(dao.Update(newId , name , price , quantity , oldId)){
                System.out.println( GREEN + "\n\t\t\t\t\t\t Updated Successfully" + RESET);

            }else{
                System.out.println(RED + "\n\t\t\t Updated false "+ RESET);
            }

            printBorder('╚', '╝', null, boxWidth);

        } catch (InputMismatchException e){
            System.out.println(RED + "\n\t Sorry you input type is wrong....." + RESET);
            printBorder('╚', '╝', null, boxWidth);
        }
    }

//    Update Quantity

    public void updateQuantity(){
        String id;
        int newQt;
        int amount;
        String title = " UPDATE QUANTITY ";

        printBorder('╔', '╗', title, boxWidth);

        try{
            System.out.print("\n\t\t\t [ 1 ] ==> Product ID : "); id = scanner.next();
            System.out.print("\n\t\t\t [ 2 ] ==> New Quantity : ");  newQt = scanner.nextInt();
            amount = newQt + dao.getQuantity(id);

            if(dao.updateQuntity(id,amount)){
                System.out.println( GREEN + "\n\t\t\t\t\t\t Quantity Updated Successfully" + RESET);
            }else{
                System.out.println(RED + "\n\t\t\t Updated false "+ RESET);
            }
            printBorder('╚', '╝', null, boxWidth);
        } catch (InputMismatchException e){
            System.out.println(RED + "\n\t Sorry you input type is wrong....." + RESET);
            printBorder('╚', '╝', null, boxWidth);
        }
    }

//    get product by id
    public void getProductByid(){
        String id;

        String title = " GET PRODUCT BY ID ";
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();


        try{

            System.out.print("\n\t\t\t [ 1 ] ==> Product ID : "); id = scanner.next();
            Product product  = dao.getProductById(id);
            if(product == null){
                System.out.println(RED + "\n\t Product not found ....." + RESET);
            }else{
                System.out.println("\t\t\t+------+----------------------+------------+----------+");
                System.out.printf("\t\t\t| %-4s | %-20s | %-10s | %-8s |%n",
                        "ID", "Name", "Price", "Quantity");
                System.out.println("\t\t\t+------+----------------------+------------+----------+");

                    System.out.printf(
                            "\t\t\t| %-4s | %-20s | %-10.2f | %-8d |%n",
                            product.getId(),
                            product.getUsername(),
                            product.getPrice(),
                            product.getQuntity()
                    );
                System.out.println("\t\t\t+------+----------------------+------------+----------+");
            }

        } catch (InputMismatchException e){
            System.out.println(RED + "\n\t Sorry you input type is wrong....." + RESET);
            printBorder('╚', '╝', null, boxWidth);
        }
        printBorder('╚', '╝', null, boxWidth);
    }

//    get Product by name
    public void getProductByName(){
        String name;
        String title = " GET PRODUCT BY NAME ";
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();

        try{
            System.out.print("\n\t\t\t [ 1 ] ==> Product Name : "); name = scanner.nextLine();
            List<Product> products = dao.getProductByName(name);
            if(products == null || products.isEmpty()){
                System.out.println(RED + "\n\t Product not found ....." + RESET);
            }else{
                for(Product ps : products){
                    System.out.println("\t\t\t+------+----------------------+------------+----------+");
                    System.out.printf("\t\t\t| %-4s | %-20s | %-10s | %-8s |%n",
                            "ID", "Name", "Price", "Quantity");
                    System.out.println("\t\t\t+------+----------------------+------------+----------+");

                    System.out.printf(
                            "\t\t\t| %-4s | %-20s | %-10.2f | %-8d |%n",
                            ps.getId(),
                            ps.getUsername(),
                            ps.getPrice(),
                            ps.getQuntity()
                    );
                    System.out.println("\t\t\t+------+----------------------+------------+----------+");
                }
            }


        }catch (InputMismatchException e){
            System.out.println(RED + "\n\t Sorry you input type is wrong....." + RESET);
            printBorder('╚', '╝', null, boxWidth);
        }
        printBorder('╚', '╝', null, boxWidth);

    }
// Check Profile
public void checkProfile(int id) {

    String title = " PROFILE INFORMATION ";
    printBorder('╔', '╗', title, 100);
    System.out.println();

    User pf = dao1.checkProfile(id);

    // Get data
    String email = pf.getGmail();
    String password = pf.getPassword();
    String fullname = pf.getFullname();
    String telephone = pf.getTelephone();
    String location = pf.getLocation();
    String role = pf.getRole();

    // Calculate column widths
    int emailW = Math.max("Email".length(), email.length());
    int passwordW = Math.max("Password".length(), password.length());
    int fullnameW = Math.max("Fullname".length(), fullname.length());
    int telephoneW = Math.max("Telephone".length(), telephone.length());
    int locationW = Math.max("Location".length(), location.length());
    int roleW = Math.max("Role".length(), role.length());

    // Top border
    System.out.printf(
            "\t\t+-%s-+-%s-+-%s-+-%s-+-%s-+-%s-+%n",
            "-".repeat(emailW),
            "-".repeat(passwordW),
            "-".repeat(fullnameW),
            "-".repeat(telephoneW),
            "-".repeat(locationW),
            "-".repeat(roleW)
    );

    // Header
    System.out.printf(
            "\t\t| %-" + emailW + "s | %-" +
                    passwordW + "s | %-" +
                    fullnameW + "s | %-" +
                    telephoneW + "s | %-" +
                    locationW + "s | %-" +
                    roleW + "s |%n",
            "Email",
            "Password",
            "Fullname",
            "Telephone",
            "Location",
            "Role"
    );

    // Middle border
    System.out.printf(
            "\t\t+-%s-+-%s-+-%s-+-%s-+-%s-+-%s-+%n",
            "-".repeat(emailW),
            "-".repeat(passwordW),
            "-".repeat(fullnameW),
            "-".repeat(telephoneW),
            "-".repeat(locationW),
            "-".repeat(roleW)
    );

    // Data
    System.out.printf(
            "\t\t| %-" + emailW + "s | %-" +
                    passwordW + "s | %-" +
                    fullnameW + "s | %-" +
                    telephoneW + "s | %-" +
                    locationW + "s | %-" +
                    roleW + "s |%n",
            email,
            password,
            fullname,
            telephone,
            location,
            role
    );

    // Bottom border
    System.out.printf(
            "\t\t+-%s-+-%s-+-%s-+-%s-+-%s-+-%s-+%n",
            "-".repeat(emailW),
            "-".repeat(passwordW),
            "-".repeat(fullnameW),
            "-".repeat(telephoneW),
            "-".repeat(locationW),
            "-".repeat(roleW)
    );

    printBorder('╚', '╝', null, 100);
}

// Deposit Money
    public void Deposit(int id){
        double money;
        String title = " DEPOSIT ";
        printBorder('╔', '╗', title, boxWidth);
        System.out.println();
        double userBalance = dao1.getBalance(id);

        try{
            if(userBalance == 0 || userBalance < 0){
                System.out.print("\n\t\t\t [ 1 ] ==> Money  : "); money = scanner.nextDouble();
                if(dao1.Deposit(id , money)){
                    System.out.println( GREEN + "\n\t\t\t\t\t\t Deposit Successfully" + RESET);
                    printBorder('╚', '╝', null, boxWidth);
                    return;
                }
            }else{
                System.out.print("\n\t\t\t [ 1 ] ==> Money  : "); money = scanner.nextDouble();
                double Amount = money + userBalance;
                if(dao1.updateDeposit(id , Amount)){
                    System.out.println( GREEN + "\n\t\t\t\t\t\t Deposit Successfully" + RESET);
                    printBorder('╚', '╝', null, boxWidth);
                    return;
                }
            }
        }catch (InputMismatchException e){
            System.out.println(RED + "\n\t Sorry you input type is wrong....." + RESET);
            printBorder('╚', '╝', null, boxWidth);
        }

    }

//    Show order
public void showAllOrder(int id) {

    String title = " ORDER HISTORY ";
    printBorder('╔', '╗', title, 100);
    System.out.println();

    List<Order> orders = dao1.getAllOrder(id);

    if (orders == null || orders.isEmpty()) {
        System.out.println("\t\tNo orders found.");
        printBorder('╚', '╝', null, 100);
        return;
    }

    // Calculate column widths based on header + all rows
    int idW = "Id_User".length();
    int nameW = "nameProduct".length();
    int priceW = "priceProduct".length();
    int quantityW = "quantity".length();
    int amountW = "Amount".length();

    for (Order o : orders) {
        idW = Math.max(idW, String.valueOf(o.getId()).length());
        nameW = Math.max(nameW, o.getNameProduct().length());
        priceW = Math.max(priceW, String.format("%.2f", o.getProductPrice()).length());
        quantityW = Math.max(quantityW, String.valueOf(o.getQuantity()).length());
        amountW = Math.max(amountW, String.format("%.2f", o.getAmount()).length());
    }

    // Top border
    System.out.printf(
            "\t\t+-%s-+-%s-+-%s-+-%s-+-%s-+%n",
            "-".repeat(idW),
            "-".repeat(nameW),
            "-".repeat(priceW),
            "-".repeat(quantityW),
            "-".repeat(amountW)
    );

    // Header
    System.out.printf(
            "\t\t| %-" + idW + "s | %-" +
                    nameW + "s | %-" +
                    priceW + "s | %-" +
                    quantityW + "s | %-" +
                    amountW + "s |%n",
            "Id_User",
            "nameProduct",
            "priceProduct",
            "quantity",
            "Amount"
    );

    // Middle border
    System.out.printf(
            "\t\t+-%s-+-%s-+-%s-+-%s-+-%s-+%n",
            "-".repeat(idW),
            "-".repeat(nameW),
            "-".repeat(priceW),
            "-".repeat(quantityW),
            "-".repeat(amountW)
    );

    // Data rows
    for (Order o : orders) {
        System.out.printf(
                "\t\t| %-" + idW + "s | %-" +
                        nameW + "s | %-" +
                        priceW + "s | %-" +
                        quantityW + "s | %-" +
                        amountW + "s |%n",
                o.getId(),
                o.getNameProduct(),
                String.format("%.2f", o.getProductPrice()),
                o.getQuantity(),
                String.format("%.2f", o.getAmount())
        );
    }

    // Bottom border
    System.out.printf(
            "\t\t+-%s-+-%s-+-%s-+-%s-+-%s-+%n",
            "-".repeat(idW),
            "-".repeat(nameW),
            "-".repeat(priceW),
            "-".repeat(quantityW),
            "-".repeat(amountW)
    );

    printBorder('╚', '╝', null, 100);
}














//    ui helper only

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
