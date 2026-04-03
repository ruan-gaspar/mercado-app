package com.mercadoapp.mercadoapp.model;
// Essa classe representa um item da compra mensal
public class OrderItem {
    private Integer id;
    private Integer orderId;
    private Integer productId;

    private int quantity;
    private Double unitPrice;
    private Double subtotal;

    public OrderItem(Integer id, Integer orderId, Integer productId, int quantity, Double unitPrice, Double subtotal) {
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
    public Double getUnitPrice() {
        return unitPrice;
    }
    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }
    public Double getSubtotal() {
        return subtotal;
    }
    public void setSubtotal(Double subtotal) {
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
