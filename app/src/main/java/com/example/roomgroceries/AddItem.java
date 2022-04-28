package com.example.roomgroceries;

public class AddItem {
    private final String itemName;
    private final String itemQuantity;

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
}
