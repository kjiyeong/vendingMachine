/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dto;

import java.math.BigDecimal;
import java.util.Objects;


/**
 *
 * @author steve
 */
public class Item {

    private String itemName;
    private BigDecimal cost;
    private int inventoryItem;
    private int inventoryStock;

    public Item(int inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public int getInventoryItem() {
        return inventoryItem;
    }

    public void setInventoryItem(int inventoryItem) {
        this.inventoryItem = inventoryItem;
    }

    public int getInventoryStock() {
        return inventoryStock;
    }

    public void setInventoryStock(int inventoryStock) {
        this.inventoryStock = inventoryStock;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.itemName);
        hash = 11 * hash + Objects.hashCode(this.cost);
        hash = 11 * hash + this.inventoryItem;
        hash = 11 * hash + this.inventoryStock;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Item other = (Item) obj;
        if (this.inventoryItem != other.inventoryItem) {
            return false;
        }
        if (this.inventoryStock != other.inventoryStock) {
            return false;
        }
        if (!Objects.equals(this.itemName, other.itemName)) {
            return false;
        }
        if (!Objects.equals(this.cost, other.cost)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Item{" + "itemName=" + itemName + ", cost=" + cost + ", inventoryItem=" + inventoryItem + ", inventoryStock=" + inventoryStock + '}';
    }
        
}
