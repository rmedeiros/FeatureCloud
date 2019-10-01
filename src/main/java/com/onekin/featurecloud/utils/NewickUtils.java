package com.onekin.featurecloud.utils;

import java.io.*;

public class NewickUtils {

    public static String getNewickFormatString(String featureList) {
        Process p;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp2.txt"));
            writer.write(featureList);
            writer.close();
            p = Runtime.getRuntime().exec("python src/main/resources/Treecloud.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
            ret = ret.split("] ")[1].replaceAll("\'", "").toUpperCase();
            new File("temp2.txt").delete();
            return ret;

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }

}
