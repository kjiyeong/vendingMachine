/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author steve
 */
public class Change {
    
    private final int toonie;
    private final int loonie;
    private final int quarters;
    private final int dimes;
    private final int nickels;
    private final int pennies;
    
    Change changedue;
    BigDecimal changeDueBigDecimal;
    
    public Change(BigDecimal change) {
        changeDueBigDecimal = change;
        
        toonie = change.divide(coin.TOONIE.getValue(), 0, RoundingMode.DOWN).intValue();
        change = change.remainder(coin.TOONIE.getValue());
        
        loonie = change.divide(coin.LOONIE.getValue(), 0, RoundingMode.DOWN).intValue();
        change = change.remainder(coin.LOONIE.getValue());
        
        quarters = change.divide(coin.QUARTERS.getValue(), 2, RoundingMode.DOWN).intValue();
        change = change.remainder(coin.QUARTERS.getValue());
        
        dimes = change.divide(coin.DIMES.getValue(), 2, RoundingMode.DOWN).intValue();
        change = change.remainder(coin.DIMES.getValue());
        
        nickels = change.divide(coin.NICKELS.getValue(), 2, RoundingMode.DOWN).intValue();
        change = change.remainder(coin.NICKELS.getValue());
        
        pennies = change.divide(coin.PENNIES.getValue(), 2, RoundingMode.DOWN).intValue();
        change = change.remainder(coin.PENNIES.getValue());
        
    }
    
    public int getToonie() {
        return toonie;
    }
    
    public int getLoonie() {
        return loonie;
    }

    public int getQuarters() {
        return quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public int getPennies() {
        return pennies;
    }
    
    public BigDecimal getChangeDue() {
        return changeDueBigDecimal;
    
    }
}
