package com.onekin.featurecloud.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class Formatting {
    private static final Logger logger = LogManager.getLogger(Formatting.class);

    private Formatting() {
        // Hide implicit public constructor
    }

    public static List<String> extractMiniPaths(List<String> longPath) {
        logger.info(longPath);
        ArrayList<String> splittedPaths = new ArrayList<>();
        Iterator<String> it = longPath.iterator();
        String filePath;
        String[] folders;
        while (it.hasNext()) {
            filePath = it.next();
            folders = filePath.split("/");
            for (int i = 0; i < folders.length - 1; i++) {
                if ((i <= 0) && (!splittedPaths.contains(folders[i])))
                    splittedPaths.add(folders[i]);
                else if ((i > 0) && (!splittedPaths.contains(folders[i - 1] + "/" + folders[i])))
                    splittedPaths.add(folders[i - 1] + "/" + folders[i]);
            }
        }
        return splittedPaths;
    }

    public static List<String> extractMiniPathsAndFile(List<String> longPath) {
        logger.info(longPath);
        ArrayList<String> splittedPaths = new ArrayList<>();
        Iterator<String> it = longPath.iterator();
        String filePath;
        String[] folders;
        while (it.hasNext()) {
            filePath = it.next();
            folders = filePath.split("/");
            for (int i = 0; i < folders.length; i++) {
                if ((i <= 0) && (!splittedPaths.contains(folders[i])))
                    splittedPaths.add(folders[i]);
                else if ((i > 0) && (!splittedPaths.contains(folders[i - 1] + "/" + folders[i]))) {
                    int j = 0;
                    while (j < i) {
                        splittedPaths.add(folders[i - 1] + "/" + folders[i]);
                        j++;
                    }

                }

            }
        }
        return splittedPaths;
    }



    public static String decodeFromBase64(String encoded) {

        byte[] valueDecoded = Base64.getDecoder().decode(encoded);
        return new String(valueDecoded);
    }

    public static List<String> stringToArrayList(String string, String parseBy) {
        if (string == null) return new ArrayList<>();
        String[] array = string.split(parseBy);
        return Arrays.asList(array);
    }


    public static String fromArrayListToString(List<String> list) {


        Iterator<String> it = list.iterator();
        StringBuilder strBuilder = new StringBuilder();
        while (it.hasNext()) {
            strBuilder.append(it.next());
        }

        return strBuilder.toString();
    }

}
