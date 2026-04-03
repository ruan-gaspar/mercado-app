package com.mercadoapp.mercadoapp.model;

public class PlannedOrderItem {
    private Integer id;
    private Integer productId;
    private Integer plannedOrderId;
    private int plannedQuantity;
    private double expectedPrice;

    public PlannedOrderItem(Integer id, Integer productId, Integer plannedOrderId, int plannedQuantity, double expectedPrice) {
        this.id = id;
        this.productId = productId;
        this.plannedOrderId = plannedOrderId;
        this.plannedQuantity = plannedQuantity;
        this.expectedPrice = expectedPrice;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
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
    public double getExpectedPrice() {
        return expectedPrice;
    }
    public void setExpectedPrice(double expectedPrice) {
        this.expectedPrice = expectedPrice;
    }
    @Override
    public String toString() {
        return "Id: " + id +
                ", ProductId: " + productId +
                ", PlannedOrderId: " + plannedOrderId +
                ", PlannedQuantity: " + plannedQuantity +
               ", ExpectedPrice: " + expectedPrice;
    }
}
