package model;

public class User {
    String gmail;
    String password;
    String fullname;
    String telephone;
    String location;
    String role;

//     default Contruture
   public User(){
        this.gmail = null;
        this.password = null;
        this.fullname = null;
        this.telephone = null;
        this.location = null;
        this.role = null;
    }
    // Constructure with Parameter
    public User(String gmail , String password, String fullname , String telephone , String location , String role){
        this.gmail = gmail;
        this.password = password;
        this.fullname = fullname;
        this.telephone = telephone;
        this.location = location;
        this.role = role;

    }
//    Setter
    public void setGmail(String gmail) {
        this.gmail = gmail;
    }
    public void setPassword(String Password){
        this.password =Password;
    }
    public void setFullname(String fullname){ this.fullname = fullname;}
    public void setTelephone(String telephone){ this.telephone = telephone; }
    public void setLocation(String location){ this.location = location; }
    public void setRole(String role){ this.role = role; }
//     Getter
    public String getGmail(){ return gmail;}
    public String getPassword(){return password;}
    public String getFullname(){ return fullname;}
    public String getTelephone(){ return telephone;}
    public String getLocation(){return location;}
    public String getRole(){ return role;}

}
