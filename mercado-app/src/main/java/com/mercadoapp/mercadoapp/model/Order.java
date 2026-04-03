package com.mercadoapp.mercadoapp.model;
// Essa classe representa uma compra mensal
public class Order {
    private Integer id;
    private String name; //Para o usuário esse vai ser o mês da compra no input
    private String createdAt;
    private double totalAmount;

    public Order(Integer id, String name, String createdAt, double totalAmount) {
      this.id = id;
      this.name = name;
      this.createdAt = createdAt;
      this.totalAmount = totalAmount;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
                ", Name (monthReference): " + name +
                ", CreatedAt: " + createdAt +
                ", TotalAmount: " + totalAmount;
    }
}
