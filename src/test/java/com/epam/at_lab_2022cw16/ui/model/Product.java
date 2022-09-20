package com.epam.at_lab_2022cw16.ui.model;

import java.util.List;
import java.util.Objects;

public class Product {
    private String productName;
    private Double productPrice;
    private List<String> productColor;

    public Product() {
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public List<String> getProductColor() {
        return productColor;
    }

    public void setProductColor(List<String> productColor) {
        this.productColor = productColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productName.equals(product.productName) && productPrice.equals(product.productPrice) && productColor.equals(product.productColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productPrice, productColor);
    }

    @Override
    public String toString() {
        return "Product{" +
                "productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productColor=" + productColor.toString() +
                '}';
    }

}
