/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sk.vendingmachine.ui;

import java.math.BigDecimal;
import java.util.Scanner;

/**
 *
 * @author steve
 */
public class UserIOConsoleImpl implements UserIO {
    Scanner sc = new Scanner(System.in);

	
	public void print(String message) {
		System.out.println(message);

	}

	
	public String readString(String prompt) {
		print(prompt);
		return sc.nextLine();

	}


	public int readInt(String prompt) {
		print(prompt);
		return Integer.parseInt(sc.nextLine());

	}

	
	public int readInt(String prompt, int min, int max) {
		int input = 0;
		print(prompt);
		input = Integer.parseInt(sc.nextLine());
		if (input > max || input < min) {
			return readInt(prompt, min, max);
		}

		return input;

	}

	
	public double readDouble(String prompt) {
		print(prompt);
		return Double.parseDouble(sc.nextLine());
	}

	
	public double readDouble(String prompt, double min, double max) {
		double input = 0;
		print(prompt);
		input = Double.parseDouble(sc.nextLine());
		if (input > max || input < min) {
			return readDouble(prompt, min, max);
		}
		return input;
	}

	
	public float readFloat(String prompt) {
		print(prompt);
		return Float.parseFloat(sc.nextLine());
	}

	
	public float readFloat(String prompt, float min, float max) {
		float input = 0;
		print(prompt);
		input = Float.parseFloat(sc.nextLine());
		if (input > max || input < min) {
			return readFloat(prompt, min, max);
		}
		return input;
	}

	
	public long readLong(String prompt) {
		print(prompt);
		return Long.parseLong(sc.nextLine());
	}


	public long readLong(String prompt, long min, long max) {
		long input = 0;
		print(prompt);
		input = Long.parseLong(sc.nextLine());
		if (input > max || input < min) {
			return readLong(prompt, min, max);
		}

		return input;
        }
        
        
        public BigDecimal readBigDecimal(String msgPrompt) {
        String inputAsString = this.readString(msgPrompt);
        BigDecimal input = null;
        try {
            input = new BigDecimal(inputAsString);
        } catch (NumberFormatException e) {
            this.print("Input error. Please try again");
        }
        return input;
    }
}
