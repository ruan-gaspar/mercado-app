package com.mercadoapp.mercadoapp.model;
// Essa classe representa um setor/corredor do mercado onde se localizam os produtos por categoria
public class Sector {
    private Integer id;
    private String name;

    public Sector(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Integer getId(){
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return "Id: " + id +
                ", Name: " + name;
    }
}
