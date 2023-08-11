/*
 *  Assembler class includes main
 * 
 *  
 */
//Andrew Liang CS220 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;




public class Assembler {
	public static  int counter=0;
	public static int nextRam = 16;
	public static String compT,destT,jumpT; // temp's
	public BufferedWriter bw;

	
	private CInstructionManager cManager;
	private Parser cParser;
	
	
	public Assembler()
	{
		cManager = new CInstructionManager();
			
	}
	
	public String decimalToBinary(int number) {
		String binVal = Integer.toBinaryString(number);
		return binVal;
	}
	
	public void initializeFiles(String iName){
		String outputName = iName.replace("asm", "hack");  //out file name
		
		cParser = new Parser(iName);  //new parser object
	
		File out = new File(outputName);  //output, .hack file
		
		FileWriter fw = null;
	
		try {
			fw = new FileWriter(out.getAbsoluteFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
		bw = new BufferedWriter(fw); // Ready to write on file
	
	}	
	
	public void closeFiles(){
		try {
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	
	public void firstPass(String assemblyFileName, SymbolTable symbolTable) {
			//first pass

//			System.out.println(s.trim());
			while(cParser.hasMoreCommand()) {  
			if(cParser.commandType()== Parser.Command.L_COMMAND) { 
				symbolTable.addEntry(cParser.parseSymbol(),Integer.toString(counter)) ; //adds new symbol to symbol table
			}
				else counter++; //next line
				
				cParser.advance();  // next command
			}		
	}
	
	public void secondPass(String assemblyFileName, SymbolTable symbolTable, String binaryFileName) {
		cParser.lineNumber =0;   // resets counter for starts from first line
		
		//second pass
		while(cParser.hasMoreCommand())
		{
			if(cParser.commandType()== Parser.Command.A_COMMAND) //@xxx
			{
				if(cParser.strFileArr[cParser.lineNumber].startsWith("@"))
				{
				String tmp  = cParser.parseSymbol(); //returns xxx
					if(cParser.isNum(tmp))  //checks if xxx is number
					{
						int xxx = Integer.parseInt(tmp);
						tmp = Parser.dexToBin(xxx);	// return bin value of xxx
						tmp = cParser.addZero(tmp);
						System.out.println(tmp);
						try {
							bw.write(tmp + '\n');//write to hack
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					}
					else  //if not number
					{
						if(!symbolTable.contains(tmp))  // not exists in Symbol Table
						{
							symbolTable.addEntry(tmp,Integer.toString(nextRam));  //Adds to Symbol Table
							nextRam++;
						}
						 if(symbolTable.contains(tmp)) // already exists in Symbol Table
							{
							String tmp2 = symbolTable.getAddress(tmp);
							int xxx = Integer.parseInt(tmp2);
							tmp2 = Parser.dexToBin(xxx);
							tmp2 = cParser.addZero(tmp2);
							System.out.println(tmp2);
							try {
								
								bw.write(tmp2+'\n');  //write to hack
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}//if command type A_COMMAND 
			if(cParser.commandType()== Parser.Command.C_COMMAND)
			{
				if(cParser.strFileArr[cParser.lineNumber].contains("="))//dest=comp
				{
					
					
					destT = cManager.dest(cParser.getDestMnemonic());
					compT = cManager.comp(cParser.getCompMnemonic());
					jumpT = cManager.jump("NULL");  //no need jump
					System.out.println("111" + compT + destT + jumpT );
					try {
						bw.write("111" + compT + destT + jumpT +'\n');
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				else if(cParser.strFileArr[cParser.lineNumber].contains(";")) //jump
				{
					destT = cManager.dest("NULL"); // no need dest
					compT = cManager.comp(cParser.getCompMnemonic());
					jumpT = cManager.jump(cParser.getJumpMnemonic());
					
					System.out.println("111" + compT + destT + jumpT );
					try {
						bw.write("111" + compT + destT + jumpT +'\n');
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}//if command type C_COMMAND 
			cParser.advance();		
		}//end while
		
		
		
	}
	
	public void handleError(String message) {

		
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		Assembler assembler = new Assembler();
		SymbolTable sTable = new SymbolTable();
		
//		System.out.println(sTable.validName("A"));
		
//		String name = args[0].substring(0, args[0].indexOf('.'));	//copies name of existing file without the file type
		String name= "C:\\Users\\andre\\OneDrive\\Desktop\\nand2tetris\\projects\\06\\rect\\Rect.asm";
		
	
		assembler.initializeFiles(name);
		assembler.firstPass(name, sTable);
		
		assembler.secondPass(name, sTable, "C:\\Users\\andre\\OneDrive\\Desktop\\nand2tetris\\projects\\06\\rect\\Rect.asm"); 
		assembler.closeFiles();
	}//main

}//end class