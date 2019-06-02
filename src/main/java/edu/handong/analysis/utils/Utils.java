package edu.handong.analysis.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.commons.csv.*;
import com.google.common.io.Files;

public class Utils {
	
	public static CSVParser getLines(String file,boolean removeHeader) throws IOException{
		BufferedReader in = new BufferedReader(new FileReader(file));
        CSVParser parser = new CSVParser(in, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase()
                .withTrim());
        
        return parser;
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

