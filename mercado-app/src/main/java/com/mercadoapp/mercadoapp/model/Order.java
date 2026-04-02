package com.mercadoapp.mercadoapp.model;
// Essa classe representa uma compra mensal
public class Order {
    private Integer id;
    private String monthReference;
    private String createdAt;
    private double totalAmount;

    public Order(Integer id, String monthReference, String createdAt, double totalAmount) {
      this.id = id;
      this.monthReference = monthReference;
      this.createdAt = createdAt;
      this.totalAmount = totalAmount;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getMonthReference() {
        return monthReference;
    }
    public void setMonthReference(String monthReference) {
        this.monthReference = monthReference;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
    public double getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    @Override
    public String toString(){
        return "Id: " + id +
                ", MonthReference: " + monthReference +
                ", CreatedAt: " + createdAt +
                ", TotalAmount: " + totalAmount;
    }
}
