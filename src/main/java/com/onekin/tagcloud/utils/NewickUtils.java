package com.onekin.tagcloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NewickUtils {

	public static String getNewickFormatString(String featureList)  {
		System.out.println(featureList);
		Process p;
		try {
			p = Runtime.getRuntime().exec("python Treecloud.py \""+ featureList+"\"");
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String ret = in.readLine();		
			ret= ret.split("] ")[1].replaceAll("\'", "").toUpperCase();
			return ret;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	
	}
	
}
