/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dao;

import com.sk.vendingmachine.dto.Item;
import com.sk.vendingmachine.service.VendingMachineDaoStubImpl;
import com.sk.vendingmachine.service.VendingMachineServiceLayer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author steve
 */
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDao testDao;
    
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeEach
    public void setUp() throws IOException, VendingMachinePersistenceException {
        String testFile = "testinventory.txt";
        
        // Use the FileWriter to quickly blank the file
        new FileWriter(testFile);
        
//        String inventoryAsString ="1::0.65::Test A::5"
//                                + "\n2::1.55::Test B::3"
//                                + "\n3::1.25::Test C::2"
//                                + "\n4::0.35::Test D::7"
//                                + "\n5::0.45::Test E::0";
        // This is pretend inventory stock for the inventory
//        BufferedWriter writer = new BufferedWriter(new FileWriter(testFile));
//        writer.write(inventoryAsString);
//        writer.close();
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("vendingMachineDaoStub", VendingMachineDaoStubImpl.class); 
        
    }
    
    @Test
    public void testGetItem() throws VendingMachinePersistenceException {
        // Arrange
        int inventoryItem = 1;
        Item itemOne = new Item(inventoryItem);
        itemOne.setCost(new BigDecimal("1.25"));
        itemOne.setItemName("Test Item");
        itemOne.setInventoryStock(5);
        
        // ACT
       
        Item retrievedItem = testDao.getItem(inventoryItem);
        
        // ASSERT
        assertEquals(itemOne.getInventoryItem(), retrievedItem.getInventoryItem(), "Checking Inventory Item Number");
        assertEquals(itemOne.getCost(), retrievedItem.getCost(), "Checking the item cost");
        assertEquals(itemOne.getItemName(), retrievedItem.getItemName(), "Checking the item name");
        assertEquals(itemOne.getInventoryStock(), retrievedItem.getInventoryStock(), "Checking item stock");
    }
    
    @Test
    public void testGetAllAndGetAvailableItem() throws VendingMachinePersistenceException {
        // Arrange
        int inventoryItem = 1;
        Item itemOne = new Item(inventoryItem);
        itemOne.setCost(new BigDecimal("0.65"));
        itemOne.setItemName("Test A");
        itemOne.setInventoryStock(5);
        
        int itemTwo = 5;
        Item itemOutOfStock = new Item(inventoryItem);
        itemOutOfStock.setCost(new BigDecimal("0.45"));
        itemOutOfStock.setItemName("Test E");
        itemOutOfStock.setInventoryStock(0);
        
        // Act
        List<Item> allList = testDao.getAllItems();
        List<Item> availableList = testDao.getAvailableItems();
        
        // Assert
        assertNotNull(allList, "All list should not be empty");
        assertNotNull(availableList, "Available List should not be empty");
        assertEquals(2, allList.size(), "All list should have 2 items");
        assertEquals(1, availableList.size(), "All list should have 1 items");
        
        assertTrue(allList.contains(itemOne), "All item list should contain item 1");
        assertFalse(allList.contains(itemOutOfStock), "All items list should contain item 5");
        assertTrue(availableList.contains(itemOne), "Available items list should contain item 1");
        assertFalse(availableList.contains(itemOutOfStock), "Available items list should Not contain item 5");
    }   
    
    public void testUpdateItem() throws VendingMachinePersistenceException {
        //Arange
        int inventoryItem = 1;
        Item itemOne = new Item(inventoryItem);
        itemOne.setCost(new BigDecimal("0.65"));
        itemOne.setItemName("Test A");
        itemOne.setInventoryStock(5);
        
        int inventoryItemTwo = 2;
        Item itemOutOfStock = new Item(inventoryItemTwo);
        itemOutOfStock.setCost(new BigDecimal("0.65"));
        itemOutOfStock.setItemName("Test B");
        itemOutOfStock.setInventoryStock(0);
        
        int inventoryItemThree = 3;
        Item itemNotInHashMap = new Item(inventoryItemThree);
        itemNotInHashMap.setCost(new BigDecimal("0.65"));
        itemNotInHashMap.setItemName("Test C");
        itemNotInHashMap.setInventoryStock(0);
        
        // Act
        Item updateOne = testDao.updateInventory(itemOne);
        Item updateOutOfStock = testDao.updateInventory(itemOutOfStock);
        Item updateInventory = testDao.updateInventory(itemNotInHashMap);
        
        // Assert
        assertEquals(4, updateOne.getInventoryStock(), "Stock should have dropped from 5 to 4");
        assertNull(updateOutOfStock, "Should return null for item out of stock");
        assertNull(updateInventory, "Should return null for item not in hashmap");
    }
    
    
}
