import static org.junit.Assert.assertEquals;

import org.junit.Test;

import java.util.ArrayList;
import java.io.*;

public class TextBuddyTest {
	static TextBuddy obj = new TextBuddy();
	private static ArrayList<String> expect = new ArrayList<String>();
	private static void expectInitialize(){
		for(int i=0; i<5;i++){
			expect.add(Integer.toString(i+1));
		}
	}
	
	@Test
	public void testExecuteCommand()throws IOException{
		expectInitialize();
		for(int i=0; i<5; i++){
			obj.executeCommand("add "+ Integer.toString(i+1));
		}
		ArrayList<String> actual = obj.displayLines();
		for(int i=0; i<5 ; i++){
			assertEquals("check" , expect.get(i) ,  actual.get(i));
		}
		
	}
}
