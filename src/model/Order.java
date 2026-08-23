package model;

public class Order {
    int id;
    String nameProduct;
    double productPrice;
    int quantity;
    double amount;

    public Order(){
        this.id = 0;
        this.nameProduct = null;
        this.productPrice = 0.00;
        this.quantity = 0;
        this.amount = 0;
    }
    public Order(int id , String nameProduct , double productPrice , int quantity , double amount){
        this.id = id;
        this.nameProduct = nameProduct;
        this.productPrice = productPrice;
        this.quantity = quantity;
        this.amount = amount;
    }

//    getter
public int getId() {
    return id;
}

    public String getNameProduct() {
        return nameProduct;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getAmount() {
        return amount;
    }

}
