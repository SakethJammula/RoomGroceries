package com.example.roomgroceries;

public class AddItem {
    private final String itemName;
    private final String itemQuantity;

    public AddItem() {
        this.itemName = null;
        this.itemQuantity = null;
    }

    public AddItem(String itemName, String itemQuantity) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public static class OrderCost {
        float totalCost;

        public OrderCost() {
            this.totalCost = 0.0f;
        }

        public OrderCost(float totalCost) {
            this.totalCost = totalCost;
        }

        public float getTotalCost() {
            return totalCost;
        }
    }
}
