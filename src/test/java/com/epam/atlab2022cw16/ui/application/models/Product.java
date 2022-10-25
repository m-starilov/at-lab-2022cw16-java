package com.epam.atlab2022cw16.ui.application.models;

import java.util.List;
import java.util.Objects;

public class Product {
    private String productName;
    private Double productPrice;
    private List<String> productColor;

    public static class Builder {
        private String productName;
        private Double productPrice;
        private List<String> productColor;

        private Builder() {}

        public Builder setProductName(String productName) {
            this.productName = productName;
            return this;
        }

        public Builder setProductPrice(Double productPrice) {
            this.productPrice = productPrice;
            return this;
        }

        public Builder setProductColor(List<String> productColor) {
            this.productColor = productColor;
            return this;
        }

        public Product build() {
            Product product = new Product();
            product.setProductName(productName);
            product.setProductPrice(productPrice);
            product.setProductColor(productColor);
            return product;
        }

    }

    public static Builder create()
    {
        return new Builder();
    }

    public String getProductName() {
        return productName;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public List<String> getProductColor() {
        return productColor;
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

    void setProductName(String productName) {
        this.productName = productName;
    }

    void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    void setProductColor(List<String> productColor) {
        this.productColor = productColor;
    }

}
