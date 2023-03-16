/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.service;

import com.sk.vendingmachine.dao.VendingMachineAuditDao;
import com.sk.vendingmachine.dao.VendingMachineDao;
import com.sk.vendingmachine.dao.VendingMachinePersistenceException;
import com.sk.vendingmachine.dto.Change;
import com.sk.vendingmachine.dto.Item;
import com.sk.vendingmachine.dto.coin;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author steve
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {
    
    
    private VendingMachineAuditDao auditDao;
    private VendingMachineDao dao;
    BigDecimal balance = new BigDecimal(0);
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public Item getItem(int inventoryItem) throws VendingMachinePersistenceException {
        return dao.getItem(inventoryItem);
    }

    @Override
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        return dao.getAllItems();
    }

    @Override
    public List<Item> getAllAvailableItems() throws VendingMachinePersistenceException {
        return dao.getAvailableItems();
    }
    
    

    @Override
    public void updateInventory(Item item) throws VendingMachinePersistenceException {
        dao.updateInventory(item);
    }

    

    @Override
    public void addToBalance(int coinChoice) throws VendingMachinePersistenceException {
        
        switch (coinChoice) {
            case 1:
                balance = balance.add(coin.TOONIE.getValue());
                auditDao.writeAuditEntry("User inserted 2.00. Balance updated to $" + balance.toString());
                break;
                
            case 2:
                balance = balance.add(coin.LOONIE.getValue());
                auditDao.writeAuditEntry("User inserted 1.00. Balance updated to $" + balance.toString());
                break;
                
            case 3:
                balance = balance.add(coin.QUARTERS.getValue());
                auditDao.writeAuditEntry("User inserted 0.25. Balance updated to $" + balance.toString());
                break;
            
            case 4:
                balance = balance.add(coin.DIMES.getValue());
                auditDao.writeAuditEntry("User inserted 0.10. Balance updated to $" + balance.toString());
                break;
                
            case 5:
                balance = balance.add(coin.NICKELS.getValue());
                auditDao.writeAuditEntry("User inserted 0.05. Balance updated to $" + balance.toString());
                break;
                
            case 6:
                balance = balance.add(coin.PENNIES.getValue());
                auditDao.writeAuditEntry("User inserted 0.01. Balance updated to $" + balance.toString());
                break;
                
            
        }
    }

    @Override
    public Change buyItem(int inventoryItem) throws VendingMachinePersistenceException, 
            NoItemInventoryException, InsufficientFundsException {
        Item item = getItem(inventoryItem);
        
        
        // Check to see the item is in stock
        if (dao.getItem(inventoryItem).getInventoryStock() == 1) {
            auditDao.writeAuditEntry("No Item Inventory Exception: Item select " + item.getItemName() + " Out of Stock");
            throw new NoItemInventoryException("Sorry: " + item.getItemName() + " is out of stock!");
        }
        // Check to see the user have enough funds
        if (balance.compareTo(item.getCost()) == -1) {
            auditDao.writeAuditEntry("Insufficient Funds Exception: User attempted to buy "
            + item.getItemName() + "But had only balance of $ " + item.getCost().toString());
            throw new InsufficientFundsException("Insufficient Funds. " + item.getItemName()
            + " Your Balance is " + balance.toString()); 
        }            
        updateInventory(item);
        
        // Calculating change from remaining balance after purchase.
        Change change = new Change(balance.subtract(item.getCost()));
        
        auditDao.writeAuditEntry("User successfully bought " + item.getItemName() + " $" + item.getCost()
        + " Change: " + balance.subtract(item.getCost()));
        
        // Reset the Balance to 0
        balance = new BigDecimal(0);
        
        return change;
    }

    @Override
    public BigDecimal getBalance() {
        return balance.setScale(2);
    }

    @Override
    public void startAudit() throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry("=== START OF TRANSACTION ===");
    }

    @Override
    public void exitAudit(String exitMessage) throws VendingMachinePersistenceException {
        auditDao.writeAuditEntry("=== END OF TRANSACTION ===");
    }
    
    
    
}
