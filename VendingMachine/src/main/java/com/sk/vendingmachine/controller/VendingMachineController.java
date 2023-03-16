/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.controller;

import com.sk.vendingmachine.dao.VendingMachineDao;
import com.sk.vendingmachine.dao.VendingMachinePersistenceException;
import com.sk.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sk.vendingmachine.dto.Change;
import com.sk.vendingmachine.dto.Item;
import com.sk.vendingmachine.service.InsufficientFundsException;
import com.sk.vendingmachine.service.NoItemInventoryException;
import com.sk.vendingmachine.service.VendingMachineServiceLayer;
import com.sk.vendingmachine.ui.UserIO;
import com.sk.vendingmachine.ui.UserIOConsoleImpl;
import com.sk.vendingmachine.ui.VendingMachineView;
import java.util.List;



/**
 *
 * @author steve
 */
public class VendingMachineController {
    
    private VendingMachineServiceLayer service;
    private VendingMachineView view ;
    private boolean keepGoing = true;
       
    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }
    
    public void run() throws VendingMachinePersistenceException, NoItemInventoryException, InsufficientFundsException {
        
        int meunSelection = 0;
        
        startAudit();
        if (service.getAllAvailableItems().isEmpty()) {
            view.displayVendingMachineOutOfStock();
            exitAuditMessage("Program closes as vending machine is out of stock");
            keepGoing = false;
        }
        
        try {
        while (keepGoing) {
            
            int menuSelection = getMenuSelection();
            
            switch (menuSelection) {
                case 1:
                    getStock();    
                    break;         
                case 2:
                    addBalance();   
                    break;
                case 3:
                    buyItem();    
                    break;
                case 0:
                    keepGoing = false;
                    exitAuditMessage("User exits from the main menu.");
                    break;
                default:
                    unknownCommand();
            }
        }
        exitMessage();
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    private int getMenuSelection() throws VendingMachinePersistenceException {
        List<Item> availableItems = service.getAllAvailableItems();
        if (availableItems.isEmpty()) {
            view.displayVendingMachineOutOfStock();
            keepGoing = false;
            exitAuditMessage("Program closes as vending machine is out of stock.");
            return 99;
        }
        return view.printMenuAndGetSelection();
    }
    
    // step5
    private void listItems() throws VendingMachinePersistenceException {
        List<Item> inventoryItem = service.getAllItems();
        view.displayItemList(inventoryItem);
    }
    
    
    
    private void getStock() throws VendingMachinePersistenceException {
        view.displayListInventoryBanner();
        List<Item> inventory = service.getAllItems();
        view.displayStock(inventory);
        int mainOrExit = view.askUserMainOrExit();
        if (mainOrExit == 0) {
            keepGoing = false;
            exitAuditMessage("User exits from stock page");
        }
    }
    
    public void addBalance() throws VendingMachinePersistenceException {
        boolean keepGoing = true;
        while (keepGoing) {
            view.displayAddCoinsBanner();
            view.displayCoinMenu();
            view.displayBalance(service.getBalance());
            int userEntry = view.getUserInsertCoins();
            if (userEntry == 0) {
                keepGoing = false;
            } else {
                service.addToBalance(userEntry);
            }
        }
    }
    
    public void buyItem() throws VendingMachinePersistenceException {
        view.displayBuyItemBanner();
        boolean hasErrors = false;
        do {
            List<Item> availableItems = service.getAllAvailableItems();
            List<Item> allItems = service.getAllItems();
            int userBuyItemChoice = view.askUserForItemChoice(availableItems, allItems, service.getBalance());
            if (userBuyItemChoice != 0) {
            try {
          
                    Item item = service.getItem(userBuyItemChoice);
                    Change change = service.buyItem(userBuyItemChoice);
                    view.displaySuccessfulTransactionResults(item);
                if (service.getBalance().equals(0)) {
                    view.displayNoChangeDue();
                } else {
                    view.displayChange(change);
                }
                int mainOrExit = view.askUserMainOrExit();
                if (mainOrExit == 0) {
                    keepGoing = false;
                    exitAuditMessage("User exits after purchasing an item.");
                }
                hasErrors = false;
            } catch (InsufficientFundsException e) {
                    view.displayInsufficientFundsMessage(service.getBalance(), service.getItem(userBuyItemChoice));
                    hasErrors = true;
                    
            } catch (NoItemInventoryException e) {
                    view.displayNoItemInventoryException(service.getItem(userBuyItemChoice));
                    hasErrors = true;
            }
            } else {
                hasErrors = false;
            }
                                    
        } while(hasErrors);
    }
    
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    public void startAudit() throws VendingMachinePersistenceException {
        service.startAudit();
    }
    
    public void exitAuditMessage(String exitMessage) throws VendingMachinePersistenceException {
        service.exitAudit(exitMessage);
        view.displayExitBanner();
    }
    
}