/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.ui;

import com.sk.vendingmachine.dto.Change;
import com.sk.vendingmachine.dto.Item; // Item class from DTO
import com.sk.vendingmachine.dto.coin;
import java.math.BigDecimal;
import java.util.List;


/**
 *
 * @author steve
 */
public class VendingMachineView {
    
    private UserIO io;
    
    public VendingMachineView(UserIO io) {
        this.io = io;
    }
    
    public int printMenuAndGetSelection() {
        io.print("Vending Machine Menu");
        io.print("1. List Inventory");
        io.print("2. Insert Coins");
        io.print("3. Purchase an item");
        io.print("0. Exit");
        
        return io.readInt("Please select from the above choice", 0, 3);
    }
    
    
    // might change this (step 5)
    // display the item list
    public void displayItemList(List<Item> inventoryItem) {
        for (Item currentItem: inventoryItem) {
            String itemInfo = String.format("#%s : %s %s", currentItem.getInventoryItem(), 
                    currentItem.getItemName(), currentItem.getInventoryStock());
            io.print(itemInfo);
        }
        io.readString("Please hit enter to continue.");
        
        }
    // might modify this (step 5)
    public void displayDisplayAllBanner() {
       io.print("=== Display All Items ===");
    }
    
    public void displayDisplayItemBanner() {
        io.print("=== Display Item ===");
    }
    // selecting the item from the inventory
    public String getInventoryItemChoice() {
        return io.readString("Please enter the inventory item.");
    }
    public void displayItem(Item item) {
        if (item != null) {
            io.print(item.getItemName());
            io.print(item.getItemName() + " " + item.getCost());
           
            io.print("");
        } else {
            io.print("No such item.");
        }
        io.readInt("Please hit enter to continue.");
    }
    
    
    public void displayExitBanner() {
        io.print("Good Bye!!!");
    }
    
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }
    // Display Inventory stock, if the items have in stock or not.
    public void displayStock(List<Item> items) {
        for (Item item: items) {
            if (item.getInventoryStock() == 0) {
                io.print("[OUT OF STOCK] " + item.getItemName());
            } else {
                io.print("[ " + item.getInventoryStock() + " LEFT ] " + item.getItemName());
            }
        }
    }
    
    public void displayInventory(List<Item> inventory) {
        for (Item item: inventory) {
            io.print(item.getInventoryItem() + " $ " + item.getCost() + " " + item.getItemName());
        }
    }
    
    public void displayCurrentBalance(BigDecimal balance) {
        io.print("Your current balance: $ " + balance.setScale(2).toString());
        io.readString("Please press enter to return to the main menu.");
    }
    
    public int getUserInsertCoins() {
        int userChoice = io.readInt("Please enter 1 - 4 for the coins you wish to insert into the vending machine."
        + "\nPlease enter 0 to return to the main menu.", 0, 6);
        if (userChoice == 0) {
            return 0;
        } else {
            return userChoice;
        }
    }
    
    public void displayCoinMenu() {
        io.print("1. " + coin.TOONIE.toString());
        io.print("2. " + coin.LOONIE.toString());
        io.print("3. " + coin.QUARTERS.toString());
        io.print("4. " + coin.DIMES.toString());
        io.print("5. " + coin.NICKELS.toString());
        io.print("6. " + coin.PENNIES.toString());
        
        io.print("0. Return to the main menu");
    }
    
    public void displayBalance(BigDecimal balance) {
        io.print("Your Balance: $ " + balance.toString());
    }
    // working progress
    public void displayChange(Change change) {
        io.print("Here is your change: $" + change.getChangeDue());
        
    }
    
    public int askUserForItemChoice(List<Item> listOfAvailableItems, List<Item> listOfAllItems, BigDecimal balance) {
        displayInventory(listOfAvailableItems);
        displayBalance(balance);
        
        int userBuyChoice = io.readInt("Please enter a choice between 1 and " + (listOfAllItems.size()) + 
                " for the item you want to buy." + " Please enter 0 to go back to the main menu.", 0, listOfAllItems.size());
        displayBalance(balance);
        return userBuyChoice;
    }
    
    
    
    public void displayListInventoryBanner() {
        io.print("=== List Inventory ===");
    }
    
    public void displayViewBalanceBanner() {
        io.print("=== View Balance ===");
    }
    
    public void displayAddCoinsBanner() {
        io.print("=== Insert Coins ===");
    }
    
    public void displayBuyItemBanner() {
        io.print("=== Buy Item ===");
    }
    
    public void displaySuccessfulTransactionResults(Item item) {
        io.print("You have successfully bought " + item.getItemName() + " " + item.getCost());
    }
    
    public void displayInsufficientFundsMessage(BigDecimal balance, Item item) {
        io.print("Insufficient Funds.");
        io.readString("Please hit enter to continue.");
    }
    
    public void displayNoItemInventoryException(Item item) {
        io.print(item.getItemName() + " is OUT OF STOCK!");
        io.readString("Please hit enter to continue.");
    }
    
    public void displayNoChangeDue() {
        io.readString("No change.  Enjoy your drink!");
    }
    
    public int askUserMainOrExit() {
        io.print("1. Main Menu");
        int intMainOrExit = io.readInt("Please enter 1 to return to the main menu or 0 to exit.", 0, 1);
        return intMainOrExit;
    }
    
    public void displayVendingMachineOutOfStock() {
        io.print("=== OUT OF STOCK!!! ===");
        io.readString("Please press enter to exit.");
    }
    
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
}
