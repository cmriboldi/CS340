package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JsonFileLoader {
	
	public static String readFile(String fileName) throws IOException 
	{
	    BufferedReader br = new BufferedReader(new FileReader(fileName));
	    
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            line = br.readLine();
	            if(line != null)
	            	sb.append("\n");
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
}
