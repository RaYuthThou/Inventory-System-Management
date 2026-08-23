package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import com.mysql.cj.x.protobuf.MysqlxPrepare;
import config.DBConnection;
import model.Product;

public class ProductDAO {
    private Connection conn = DBConnection.getConnection();

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

    // LOGIC SQL

    // Insert product Admin
    public boolean insertProduct(String id, String name, float price, int quantity) {
        String query = "INSERT INTO Product (Id, Name, Price, Quantity) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setFloat(3, price);
            ps.setInt(4, quantity);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // getProductByID User and Admin
    public Product getProductById(String Id){
        String Query = "SELECT * FROM Product WHERE Product.Id = ?";


         // Why use Only one because
        // Id is Primary Key only have one value


        // try catch Condition
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setString(1,Id);
            ResultSet rs = ps.executeQuery();

            // Only One Data Value
            if(rs.next()){
                String id = rs.getString("Id");
                String Name = rs.getString("Name");
                float price = rs.getFloat("Price");
                int Quantity = rs.getInt("Quantity");

                Product pd = new Product(id,Name,price,Quantity);

                // Return Value
                return pd;
            }

        }catch(SQLException e){
            e.printStackTrace();
        }

        // if  not found
        return null;
    }

    // getProductByname User / Admin
    public List<Product> getProductByName(String Name){
        // Deaclear Value
        String Query = "SELECT * FROM Product WHERE Product.Name LIKE?";
        List<Product> Products = new ArrayList<>();


        // try Catch
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setString(1,Name);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                String Id = rs.getString("Id");
                String name = rs.getString("Name");
                float price = rs.getFloat("Price");
                int Quantity = rs.getInt("Quantity");

                // Created Everytime when loop is running
                Product pt = new Product(Id,name,price,Quantity);

                Products.add(pt);

            }

        }catch(SQLException e){
            System.out.println(RED + "\n\t\t\t Not Working with SQL in getProductByname section....");
            return null;
        }
        return Products;

    }


    // getAllProduct // User and Admin
    public List<Product> getAllProduct(){
        String Query = "SELECT * FROM Product";
        List<Product> Products = new ArrayList<>();

        try(PreparedStatement ps = conn.prepareStatement(Query); ResultSet rs = ps.executeQuery()){
            while(rs.next()){
                String Id = rs.getString("Id");
                String name = rs.getString("Name");
                float price = rs.getFloat("Price");
                int Quantity = rs.getInt("Quantity");

                // Created Everytime when loop is running
                Product pt = new Product(Id,name,price,Quantity);

                Products.add(pt);
            }
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return Products;
    }


    // DeleteProductByID Admin Function
    public boolean deleteProductById(String Id){
        String Query = "DELETE FROM Product WHERE Product.Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){

            ps.setString(1,Id);
            return ps.executeUpdate() > 0;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Update // Admin Function
    public boolean Update(String newid , String name , float price , int quantity , String oldId ){
        String Query = "UPDATE Product SET Id = ? , Name = ? , Price = ? , Quantity = ? WHERE Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){

            ps.setString(1, newid);
            ps.setString(2, name);
            ps.setFloat(3, price);
            ps.setInt(4, quantity);
            ps.setString(5, oldId);
            return ps.executeUpdate() >0;

        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    // Admin Function
    public boolean updateQuntity(String Id , int newQt){
        String Query = "UPDATE Product SET Quantity = ? WHERE Id = ?";
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setInt(1, newQt);
            ps.setString(2, Id);

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }


    // Discrese  when buying Auto have when Buying from user this is a buy function
    public boolean decreaseQuantity(String id , int amount){
        String Query = "UPDATE Product SET Quantity = Quantity - ? WHERE Id = ? AND Quantity >= ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setInt(1,amount);
            ps.setString(2,id);
            ps.setInt(3,amount);

            return ps.executeUpdate() > 0;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    // Check Product Exsith by id Admin Function
    public boolean existById(String Id){
        String Query = "SELECT 1  FROM Product WHERE Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setString(1,Id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

//    get price of product
    public float getPriceProduct(String id){
        String query = "SELECT price FROM Product WHERE Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
               float price_product = rs.getFloat("Price");
               return price_product;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return -1;
    }


//    get all product from database
    public int getQuantity(String id){
        String query = "SELECT Quantity FROM Product WHERE Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int amountQuantity = rs.getInt("Quantity");
                return amountQuantity;
            }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

//   get name Product
    public String getNameProduct(String id){
        String Query = "SELECT Name FROM Product WHERE Id = ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setString(1 , id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                String name = rs.getString("Name");
                return name;
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
