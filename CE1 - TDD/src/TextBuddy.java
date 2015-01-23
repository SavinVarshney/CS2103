/*This class is written to implement a command line editor that allows user to input text,delete it, 
 * clear the entries and writes the data on exit.The initial data is initialized in an arraylist and is 
 * then edited upon by series of commands.The CLI takes account of 5 types of commands - add,delete,clear, 
 * display, exit while the rest are categorized as invalid commands.
 * The data is written to file as soon as user enters the command rather than just on exit
 * The file is not in append mode so every time the old data is initialized first and then after series of
 * operations is written back to file.
 * the user can follow the following command format
 * 
 * add "command"
 * delete Line_Number
 * display
 * clear
 * exit
 * 
 * @author Savin Varshney
 */
import java.util.ArrayList;

import java.util.Scanner;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;

import java.io.IOException;

public class TextBuddy {
	
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ADD = "added to %1$s %2$s ";
	private static final String MESSAGE_DELETE = "deleted from %1$s %2$s";
	private static final String MESSAGE_CLEAR = "all content deleted from %1$s";
	private static final String MESSAGE_INVALID_COMMAND = "invalid command";
	private static final String MESSAGE_EMPTY = "%1$s is empty";
	
	private static final int EXTRACT_LINES_BEGIN_INDEX = 3;
	private static final int EXTRACT_ADD_BEGIN_INDEX = 4;
	
	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DELETE, DISPLAY, CLEAR, EXIT, INVALID
	};

	// This arraylist is to store the series of commands
	private static ArrayList<String> lines = new ArrayList<String>();
	
	private static Scanner scanner = new Scanner(System.in);
	
	// This stores the name of file on which operations are being done
	private static String fileName;
	
	public static void main(String args[])throws  IOException{
		fileName = args[0];
		showToUser(String.format(MESSAGE_WELCOME, fileName));
		userInput();
	}
	
	public static void userInput()throws IOException{
		initialize();
		while(true){
			String command = scanner.nextLine();
			String userCommand = command ;
			showToUser(executeCommand(userCommand));
			write();
		}
	}

	/* The command entered by the user is executed and necessary action is taken depending on his choice
	 * @param userCommand - command entered by user
	 * */
	public static String executeCommand(String userCommand)throws IOException {
		if (userCommand.trim().equals("")){
			return String.format(MESSAGE_INVALID_COMMAND);
		}
		
		String commandTypeString = getFirstWord(userCommand);

		COMMAND_TYPE commandType = determineCommandType(commandTypeString);
		try
		{
			switch (commandType) {
			case ADD:
				return add(userCommand);
			case DELETE:
				return delete(userCommand);
			case CLEAR:
				return clear();
			case DISPLAY:
				return display();
			case INVALID:
				 return String.format(MESSAGE_INVALID_COMMAND);
			case EXIT:
				write();
				System.exit(0);
			default:
				//throws a error for invalid commands
				throw new Error(MESSAGE_INVALID_COMMAND);
			}
		}
		catch(Exception e){
			System.out.println(String.format(MESSAGE_INVALID_COMMAND));
		}
		return "";
	}
	
	/**
	 * This operation determines which of the supported command types the user
	 * wants to perform
	 * 
	 * @param commandTypeString
	 *            is the first word of the user command
	 */
	private static COMMAND_TYPE determineCommandType(String commandTypeString)throws IOException {
		if (commandTypeString.equalsIgnoreCase("add")) {
			return COMMAND_TYPE.ADD;
		} else if (commandTypeString.equalsIgnoreCase("delete")) {
			return COMMAND_TYPE.DELETE;
		} else if (commandTypeString.equalsIgnoreCase("exit")) {
		 	return COMMAND_TYPE.EXIT;
		} else if (commandTypeString.equalsIgnoreCase("display")) {
		 	return COMMAND_TYPE.DISPLAY;
		}else if (commandTypeString.equalsIgnoreCase("clear")) {
		 	return COMMAND_TYPE.CLEAR;
		}else {
			return COMMAND_TYPE.INVALID;
		}
	}
	
	/**
	 * This operation returns the 1st word of userCommand
	 * @param userCommand
	 */
	private static String getFirstWord(String userCommand) {
		String commandTypeString = userCommand.trim().split("\\s+")[0];
		return commandTypeString;
	}
	
	private static void showToUser(String text) {
		System.out.println(text);
	}
	
	/**
	 * This operation initializes the arraylist with contents of the file
	 */
	private static void initialize()throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = null;
		while((line = br.readLine()) != null){
			lines.add(line.substring(EXTRACT_LINES_BEGIN_INDEX));
		}
		br.close();
	}
	
	/**
	 * This operation displays the content of the arraylist
	 */
	private static String display()
	{
		String line = "";
		if(lines.isEmpty()){
			return String.format(MESSAGE_EMPTY, fileName);
		}else{
			for(int i=0; i<lines.size();i++){
				line = line + (i+1) + ". " + lines.get(i) + "\n";
			}
			return line;
		}
	}
	
	public static ArrayList<String> displayLines(){
		return lines;
	}
	
	/**
	 * This operation adds the data associated with userCommand to the file
	 * @param userCommand
	 */
	public static String add(String userCommand){
		String data  = userCommand.substring(EXTRACT_ADD_BEGIN_INDEX);
		lines.add(data);
		return String.format(MESSAGE_ADD, fileName, data);
	}
	
	/**
	 * This operation deletes the data associated with userCommand from the file
	 * @param userCommand
	 */
	private static String delete(String userCommand){
		int idx = userCommand.indexOf(' ');
		String data  = userCommand.substring(idx+1);
		int temp = Integer.parseInt(data);
		String line = lines.get(temp-1);
		lines.remove(temp-1);
		return String.format(MESSAGE_DELETE, fileName, line);
		
	}
	
	/**
	 * This operation clears the whole data of the file
	 */
	private static String clear(){
		lines.clear();
		return String.format(MESSAGE_CLEAR, fileName);
	}
	
	/**
	 * This operation writess the whole data in arraylist after series of operations of the file
	 */
	private static void write()throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
		for(int i=0; i<lines.size();i++){
			out.println((i+1) + ". " + lines.get(i));
		}
		out.close();
	}
}