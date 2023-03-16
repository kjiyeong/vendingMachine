/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author steve
 */
public enum coin {
   TOONIE(new BigDecimal("2.00")),
   LOONIE(new BigDecimal("1.00")),
   QUARTERS(new BigDecimal("0.25")),
   DIMES(new BigDecimal("0.10")),
   NICKELS(new BigDecimal("0.05")),
   PENNIES(new BigDecimal("0.01"));
   
   private final BigDecimal value;
   
   coin(BigDecimal value) {
       this.value = value;
   }
   
   public BigDecimal getValue() {
       return value;
   }
}
