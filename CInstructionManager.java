/*
 *  Code class
 *  
 *  
 */
//Andrew Liang CS220 


import java.util.Map;
import java.util.TreeMap;


public class CInstructionManager {
	
	//jump table
	private Map<String, String> jumpCodes;
	//comp table
	private Map<String, String> compCodes;
	//dest table
	private Map<String, String> destCodes;
	
	
	public CInstructionManager()
	{
		//jump table, values vas copied from 7-th class presetation
		jumpCodes = new TreeMap<String,String>();
		jumpCodes.put("NULL", "000");
		jumpCodes.put("JGT", "001");
		jumpCodes.put("JEQ", "010");
		jumpCodes.put("JGE", "011");
		jumpCodes.put("JLT","100");
		jumpCodes.put("JNE","101");
		jumpCodes.put("JLE","110");
		jumpCodes.put("JMP", "111");
		
		//comp table, also copied from class presentation
		compCodes = new TreeMap<String,String>();
		compCodes.put("0", "0101010");
		compCodes.put("1", "0111111");
		compCodes.put("-1","0111010");
		compCodes.put("D", "0001100");
		compCodes.put("A", "0110000");
		compCodes.put("!D", "0001101");
		compCodes.put("!A", "0110001");
		compCodes.put("-D", "0001111");
		compCodes.put("-A", "0110011");
		compCodes.put("D+1","0011111");
		compCodes.put("A+1","0110111");
		compCodes.put("D+A","0000010");
		compCodes.put("D-A","0010011");
		compCodes.put("A-D","0000111");
		compCodes.put("D&A","0000000");
		compCodes.put("D|A","0010101");	
		compCodes.put("M","1110000");
		compCodes.put("!M","1110001");
		compCodes.put("-M","1110011");
		compCodes.put("M+1","1110111");
		compCodes.put("M-1","1110010");
		compCodes.put("D+M","1000010");
		compCodes.put("D-M","1010011");
		compCodes.put("M-D","1000111");
		compCodes.put("D&M","1000000");
		compCodes.put("D|M","1010101");
		compCodes.put("D-1","0001110");
		compCodes.put("A-1","0110010");

		//dest table, like previous, copied from class presentation
		destCodes = new TreeMap<String, String>();
		destCodes.put("NULL","000");
		destCodes.put("M","001");
		destCodes.put("D","010");
		destCodes.put("MD","011");
		destCodes.put("A","100");
		destCodes.put("AM","101");
		destCodes.put("AD","110");
		destCodes.put("AMD","111");		
		
	}
	
	public String dest(String mn)
	{
		return destCodes.get(mn);
	}
	
	public String comp(String mn)
	{
		return compCodes.get(mn);
	}
	
	
	public String jump(String mn)
	{
		return jumpCodes.get(mn);
	}

}