package com.onekin.featurecloud.utils;

import com.onekin.featurecloud.exceptions.PythonExecutionErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;

public class NewickUtils {
    private static final Logger logger = LogManager.getLogger(NewickUtils.class);

    public static String getNewickFormatString(String featureList) {
        Process p;
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("temp2.txt"));
            writer.write(featureList);
            writer.close();
            p = Runtime.getRuntime().exec("python src/main/resources/Treecloud.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String ret = in.readLine();
            if(ret==null){
                throw new PythonExecutionErrorException("Treecloud python execution failed the text given for the execution was:" +featureList);
            }
            ret= ret.split("] ")[1].replaceAll("\'", "").toUpperCase();;

            new File("temp2.txt").delete();
            return ret;

        } catch (IOException | PythonExecutionErrorException e) {
            logger.error(e.getMessage(),e);
            return null;
        }

    }

}
