package com.onekin.featurecloud.utils;

import com.google.gson.Gson;
import com.onekin.featurecloud.exceptions.PythonExecutionErrorException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.util.List;

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
            //Files.delete(Paths.get("temp2.txt"));
            return ret;
        } catch (IOException | PythonExecutionErrorException e) {
            e.printStackTrace();
        }
        return ret;

    }

    public static String getNewickFormatStringFromApi(List<String> featureIdList) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request =
                new HttpEntity<String>(new Gson().toJson(featureIdList), headers);
        String tree=restTemplate.postForObject("http://localhost:5000/newick-tree",request,String.class );
        tree= tree.replaceAll("\'", "").toUpperCase();
        System.out.println(tree);
        return tree;

    }


}
