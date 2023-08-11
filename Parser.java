//Andrew Liang CS220 


import java.text.*;
import java.io.*;

 

//Parser class to parse the input file and clean it up for the assembler
public class Parser {
	//create necessary variables for the methods
	private String stringLine;
	public static Command commandType; //A,C,L commands
	private String compMnemonic;
	private String destMnemonic;
	private String jumpMnemonic;
	
	public int lineNumber;
	private String symbol;
	public static String rawLine;
	public String strFileArr[]; //string array
	public static String cleanLine;

	public BufferedReader br;
	public static int symbValue = 16;

	//takes input file to parse
	public Parser(String exFileName){
		lineNumber = 0;

		FileInputStream fstream = null;
		try{
			fstream = new FileInputStream(exFileName);
		}
		catch (FileNotFoundException e1){
			e1.printStackTrace();
		}

		int content;
		try{
			while((content = fstream.read()) != -1){
				stringLine += (char) content;
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		//Get DataInpitSyream object
		DataInputStream in = new DataInputStream(fstream);
		br = new BufferedReader(new InputStreamReader(in));

		stringLine = removeComments(stringLine);
		//copy string to the array
		strFileArr = stringLine.split("\n");
		//trim string
		for(int i = 0; i < strFileArr.length; i++){
			strFileArr[i] = strFileArr[i].trim();
		}
		
	}
	//Command types
	public enum Command{
		A_COMMAND,C_COMMAND,L_COMMAND,NO_COMMAND
	}

	//Returns type of current command
	public Command commandType()
	{
		cleanLine = strFileArr[lineNumber];
		
		if(cleanLine.contains("(") || cleanLine.contains("("))
		{
			return commandType = Command.L_COMMAND;
		}
		else if(cleanLine.startsWith("@"))
		{
			return commandType = Command.A_COMMAND;
		}else if(cleanLine.contains("=")|| cleanLine.contains(";"))
		{
			return commandType = Command.C_COMMAND;
		}

		return Command.NO_COMMAND;
	}

	//has more commands
	public boolean hasMoreCommand(){
		if (lineNumber != strFileArr.length) 
			return true;
		else
			return false;
	}
	//if has more commands, reads next command
	public void advance(){
		if (hasMoreCommand())
			lineNumber++;
	}

	//trim, clean line
	public void cleanLine(){
		cleanLine = strFileArr[lineNumber].trim().replaceAll("//.*","");
	}

	//parse the A,C,L, or no command
	public void parseCommandType(){
		if(cleanLine.isEmpty()){
			commandType = Command.NO_COMMAND;
		}
		else if (cleanLine.startsWith("@")){
			commandType = Command.A_COMMAND;
		}
		else if (cleanLine.startsWith("(") && cleanLine.endsWith(")")){
			commandType = Command.L_COMMAND;
		}
		else{
			commandType = Command.C_COMMAND;
		}
	}


	//Symnbol parse
	String parseSymbol(){
		cleanLine = strFileArr[lineNumber];
		if(cleanLine.startsWith("@"))
		{
			symbol = cleanLine;
			symbol = symbol.replaceAll("@", "");
		}
		else 
			if(cleanLine.startsWith("("))
			{
				symbol = cleanLine;
				symbol = symbol.replaceAll("\\((.*?)\\)", "$1");
			}
		return symbol;
}

	
	//Dest parse
	public void parseDest() {
		if (cleanLine.contains("=")) {
			destMnemonic = cleanLine.split("=")[0].trim();
		}
	}

	//Comp parse
	public void parseComp() {
		String[] parts = cleanLine.split(";");
		String[] destComp = parts[0].split("=");
		compMnemonic = destComp[destComp.length - 1].trim();
	}

	//Jump Parse
	public void parseJump() {
		if (cleanLine.contains(";")) {
			jumpMnemonic = cleanLine.split(";")[1].trim();
		}
	}

	//converts decimal to binary
	public static String dexToBin(int value){
		String binary = Integer.toBinaryString(value);
		return binary;
	}

	//chekcs if string is a number
	public boolean isNum(String num){
		NumberFormat format = NumberFormat.getInstance();
		ParsePosition pos = new ParsePosition(0);
		format.parse(num,pos);
		return num.length() == pos.getIndex();

	}

	//appends 0s
	public String addZero(String num){
		StringBuilder sb = new StringBuilder();
		for(int toPrepend = 16-num.length(); toPrepend > 0; toPrepend--){
			sb.append('0');
		}
		sb.append(num);
		String result = sb.toString();
		return result;
	}

	//Getter functions for the class variables
	public Command getCommandType(){
		return commandType;
	}

	public String getSymbol(){
		return symbol;
	}

	public String getDestMnemonic(){
		parseDest();
		return destMnemonic;
	}

	public String getCompMnemonic(){
		parseComp();
		return compMnemonic;
	}

	public String getJumpMnemonic(){
		parseJump();
		return jumpMnemonic;
	}

	public int getLineNumber(){
		return lineNumber;
	}

	public String getRawLine(){
		return strFileArr[lineNumber];
	}

	public String getCleanLine(){
		return cleanLine;
	}

	
	//cleans up rawLine
	public String cleanLine(String rawLine){
		if(rawLine !=  null && rawLine.contains("//"))
			rawLine = rawLine.substring(0,rawLine.indexOf("/")).trim();
		else
			rawLine = rawLine.trim();

		return rawLine;
	}

	//removes comments
	public String removeComments(String file){
		String tmpFile = file.replaceAll("//.*|(\"(?:\\\\[^\"]|\\\\\"|.)*?\")|(?s)/\\*.*?\\*/|(?m)^[ \t]*\r?\n|null|\t", "");
		tmpFile = tmpFile.replaceAll("(?m)^[ \t]*\r?\n", "");
		return tmpFile;
	}
}
