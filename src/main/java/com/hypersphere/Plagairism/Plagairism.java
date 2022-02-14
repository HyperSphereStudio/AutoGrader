package com.hypersphere.Plagairism;

import com.hypersphere.Analysis.Code;
import com.hypersphere.Utils;

import java.io.File;
import java.io.FileReader;

public class Plagairism {

    public static void Test(){
        try{
            CheckForPlagairsm(null, new File("file"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void CheckForPlagairsm(File baseDir, File fileDir) throws Exception {
        for(File subFile : fileDir.listFiles()){
            Utils.println("Code in:" + subFile);
            Utils.println(new Code(new FileReader(subFile)));
            Utils.println("\n\n");
        }
    }


}
