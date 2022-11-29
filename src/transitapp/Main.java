package transitapp;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;

/**
 * The main class, reads input from events.txt, change file path below if needed.
 * This is the only file that should be run.
 * 
 * @author Hui Zhu
 */
public class Main {
	
	/**
	 * Reads line by line for events.txt ("src/transitapp/events.txt"), change path if needed
	 * Creates an input reader and use it read a line.
	 */
	public static void main(String[] args) throws IOException{
        String file = "src/transitapp/events.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        InputReader reader = new InputReader();
        String curLine;
        while ((curLine = bufferedReader.readLine()) != null){
           reader.read(curLine);
        }
        bufferedReader.close();
    }

}
