/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.app;

import com.sk.vendingmachine.controller.VendingMachineController;
import com.sk.vendingmachine.dao.VendingMachineAuditDao;
import com.sk.vendingmachine.dao.VendingMachineAuditDaoFileImpl;
import com.sk.vendingmachine.dao.VendingMachineDao;
import com.sk.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sk.vendingmachine.dao.VendingMachinePersistenceException;
import com.sk.vendingmachine.service.InsufficientFundsException;
import com.sk.vendingmachine.service.NoItemInventoryException;
import com.sk.vendingmachine.service.VendingMachineServiceLayer;
import com.sk.vendingmachine.service.VendingMachineServiceLayerImpl;
import com.sk.vendingmachine.ui.UserIO;
import com.sk.vendingmachine.ui.UserIOConsoleImpl;
import com.sk.vendingmachine.ui.VendingMachineView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 *
 * @author steve
 */
public class app {
    
    public static void main(String[] args) throws VendingMachinePersistenceException, InsufficientFundsException, NoItemInventoryException {
        // Instantiate the UserIO implementation
        // UserIO myIo = new UserIOConsoleImpl();
        // Instantiate the View and wire the UserIO
        // VendingMachineView myView = new VendingMachineView(myIo);
        // Instantiate the DAO
        // VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        // Instantiate the Audit DAO
        // VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        // Instantiate the Service Layer and wire the DAO and Audit DAO into it
        // VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        // Instantiate the Controller and wire the Service Layer into it
        // VendingMachineController controller = new VendingMachineController(myService, myView);
        // Kick off the Controller
        // controller.run();
        
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
        
    }
    
}
