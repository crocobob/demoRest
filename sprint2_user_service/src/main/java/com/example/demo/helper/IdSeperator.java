package com.example.demo.helper;

import com.example.demo.exceptions.IdNotInt;

public class IdSeperator {
	public int[] seperator(String ids) throws IdNotInt {
		String[] separatedStrings = ids.split(",");
	        
		int[] intArray = new int[separatedStrings.length];
	        
		for (int i = 0; i < separatedStrings.length; i++) {
	            
			try {
				intArray[i] = Integer.parseInt(separatedStrings[i]);
			} catch (Exception e) {
				throw new IdNotInt("Id cannot be parsed to Integer");
			}
	        }
	        return intArray;
	}
}
