package bachelor.project.nije214.thhym14;


import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Nicolai on 18-02-2017.
 */
public class InterpretCode {

    private StringBuilder sb = new StringBuilder();
    private String PATH = "/storage/sdcard/InteractionFirstScripts/";
    private List<File> names = new LinkedList<>();


    public List<File> getFileNames() {
        File f = new File(PATH);

        //for first run
        if (!f.exists()) {
            f.mkdirs();
        }

        //put all names in the list
        for (File file : f.listFiles()) {
            if (file.getName().endsWith(".txt")) {
                if (!names.contains(file.getName())) {
                    names.add(file);
                }
            }
        }
        return names;
    }

    public void representNamesAsList() {
        //create a gallery of sorts for the values found in getFileNames method
    }

    public String getText() throws Exception {
        getFileNames();
        if (!names.isEmpty()) {
            for (File file : names) {
                String fileName = file.getName();
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                int readFile;
                while ((readFile = br.read()) != -1) {
                    sb.append(Character.toString((char) readFile));
                }
                return sb.toString();
            }
        }
        return null;
    }


}


