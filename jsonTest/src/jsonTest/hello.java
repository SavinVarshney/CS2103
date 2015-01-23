package jsonTest;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
 
public class hello {
     public static void main(String[] args) {
 
	JSONObject obj = new JSONObject();
	obj.put("name", null);
	obj.put("age", new Integer(100));
 
	JSONArray list = new JSONArray();
	list.add("msg 1");
	list.add("msg 2");
	list.add("msg 3");
 
	obj.put("messages", list);
 
	JSONObject obj1 = new JSONObject();
	obj1.put("name", "Varshney");
	obj1.put("age", new Integer(20));
	
	try {
		PrintWriter out = new PrintWriter(new FileWriter("test.txt", false));
		out.print(obj.toJSONString() + "\r\n");
		out.write(obj1.toJSONString() + "\r\n");
		out.close();
	} catch (IOException e) {
		e.printStackTrace();
	}
 
	System.out.print(obj);
 
     }
 
}