package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.User;
import model.Order;


import com.mysql.cj.x.protobuf.MysqlxPrepare;
import config.DBConnectioni_UserInformation;

public class UserDAO {

    private Connection conn = DBConnectioni_UserInformation.getConnection();



    //    get Price from Database for compare to price product
    public double getBalance(int id){
        String Query = "SELECT Balance FROM UserBalance WHERE id =  ?";
        double temp_price = 0.00;
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
//            Take only one row beause id is only one have
            if(rs.next()){
                temp_price = rs.getDouble("Balance");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
//  What if the SQL fails and temp_price never gets a value?
        return temp_price;
    }
    //    update the price after Buy
    public boolean updateBalance(int id , double newBalance){
        String Query = "UPDATE UserBalance SET balance = ? WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setDouble(1,newBalance);
            ps.setInt(2,id);
            // Break Auto
            return ps.executeUpdate() > 0;
        } catch(SQLException e){
            e.printStackTrace();
        }

        // not in Try so it will return false in this
        return false;
    }

    //    Create Account
    public int createAccount(String email, String password , String fullname ,String telephone, String location , String role){
        String Query = "INSERT INTO Information(email , password , fullname , telephone, location , role) " +
                "VALUES(?,?,?,?,?,?)";
        int id;
        try(PreparedStatement ps = conn.prepareStatement(Query , Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,email);
            ps.setString(2,password);
            ps.setString(3,fullname);
            ps.setString(4,telephone);
            ps.setString(5,location);
            ps.setString(6,role);

           int Rowaffected = ps.executeUpdate();
           if(Rowaffected > 0){
               ResultSet rs = ps.getGeneratedKeys();

               if(rs.next()){
                   id = rs.getInt(1);
                   return id;
               }
           }else{
               System.out.println("❌ Wrong please Try again.......");
           }

        } catch(SQLException e){
            e.printStackTrace();
        }
        return -1;
    }
    public boolean Deposit(int id , double money){
        String Query = "INSERT INTO UserBalance(id , balance) VALUES(?,?)";
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setInt(1,id);
            ps.setDouble(2,money);
            return ps.executeUpdate() > 0;

        } catch (SQLException e){
            e.printStackTrace();
        }

        return false;

    }

//   Update Deposit if already have money
public boolean updateDeposit(int id, double money) {
    String query = "UPDATE UserBalance SET balance = ? WHERE id = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setDouble(1, money);
        ps.setInt(2, id);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}



//    get all row of table 1
    public int getRow(){
        String Query = "SELECT COUT(*) FROM Information";

        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ResultSet rs = ps.executeQuery();
            int count = 0;

            if(rs.next()){
                count = rs.getInt(1);
            }

            return count;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }

//    get Role
    public String getRole(int id){
        String Query = "SELECT role FROM Information WHERE id = ?";
        String role = null;
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                role = rs.getString("role");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return role;
    }

    /**
     *this function for login to use system and give the id for buying the product
     * @param  email for email , gmail whatever and as String type
     * @param  password for password user for login
     * @author Rayuth Developer
     * @since 2026.08.09
     * */
    public int login(String email, String password){
        String query = "SELECT id ,  email , password FROM Information WHERE email = ? AND password = ?";
        int user_id;
        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1,email);
            ps.setString(2,password);

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String email_user = rs.getString("email");
                String password_user = rs.getString("password");
                user_id = rs.getInt("id");
                if(email.equals(email_user) && password.equals(password_user)){
                    return user_id;
                }else{
                    System.out.println("❌ Wrong please Try again.......");
                }
            }


        } catch (SQLException e){
            e.printStackTrace();
        }

        return -1;
    }

//  decrease price
    public boolean decreaseBalance(int id , double balance_new){
        String Query = "UPDATE UserBalance SET balance = ? WHERE id = ?";

        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setDouble(1,balance_new);
            ps.setInt(2,id);

            return ps.executeUpdate() > 0;

        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

//    Check Profile
public User checkProfile(int id) {

    String query = "SELECT email, password, fullname, telephone, location, role " +
            "FROM Information WHERE id = ?";

    try (PreparedStatement ps = conn.prepareStatement(query)) {

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

            String gmail = rs.getString("email");
            String password = rs.getString("password");
            String fullname = rs.getString("fullname");
            String telephone = rs.getString("telephone");
            String location = rs.getString("location");
            String role = rs.getString("role");

            User pd = new User(
                    gmail,
                    password,
                    fullname,
                    telephone,
                    location,
                    role
            );

            return pd;
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return null;
}


// Insert  Product
public boolean InsertOrder(int id, String nameProduct, double productPrice, int quantity, double amount) {
    String query = "INSERT INTO OrderUser (user_id, nameProduct, productPrice, quantity, amount) " +
            "VALUES (?, ?, ?, ?, ?)";

    try (PreparedStatement ps = conn.prepareStatement(query)) {
        ps.setInt(1, id);
        ps.setString(2, nameProduct);
        ps.setDouble(3, productPrice);
        ps.setInt(4, quantity);
        ps.setDouble(5, amount);

        return ps.executeUpdate() > 0;

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return false;
}

//    get all order
    public List<Order> getAllOrder (int id){
        String Query = "SELECT user_id , nameProduct , productPrice , quantity , amount " +
                "FROM OrderUser WHERE user_id = ? ";
        List<Order> ods = new ArrayList<>();
        try(PreparedStatement ps = conn.prepareStatement(Query)){
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
            String nameProduct = rs.getString("nameProduct");
            double productPrice = rs.getDouble("productPrice");
            int quantity = rs.getInt("quantity");
            double amount = rs.getDouble("amount");
            Order od = new Order(id , nameProduct , productPrice , quantity , amount);
            ods.add(od);
        }

          return ods;

        } catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
