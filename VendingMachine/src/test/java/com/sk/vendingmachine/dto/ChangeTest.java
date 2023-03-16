/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dto;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author steve
 */
public class ChangeTest {
    
    public ChangeTest() {
    }
    
    @Test
    public void testZeroChange() {
        // Arrange
        BigDecimal changeDueBigDecimal = new BigDecimal(0);
        
        // Act
        Change changeDue = new Change(changeDueBigDecimal);
        
        // Assert
        assertEquals(0, changeDue.getToonie(), "Change due should be 0 $2.00");
        assertEquals(0, changeDue.getLoonie(), "Change due should be 0 $1.00");
        assertEquals(0, changeDue.getQuarters(), "Change due should be 0 quarters");
        assertEquals(0, changeDue.getDimes(), "Change due should be 0 dimes");
        assertEquals(0, changeDue.getNickels(), "Change due should be 0 nickels");
        assertEquals(0, changeDue.getPennies(), "Change due should be 0 pennies");
    }
    
    @Test
    public void testChange() {
        // Arrange
        BigDecimal changeDueBigDecimal = new BigDecimal("1.05");
        
        //Act
        Change changeDue = new Change(changeDueBigDecimal);
        
        // Assert
        assertEquals(0, changeDue.getToonie(), "Change due should be 0 $2.00");
        assertEquals(1, changeDue.getLoonie(), "Change due should be 1 $1.00");
        assertEquals(0, changeDue.getQuarters(), "Change due should be 0 quarters");
        assertEquals(0, changeDue.getDimes(), "Change due should be 0 dimes");
        assertEquals(1, changeDue.getNickels(), "Change due should be 1 nickels");
        assertEquals(0, changeDue.getPennies(), "Change due should be 0 pennies");
    }
    
    
    
}
