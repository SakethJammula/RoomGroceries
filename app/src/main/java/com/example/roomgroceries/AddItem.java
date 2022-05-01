package com.example.roomgroceries;

public class AddItem {
    private final String itemName;
    private final String itemQuantity;
    private final String itemPrice;

    public AddItem() {
        this.itemName = null;
        this.itemQuantity = null;
        this.itemPrice = null;
    }

    public AddItem(String itemName, String itemQuantity, String itemPrice) {
        this.itemName = itemName;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
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

    public static class Users {
        String userName;
        float memberSpent;

        public Users() {
            this.userName = null;
            this.memberSpent = 0.0f;
        }

        public Users(String userName, float memberSpent) {
            this.userName = userName;
            this.memberSpent = memberSpent;
        }

        public String getUserName() {
            return userName;
        }

        public float getMemberSpent() {
            return memberSpent;
        }
    }
}
