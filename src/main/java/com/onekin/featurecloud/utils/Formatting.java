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


    public static List<String> extractPathsFromPathList(List<String> paths) {
        ArrayList<String> thepaths = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            String[] parsed = path.split("/"); //[input, js, sensors.js]
            for (int j = 0; j < parsed.length; j++) {
                String partialpath = "";
                for (int z = 0; z <= j; z++) {
                    if (z == 0) partialpath = partialpath.concat(parsed[z]);
                    else partialpath = partialpath.concat("/" + parsed[z]);
                }
                if (!thepaths.contains(partialpath)) {
                    thepaths.add(partialpath);
                    logger.info("Added path: %s", partialpath);
                }
            }
        }
        return thepaths;
    }

    public static List<String> extractPathsFromPathListWitoutFilePath(List<String> paths) {
        ArrayList<String> thepaths = new ArrayList<>();

        for (int i = 0; i < paths.size(); i++) {
            String path = paths.get(i);
            String[] parsed = path.split("/"); //[input, js, sensors.js]
            for (int j = 0; j < parsed.length - 1; j++) {
                String partialpath = "";
                for (int z = 0; z <= j; z++) {
                    if (z == 0) partialpath = partialpath.concat(parsed[z]);
                    else partialpath = partialpath.concat("/" + parsed[z]);
                }
                if (!thepaths.contains(partialpath)) {
                    thepaths.add(partialpath);
                    logger.info("Added path: %s", partialpath);
                }
            }
        }
        return thepaths;
    }


    public static String decodeFromBase64(String encoded) {

        byte[] valueDecoded = Base64.getDecoder().decode(encoded);
        return new String(valueDecoded);
    }

    public static List<String> stringToArrayList(String string, String parseBy) {
        if (string == null) return new ArrayList<>();
        String[] array = string.split(parseBy);
        return Arrays.asList(array) ;
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
