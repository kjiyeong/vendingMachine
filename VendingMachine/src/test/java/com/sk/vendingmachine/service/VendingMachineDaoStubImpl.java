/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.service;

import com.sk.vendingmachine.dao.VendingMachineDao;
import com.sk.vendingmachine.dao.VendingMachinePersistenceException;
import com.sk.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author steve
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    public Item testItem;
    public Item outOfStockItem;
    
    public VendingMachineDaoStubImpl() {
        testItem = new Item(1);
        testItem.setCost(new BigDecimal("1.25"));
        testItem.setItemName("Test Item");
        testItem.setInventoryStock(5);
        
        outOfStockItem = new Item(2);
        outOfStockItem.setCost(new BigDecimal(".80"));
        outOfStockItem.setItemName("Test Item Two");
        outOfStockItem.setInventoryStock(0);
    }
    
    public VendingMachineDaoStubImpl(Item testItem, Item outOfStockItem) {
        this.testItem = testItem;
        this.outOfStockItem = outOfStockItem;
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        List<Item> allItemList = new ArrayList<>();
        allItemList.add(testItem);
        allItemList.add(outOfStockItem);
        return allItemList;
    }

    @Override
    public Item getItem(int inventoryItem) throws VendingMachinePersistenceException {
        if (inventoryItem == testItem.getInventoryItem()) {
            return testItem;
        } else if (inventoryItem == outOfStockItem.getInventoryItem()) {
            return outOfStockItem;
        } else {
            return null;
        }
    }

    @Override
    public List<Item> getAvailableItems() throws VendingMachinePersistenceException {
        List<Item> availableItemList = new ArrayList<>();
        availableItemList.add(testItem);
        return availableItemList;
    }

    @Override
    public Item updateInventory(Item item) throws VendingMachinePersistenceException {
        int newStock = testItem.getInventoryStock() -1;
        testItem.setInventoryStock(newStock);
        return testItem;
    }
    
}
