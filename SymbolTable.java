/*  Symbol table class
 * 
 * 
 * 
 */
//Andrew Liang CS220 

import java.util.Map;
import java.util.TreeMap;


public class SymbolTable {

	private static Map<String, String> symbolTable;
	 
	public SymbolTable()
	 {
		 symbolTable = new TreeMap<String,String>();
		 	symbolTable.put("SP", "0");
			symbolTable.put("LCL", "1");
			symbolTable.put("ARG", "2");
			symbolTable.put("THIS", "3");
			symbolTable.put("THAT", "4");
			symbolTable.put("R0", "0");
			symbolTable.put("R1", "1");
			symbolTable.put("R2", "2");
			symbolTable.put("R3", "3");
			symbolTable.put("R4", "4");
			symbolTable.put("R5", "5");
			symbolTable.put("R6", "6");
			symbolTable.put("R7", "7");
			symbolTable.put("R8", "8");
			symbolTable.put("R9", "9");
			symbolTable.put("R10", "10");
			symbolTable.put("R11", "11");
			symbolTable.put("R12", "12");
			symbolTable.put("R13", "13");
			symbolTable.put("R14", "14");
			symbolTable.put("R15", "15");
			symbolTable.put("SCREEN", "16384");
			symbolTable.put("KBD", "24576");
	 }
	
	// add's entry in to table
	public boolean addEntry(String key, String value) {
		if(symbolTable.put(key, value) != null)
			return true;
		return false;
	}
	
	//checks if symbol exists
	public boolean contains(String key){
		return symbolTable.containsKey(key);
	}
	
	public String getAddress(String val)
	{
		return	symbolTable.get(val);	
	}
	
	public boolean validName(String val)
	{

		return	(val != null) && (val.matches("[a-zA-Z_][a-zA-Z0-9_]*") || val.matches("[$][:][0-9]*") );	
	}
	
}//end symbol table class