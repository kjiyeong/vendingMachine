/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dao;

import com.sk.vendingmachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;



/**
 *
 * @author steve
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    
    public final String INVENTORY_FILE;
    
    public VendingMachineDaoFileImpl() {
        INVENTORY_FILE = "inventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String inventoryTextFile) {
        INVENTORY_FILE = inventoryTextFile;
    }
    
    public static final String DELIMITER = "::";
    
    private Map<Integer, Item> items = new HashMap<>();

    
    
    
    
    
    // might have to change this list 
    
    public List<Item> getAllItems() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList<Item>(items.values());
    }

    public Item getItem(int inventoryItem) throws VendingMachinePersistenceException {
        return items.get(inventoryItem);
    }
    

    public List<Item> getAvailableItems() throws VendingMachinePersistenceException {
        loadInventory();
        List<Item> availableItems = items.values().stream()
                .filter((item) -> item.getInventoryStock() > 0)
                .collect(Collectors.toList());
        return availableItems;
    }

    public Item updateInventory(Item item) throws VendingMachinePersistenceException {
        loadInventory();
        if (items.containsValue(item) && items.get(item.getInventoryItem()).getInventoryStock() > 0) {
            int newInventoryStock = item.getInventoryStock() -1;
            items.get(item.getInventoryItem()).setInventoryStock(newInventoryStock);
            item.setInventoryStock(newInventoryStock);
            writeInventory();
            return item;
        } else {
            return null;
        }
    }

    
    
    public Item unmarshallItem(String itemAsText) {
    // READ / WRITE FILE
    /**
     * itemAsText is expecting to read the lines from "inventory.txt"
     * For example it should look like this:
     * 1::$1.00::Coca Cola::3
     * We then split that line on our DELIMITER which we are using as ::
     * Leaving us with an array of Strings, stored in itemTokens. Which
     * should look like this:
     * ----------------------------
     * |   |      |           |   |
     * | 1 | 1.00 | Coca Cola | 3 |
     * |   |      |           |   |
     * ----------------------------
     * [0]   [1]       [2]     [3]
     * 
     * [0] Item Number
     * [1] Cost
     * [2] Name of the Item
     * [3] Stock of inventory
     */
    
        // Retrieve the inventoryItem number in order to generate an item.
        String[] itemTokens = itemAsText.split(DELIMITER);
        int inventoryItem = Integer.parseInt(itemTokens[0]);
        Item itemFromFile = new Item(inventoryItem);
        
        // Retrieve the remaining attributes of the item
        BigDecimal cost = new BigDecimal(itemTokens[1]);
        String itemName = itemTokens[2];
        int inventoryStock = Integer.parseInt(itemTokens[3]);
        
        // set the variables in the new item attained from the String array
        itemFromFile.setCost(cost);
        itemFromFile.setItemName(itemName);
        itemFromFile.setInventoryStock(inventoryStock);
        
        return itemFromFile;
    }
    
    public void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;
        
        try {
            // Create Scanner for reading the file
            scanner = new Scanner(new BufferedReader(new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException("-_- Could not load inventory data into memory.", e);
        }
        
        String currentLine;
        
        Item currentItem;
        
        while (scanner.hasNextLine()) {
            
            currentLine = scanner.nextLine();
            
            currentItem = unmarshallItem(currentLine);
            
            items.put(currentItem.getInventoryItem(), currentItem);
        }
        scanner.close();
    }
    /*
    We need to turn Item object into a line of text for our file.
    For example we need an in memory object to end up like this:
    1::1.00::Coca Cola::3
    It's not a complicated process. Just get out each property,
    and concatenate with our DELIMITER as a kind of spacer.
    */
    public String marshallItem(Item item) {
        String itemAsText = Integer.toString(item.getInventoryItem()) + DELIMITER;
        itemAsText += item.getCost().toString() + DELIMITER;
        itemAsText += item.getItemName() + DELIMITER;
        itemAsText += Integer.toString(item.getInventoryStock());
        return itemAsText;
    }
    
    private void writeInventory() throws VendingMachinePersistenceException {
        
        PrintWriter out;
        
        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException("Could not save item data.", e);
        }
        
        String itemAsText;
        List<Item> itemList = new ArrayList(items.values());
        for (Item currentItem : itemList) {
            itemAsText = marshallItem(currentItem);
            out.println(itemAsText);
            out.flush();
        }
        out.close();
    }


}
