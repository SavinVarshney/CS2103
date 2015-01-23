import java.io.*;
import java.util.*;

public class TextBuddy {
	
	static ArrayList<String> arr = new ArrayList<String>();

	public static void main(String args[])throws  IOException{
		String filename = args[0];
		input(filename);
	}
	
	public static void input(String name)throws IOException{
		System.out.println("Welcome To TextBuddy. " + name +" is ready for use");
		initialize(name);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inpt = br.readLine();
		while(!inpt.equals("exit")){
			if(inpt.equals("display")){
				display(name);
			}else if(inpt.equals("clear")){
				arr.clear();
				System.out.println("all content deleted from "+ name);
			}else{
				int idx = inpt.indexOf(' ');
				try{
					String cmd = inpt.substring(0, idx);
					String data  = inpt.substring(idx+1);
					if(cmd.equals("add")){
						arr.add(data);
						System.out.println("added to " + name + " \"" + data + "\"" );
					}else if(cmd.equals("delete")){
						int temp = Integer.parseInt(data);
						System.out.println("deleted from " + name + " \"" + arr.get(temp-1) + "\"");
						arr.remove(temp-1);
					}else{
						throw new Exception();
					}
				}
				catch(Exception e){
					System.out.println("Unrecognized Command type");
				}
			}
			inpt = br.readLine();
		}
		write(name);
	}
	
	public static void initialize(String name)throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(name));
		String line = null;
		while((line = br.readLine()) != null){
			arr.add(line.substring(3));
		}
		br.close();
	}
	
	public static void display(String name)
	{
		if(arr.isEmpty()){
			System.out.println(name + " is empty");
		}else{
			for(int i=0; i<arr.size();i++){
				System.out.println((i+1) + ". " + arr.get(i));
			}
		}
	}
	
	public static void write(String name)throws IOException{
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(name, false)));
		for(int i=0; i<arr.size();i++){
			out.println((i+1) + ". " + arr.get(i));
		}
		out.close();
	}
}
