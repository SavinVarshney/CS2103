/*This class is written to implement a command line editor that allows user to input text,delete it, 
 * clear the entries and writes the data on exit.The initial data is initialized in an arraylist and is 
 * then edited upon by series of commands.The CLI takes account of 5 types of commands - add,delete,clear, 
 * display, exit while the rest are categorized as invalid commands.upon exit the final data is writtenback
 * to file.The file is not in append mode so evertime the old data is initialized first and then after series of
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
	
	// These are the possible command types
	enum COMMAND_TYPE {
		ADD, DELETE, DISPLAY, CLEAR, EXIT, INVALID
	};

	// This arraylist is to store the series of commands
	private static ArrayList<String> arr = new ArrayList<String>();
	
	private static Scanner scanner = new Scanner(System.in);
	
	// This stores the name of file on which operations are being done
	private static String fileName;
	
	public static void main(String args[])throws  IOException{
		fileName = args[0];
		showToUser(String.format(MESSAGE_WELCOME, fileName));
		input();
	}
	
	private static void input()throws IOException{
		initialize();
		while(true){
			String command = scanner.nextLine();
			String userCommand = command ;
			executeCommand(userCommand);
		}
	}

	/* The command entered by the user is executed and necessary action is taken depending on his choice
	 * @param userCommand - command entered by user
	 * */
	private static void executeCommand(String userCommand)throws IOException {
		if (userCommand.trim().equals("")){
			showToUser(String.format(MESSAGE_INVALID_COMMAND));
		}
		
		String commandTypeString = getFirstWord(userCommand);

		COMMAND_TYPE commandType = determineCommandType(commandTypeString);
		try
		{
			switch (commandType) {
			case ADD:
				add(userCommand);
				break;
			case DELETE:
				delete(userCommand);
				break;
			case CLEAR:
				clear();
				break;
			case DISPLAY:
				display();
				break;
			case INVALID:
				 showToUser(String.format(MESSAGE_INVALID_COMMAND));
				 break;
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
			arr.add(line.substring(3));
		}
		br.close();
	}
	
	/**
	 * This operation displays the content of the arraylist
	 */
	private static void display()
	{
		if(arr.isEmpty()){
			showToUser(String.format(MESSAGE_EMPTY, fileName));
		}else{
			for(int i=0; i<arr.size();i++){
				System.out.println((i+1) + ". " + arr.get(i));
			}
		}
	}
	
	/**
	 * This operation adds the data associated with userCommand to the file
	 * @param userCommand
	 */
	private static void add(String userCommand){
		String data  = userCommand.substring(4);
		arr.add(data);
		showToUser(String.format(MESSAGE_ADD, fileName, data));
	}
	
	/**
	 * This operation deletes the data associated with userCommand from the file
	 * @param userCommand
	 */
	private static void delete(String userCommand){
		int idx = userCommand.indexOf(' ');
		String data  = userCommand.substring(idx+1);
		int temp = Integer.parseInt(data);
		showToUser(String.format(MESSAGE_DELETE, fileName, arr.get(temp-1)));
		arr.remove(temp-1);
	}
	
	/**
	 * This operation clears the whole data of the file
	 */
	private static void clear(){
		arr.clear();
		showToUser(String.format(MESSAGE_CLEAR, fileName));
	}
	
	/**
	 * This operation writess the whole data in arraylist after series of operations of the file
	 */
	private static void write()throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, false)));
		for(int i=0; i<arr.size();i++){
			out.println((i+1) + ". " + arr.get(i));
		}
		out.close();
	}
}