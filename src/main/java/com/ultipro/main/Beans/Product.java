package com.ultipro.main.Beans;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Size;

@Document(collection = "Product")
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private long id;

    @NotBlank
    @Size(max = 100)
    private String productName;

    private String productImage;

    private String productDescription;

    private String productColor;

    private String priceForOneQuantity;

    @NotBlank
    @Size(max = 100)
    private String inventorySize;

    public static String getSequenceName() {
        return SEQUENCE_NAME;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getPriceForOneQuantity() {
        return priceForOneQuantity;
    }

    public void setPriceForOneQuantity(String priceForOneQuantity) {
        this.priceForOneQuantity = priceForOneQuantity;
    }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", productName='" + productName + '\'' + ", productDescription='" + productDescription + '\'' + ", productColor='"
                + productColor + '\'' + ", priceForOneQuantity='" + priceForOneQuantity + '\'' + ", inventorySize='" + inventorySize + '\'' + '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public String getInventorySize() {
        return inventorySize;
    }

    public void setInventorySize(String inventorySize) {
        this.inventorySize = inventorySize;
    }
}