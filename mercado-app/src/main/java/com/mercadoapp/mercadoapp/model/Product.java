package com.mercadoapp.mercadoapp.model;

public class Product {
    private Integer id;
    private String name;
    private Double price;
    private String brand;
    private Integer categoryId;
    private Integer sectorId;

    public Product(Integer id, String name, Double price, String brand, Integer categoryId, Integer sectorId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.categoryId = categoryId;
        this.sectorId = sectorId;
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
    public double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
    public Integer getSectorId() {
        return sectorId;
    }
    public void setSectorId(Integer sectorId){
        this.sectorId = sectorId;
    }
    @Override
    public String toString(){
        return "Id: " + id +
                ", Name: " + name +
                ", Price: " + price +
                ", Brand: " + brand +
                ", CategoryId: " + categoryId +
                ", SectorId: " + sectorId;
    }
}
