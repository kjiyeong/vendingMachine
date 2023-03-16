/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dao;

import com.sk.vendingmachine.dto.Item;
import java.util.List;

/**
 *
 * @author steve
 */
public interface VendingMachineDao {
    
    /**
     * This method simply returns a list of all the items in the Items
     * @return a list of Item objects from the items.
     */
    List<Item> getAllItems() throws VendingMachinePersistenceException;  // keep this
    
    /**
     * This method returns the item stored in the items given the inventoryItem 
     * @param inventoryItem for the requested item
     * @return the item in the hashmap stored under that inventoryItem
     */
    Item getItem(int inventoryItem) throws VendingMachinePersistenceException; // keep this!
    
    /**
     * This method only returns those items that are available, such that they have
     * an attribute greater than 0 for quantity in stock
     * @return a list of items that are in stock
     */
    List<Item> getAvailableItems()throws VendingMachinePersistenceException;
    
    /**
     * This method simply updates the items after an item has been purchased.
     * So that the inventory can be updated by decreasing the stock number for the given item.
     * returns null if item is out of stock or if item does not exist
     */
    Item updateInventory(Item item) throws VendingMachinePersistenceException;
}
