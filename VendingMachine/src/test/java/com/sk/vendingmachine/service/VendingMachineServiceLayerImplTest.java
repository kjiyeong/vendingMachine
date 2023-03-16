/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.service;

import com.sk.vendingmachine.dao.VendingMachineAuditDao;
import com.sk.vendingmachine.dao.VendingMachineDao;
import com.sk.vendingmachine.dao.VendingMachinePersistenceException;
import com.sk.vendingmachine.dto.Item;
import java.math.BigDecimal;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author steve
 */
public class VendingMachineServiceLayerImplTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerImplTest() {
        // wire the service layer with stub implementation of the DAO and
        // AuditDao
        // VendingMachineDao dao = new VendingMachineDaoStubImpl();
        // VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        // service = new VendingMachineServiceLayerImpl(dao, auditDao);
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
        
    }
    
    
    
    @BeforeEach
    public void setUp() {
    }
    
    @Test
    public void TestValidPurchase() throws VendingMachinePersistenceException {
        // Arrange
        service.addToBalance(1); // added $2.00 to balance
        //Act
        try {
            service.buyItem(1);
        } catch (InsufficientFundsException | NoItemInventoryException e) {
            // Assert
            fail("A valid item was bought with enough funds. No exception should have been thrown.");
        }
    }
    
    @Test
    public void testInsufficientFundsForItem() throws VendingMachinePersistenceException {
        //Arrange
        service.addToBalance(5); 
        
        // ACT
        try {
            service.buyItem(1);
            fail("Expected InsufficientFundsException was not thrown.");
        } catch (NoItemInventoryException e) {
            // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
            
        }
    }
    @Test
    public void testNoStockOfItem() throws VendingMachinePersistenceException {
        // Arrange
        service.addToBalance(6);
        
        // Act
        try {
            service.buyItem(2); // item is out of stock
            fail("Expected NoItemInventoyException was not thrown.");
        } catch (NoItemInventoryException e) {
            //Assert
            fail("Incorrect exception was thrown.");
        } catch (InsufficientFundsException e) {
    }
    }
    
    @Test
    public void testAddBalance() throws VendingMachinePersistenceException {
        // Arrange
        service.addToBalance(1); // adds $2.00 to balance
        service.addToBalance(4); // adds $0.10 to balance
        service.addToBalance(6); // adds $0.01 to balance
        
        //Act
        BigDecimal actualBalance = service.getBalance();
        
        // Assert
        assertEquals(new BigDecimal("2.11"), actualBalance, "The balance should be $2.11");
        assertNotEquals(new BigDecimal("0.51"), actualBalance, "The balance should NOT be $0.51");
    }
    
    @Test
    public void testGetItem() throws VendingMachinePersistenceException {
        // Arrange
        Item onlyItem = new Item(1);
        onlyItem.setCost(new BigDecimal("1.25"));
        onlyItem.setItemName("Test Item");
        onlyItem.setInventoryStock(5);
        
        // Act & Assert
        Item testItem = service.getItem(1);
        assertNotNull(testItem, "Getting item 1 should not be null.");
        assertEquals(onlyItem, testItem, "Item stored under 1 should be Test Item.");
        
        Item itemBeNull = service.getItem(10);
        assertNull(itemBeNull, "Getting 10 should be null");
    }
    
    @Test
    public void testGetAllItem() throws VendingMachinePersistenceException {
        // Arrange
        Item testItem = new Item(1);
        testItem.setCost(new BigDecimal("1.25"));
        testItem.setItemName("Test Item");
        testItem.setInventoryStock(5);
        
        Item outOfStockItem = new Item(2);
        outOfStockItem.setCost(new BigDecimal("0.80"));
        outOfStockItem.setItemName("Test Item Two");
        outOfStockItem.setInventoryStock(0);
        
        // Act & Assert
        assertEquals(2, service.getAllItems().size(), "Should only have 2 items");
        assertTrue(service.getAllItems().contains(testItem), "All items list should contain test item.");
        assertTrue(service.getAllItems().contains(outOfStockItem), "All items should contain out of stock item.");
    }
    
    
}
