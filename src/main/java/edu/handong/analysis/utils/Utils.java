package edu.handong.analysis.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Utils {
	
	public static ArrayList<String> getLines(String file,boolean removeHeader){
		String fileName = file;
		ArrayList<String> getLine= new ArrayList<String>();
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
			inputStream.close();
		}
		catch(IOException e) {
			System.out.println("The file path does not exist. Please check your CLI argument!");
			System.exit(0);
		}
		return getLine;
	}
	
	public static void writeAFile (ArrayList<String> lines, String targetFileName) {
		try {
	         File fp= new File(targetFileName);
	         FileOutputStream fos = new FileOutputStream(fp);
	         DataOutputStream dos =new DataOutputStream(fos);
	         
	         for(String line:lines){
	            dos.write((line+"\n").getBytes());
	         }
	         dos.close();
	         fos.close();
	      } catch (IOException e) {
	         e.printStackTrace();
	      } 
		System.out.println("complete");
	   }
	}

