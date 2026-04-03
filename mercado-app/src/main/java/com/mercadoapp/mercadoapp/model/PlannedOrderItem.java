package com.mercadoapp.mercadoapp.model;

public class PlannedOrderItem {
    private Integer id;
    private String productId;
    private Integer plannedOrderId;
    private int plannedQuantity;

    public PlannedOrderItem(Integer id, String productId, Integer plannedOrderId, int plannedQuantity) {
        this.id = id;
        this.productId = productId;
        this.plannedOrderId = plannedOrderId;
        this.plannedQuantity = plannedQuantity;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getProductId() {
        return productId;
    }
    public void setProductId(String productId) {
        this.productId = productId;
    }
    public Integer getPlannedOrderId() {
        return plannedOrderId;
    }
    public void setPlannedOrderId(Integer plannedOrderId) {
        this.plannedOrderId = plannedOrderId;
    }
    public int getPlannedQuantity() {
        return plannedQuantity;
    }
    public void setPlannedQuantity(int plannedQuantity) {
        this.plannedQuantity = plannedQuantity;
    }
    @Override
    public String toString() {
        return "Id: " + id +
                "ProductId: " + productId +
                "PlannedOrderId: " + plannedOrderId +
                "PlannedQuantity: " + plannedQuantity;
    }
}
