package com.onekin.featurecloud.utils;

import com.onekin.featurecloud.exceptions.PythonExecutionErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class NewickUtils {
    private NewickUtils() {
        // Hide public constructor
    }

    private static final Logger logger = LogManager.getLogger(NewickUtils.class);

    public static String getNewickFormatString(String featureList) {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("temp2.txt"))) {
            writer.write(featureList);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
        String ret = null;

        try {
            Process p= Runtime.getRuntime().exec("python src/main/resources/Treecloud.py");
            BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
            ret = in.readLine();
            if (ret == null) {
                throw new PythonExecutionErrorException("Treecloud python execution failed the text given for the execution was:" + featureList);
            }
            ret = ret.split("] ")[1].replaceAll("\'", "").toUpperCase();
            Files.delete(Paths.get("temp2.txt"));
            return ret;
        } catch (IOException | PythonExecutionErrorException e) {
            e.printStackTrace();
        }
        return ret;

    }

}
