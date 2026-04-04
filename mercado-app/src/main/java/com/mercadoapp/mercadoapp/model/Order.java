package com.mercadoapp.mercadoapp.model;

import java.time.LocalDateTime;

// Essa classe representa uma compra mensal
public class Order {
    private Integer id;
    private String monthReference;
    private LocalDateTime createdAt;
    private double totalAmount;

    public Order(Integer id, String monthReference, LocalDateTime createdAt, double totalAmount) {
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
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
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
