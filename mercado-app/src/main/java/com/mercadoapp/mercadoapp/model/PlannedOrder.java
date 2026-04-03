package com.mercadoapp.mercadoapp.model;
// Essa classe representa a lista de compras planejada
public class PlannedOrder {
    private Integer id;
    private String name;
    private String createdAt;

    public PlannedOrder(Integer id, String name, String createdAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
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

    @Override
    public String toString(){
        return "Id: " + id +
                ", Name: " + name +
                ", CreatedAt: " + createdAt;
    }
}
