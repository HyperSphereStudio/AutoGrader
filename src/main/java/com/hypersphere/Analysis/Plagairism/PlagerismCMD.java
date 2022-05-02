package com.hypersphere.Analysis.Plagairism;

import picocli.CommandLine;

import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.Callable;

public class PlagerismCMD {

    @CommandLine.Command(name = "similarity", mixinStandardHelpOptions = true, version = "similarity 1.0",
            description = "Compare a directory of c files to a specific c file for similarity")
    static class Similarity implements Callable<Integer> {
        @CommandLine.Parameters(index = "0", description = "The base similarity file being compared against")
        private File similarity_file;

        @CommandLine.Parameters(index = "1", description = "The directory containing the c files")
        private File similarity_dir;

        @CommandLine.Option(names = {"-b", "--base"}, description = "The instructor file that will be subtracted from everything")
        private File similarity_base;

        @CommandLine.Option(names = {"-o", "--output"}, description = "Optional Output File")
        private File output_file;

        @Override
        public Integer call() throws Exception {
            int[] b1 = similarity_base == null ? null : new Submission(similarity_base,null).getFreqs();
            Submission s1 = new Submission(similarity_file, b1);

            ArrayList<SubmissionPair> pairs = new ArrayList<>();

            for(File f : similarity_dir.listFiles()){
                if(f.getName().endsWith(".c")){
                    pairs.add(new SubmissionPair(s1, new Submission(f, b1)));
                }
            }

            Collections.sort(pairs, Collections.reverseOrder());

            DecimalFormat df = new DecimalFormat(".######");

            StringBuilder sb = new StringBuilder("Rank,File,Similarity\n");
            for(int i = 0; i < pairs.size(); i++){
                sb.append(i).append(",")
                        .append(pairs.get(i).getSub2().getSource().getName())
                        .append(",")
                        .append(df.format(pairs.get(i).getSimilarity()))
                        .append("\n");
            }

            if(output_file != null){
                try(FileWriter fos = new FileWriter(output_file)){
                    fos.append(sb);
                }
            }

            System.out.println(sb);

            return 0;
        }
    }

    public static void main(String[] args){
        try{
            System.exit(new CommandLine(new Similarity()).execute(args));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
