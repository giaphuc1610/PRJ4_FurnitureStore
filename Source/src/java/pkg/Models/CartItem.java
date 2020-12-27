package pkg.Models;

import java.math.BigDecimal;
import pkg.Entities.Products;

public class CartItem {
  private String productID;
  private String productName;
  private String image;
  private String category;
  private String type;
  private String brand;
  private double price;
  private int quantity;
  
  public CartItem() {}
  
  public CartItem(Products item, int quantity) {
    this.productID = new String(item.getProductID());
    this.productName = new String(item.getProductName());
    this.image = new String(item.getProductImages());
    this.category = new String(item.getCategoryID().getCategoryName());
    this.type = new String(item.getTypeID().getTypeName());
    this.brand = new String(item.getBrandID().getBrandName());
    this.price = item.getPrice();
    this.quantity = quantity;
  }

  
  public double getPrice() { return this.price; }


  
  public void setPrice(double price) { this.price = price; }


  
  public String getProductID() { return this.productID; }


  
  public void setProductID(String productID) { this.productID = productID; }


  
  public String getProductName() { return this.productName; }


  
  public void setProductName(String productName) { this.productName = productName; }


  
  public String getImage() { return this.image; }


  
  public void setImage(String image) { this.image = image; }


  
  public String getCategory() { return this.category; }


  
  public void setCategory(String category) { this.category = category; }


  
  public String getType() { return this.type; }


  
  public void setType(String type) { this.type = type; }


  
  public String getBrand() { return this.brand; }


  
  public void setBrand(String brand) { this.brand = brand; }


  
  public int getQuantity() { return this.quantity; }


  
  public void setQuantity(int quantity) { this.quantity = quantity; }
}
