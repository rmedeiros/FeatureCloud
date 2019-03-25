package com.onekin.tagcloud.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewickUtils {

	public static String getNewickFormatString(String featureList)  {
		System.out.println(featureList);
		Process p;
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("temp2.txt"));
			writer.write(featureList);
			writer.close();
			p = Runtime.getRuntime().exec("python Treecloud.py");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ret = in.readLine();		
			ret= ret.split("] ")[1].replaceAll("\'", "").toUpperCase();
			new File("temp2.txt").delete();
			return ret;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}

}
