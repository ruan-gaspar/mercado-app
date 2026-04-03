package com.mercadoapp.mercadoapp.model;
// Essa classe trata da lista de compras planejada para o mercado
public class PlannedOrder {
    private Integer id;
    private String productName;
    private int quantity;
    private Double expectedPrice;

    public PlannedOrder(Integer id, String productName, int quantity, Double expectedPrice) {
        this.id = id;
        this.productName = productName;
        this.quantity = quantity;
        this.expectedPrice = expectedPrice;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public Double getExpectedPrice() {
        return expectedPrice;
    }
    public void setExpectedPrice(Double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }
    @Override
    public String toString(){
        return "Id: " + id +
                ", Product: " + productName +
                ", Quantity: " + quantity +
                ", ExpectedPrice: " + expectedPrice;
    }
}
