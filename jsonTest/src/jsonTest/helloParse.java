package jsonTest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import org.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
 
public class helloParse {
     public static void main(String[] args) {
 
	JSONParser parser = new JSONParser();
	String line; 
	try {
		BufferedReader br = new BufferedReader(new FileReader("test.txt"));
		while ((line = br.readLine()) != null){
			Object obj = parser.parse(line.trim());
			
		JSONObject jsonObject = (JSONObject) obj;
		
		String name = (String) jsonObject.get("DESCRIPTION");
		if(name == null){
			System.out.println("savin");
		}else{
			System.out.println(name);
		}
		
		String age = (String) jsonObject.get("LOCATION");
		System.out.println(age);
		
		}
		br.close();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
 
     }
 
}