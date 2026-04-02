package com.mercadoapp.mercadoapp.model;
// Essa classe representa um item da compra mensal
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;

    private int quantity;
    private double unitPrice;
    private double subtotal;

    public OrderItem(Integer id,Integer orderId, Integer productId, int quantity, double unitPrice, double subtotal) {
        this.id = id;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subtotal = subtotal;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getOrderId() {
        return orderId;
    }
    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public Integer getProductId() {
        return productId;
    }
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
    @Override
    public String toString() {
        return "Id: " + id +
                ", OrderId: " + orderId +
                ", ProductId: " + productId +
                ", Quantity: " + quantity +
                ", UnitPrice: " + unitPrice +
                ", Subtotal: " + subtotal;
    }
}
