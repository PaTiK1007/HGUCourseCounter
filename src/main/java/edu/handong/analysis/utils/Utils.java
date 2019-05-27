package edu.handong.analysis.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	
	public static ArrayList<String> getLines(String file,boolean removeHeader){
		String fileName = file;
		ArrayList<String> getLine = null;
		try
		{
			// Skip the header line by reading and ignoring it
			Scanner inputStream = new Scanner(new File(fileName));
			String line = inputStream.nextLine();
			
			
			// Read the rest of the file line by line
			while (inputStream.hasNextLine())
			{
				line = inputStream.nextLine();
				getLine.add(line);
				
			}
			inputStream.close( );
		}
		catch(FileNotFoundException e) {
			System.out.println("Cannot find file " + fileName);
		}
		return getLine;
	}
	
	public static void writeAFile (ArrayList<String> lines, String targetFileName) {
		
	}

}