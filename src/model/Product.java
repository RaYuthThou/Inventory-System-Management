package model;

public class Product {
    private String Id;
    private String username;
    private float price;
    private int quntity;


    // Constructure with Parameter
    // set Constructure to Public for use in other package
    public Product(String Id , String Name ,  float Price , int Quantity ){
        this.Id  = Id;
        this.username = Name;
        this.price = Price;
        this.quntity = Quantity;
    }

    // Constructure with no parameter
    public Product(){
        this.Id = null;
        this.username = null;
        this.price = 0;
        this.quntity = 0;
    }
    // setter
    public void setId(String Id){
        this.Id = Id;
    }
    public void setUsername(String Username){
        this.username = Username;
    }
    public void setPrice(float Price){
        this.price = Price;
    }
    public void setQuntity(int Quntity){
        this.quntity = Quntity;
    }

    // getter
    public String getId(){ return Id; }
    public String getUsername() { return username;}
    public float getPrice(){ return price;}
    public int getQuntity(){ return quntity;}

}
