/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.service;

import com.sk.vendingmachine.dao.VendingMachinePersistenceException;
import com.sk.vendingmachine.dto.Change;
import com.sk.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author steve
 */
public interface VendingMachineServiceLayer {
    
    
    
    Item getItem(int inventoryItem) throws VendingMachinePersistenceException;
    
    
    List<Item> getAllItems() throws VendingMachinePersistenceException;
    
    
    List<Item> getAllAvailableItems() throws VendingMachinePersistenceException;
    
    
    void updateInventory(Item item) throws VendingMachinePersistenceException;
    
    
    
    
    
    public void addToBalance(int coinChoice) throws VendingMachinePersistenceException;
    
    
    public Change buyItem(int inventoryItem) throws VendingMachinePersistenceException,
            NoItemInventoryException, InsufficientFundsException;
    
    
    public BigDecimal getBalance();
    
    
    public void startAudit() throws VendingMachinePersistenceException;
    
    
    public void exitAudit(String exitMessage) throws VendingMachinePersistenceException;
    
}
