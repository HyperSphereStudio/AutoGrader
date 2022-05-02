package com.hypersphere;

import com.hypersphere.Analysis.Plagairism.Plagiarism;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;


public class AutoGrader{
    private static final File root = new File("");

    private static File cfile, graded_notes;
    private static File[] cfiles;
    private static String ExecName;

    private static RandomAccessFile raf;

    private static void print(Object... o){
        for(Object o2 : o)
            System.out.println(o2);
    }

    private static <T> int contains(T[] array, T item){
        for(int i = 0; i < array.length; ++i)
            if(array[i].equals(item))
                return i;
        return -1;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
            ExecName = args[0];
            cfile = new File(args[1]);
            graded_notes = new File(args[2]);
            cfiles = cfile.listFiles();


            raf = new RandomAccessFile(new File("main.c"), "rw");
            String mainStr = Files.readAllLines(new File("main.c").toPath()).get(0).substring(10);
            exec(contains(cfiles, mainStr.substring(0, mainStr.length() - 1)));
            raf.close();
    }

    private static void exec(int startFileIdx) throws IOException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int fileIdx = startFileIdx;

        while(fileIdx < cfiles.length){
            if(fileIdx > -1)
                print("Current Project:" + cfiles[fileIdx].getName() + "\t#" + fileIdx + "\tProjects Left:" + (cfiles.length - fileIdx - 1));
            else
                print("No Current Project Set!");
            print("MENU:");
            print("\tGet by Index:1");

            if(fileIdx > -1)
                print("\tGet by Delta:2");

            print("\tNext:Enter");
            print("\tRetry:3");
            fileIdx = run(scanner, fileIdx);
        }

        print("com.hypersphere.AutoGrader Complete!");
        print("Would you like to exit?(y/n)");

        while(true){
            String val = scanner.nextLine();
            if(val.equals("n")){
                exec(startFileIdx);
            }else if(val.equals("y")){
                System.exit(0);
            }else{
                print("Unknown Symbol:" + val);
            }
        }
    }

    private static int run(Scanner scanner, int fileIdx) throws IOException, InterruptedException {
        String in = scanner.nextLine();
        if(in.isEmpty())
            return setfile(scanner,fileIdx + 1);
        switch (in) {
            case "1":
                print("Enter Idx:");
                return setfile(scanner, Integer.parseInt(scanner.nextLine()));
            case "2":
                print("Enter Delta:");
                return setfile(scanner, fileIdx + Integer.parseInt(scanner.nextLine()));
            case "3":
                return setfile(scanner, fileIdx);
            default:
                print("Unknown Symbol:" + in);
                return fileIdx;
        }
    }

    private static int setfile(Scanner scanner, int idx) throws IOException, InterruptedException {
        if(idx < 0 || idx >= cfiles.length){
            print("Invalid Idx!");
            return idx;
        }
        raf.setLength(0);
        raf.writeBytes("#include \"" + cfiles[idx].getName() + "\"");
        print("Compiling Program....");
        execProc("make V=1");
        print("Executing Program...");
        print("Current Project:" + cfiles[idx].getName() + "\t#" + idx + "\tProjects Left:" + (cfiles.length - idx - 1));

        File notes = new File(graded_notes, cfiles[idx].getName().replace(".c", ".txt"));

        if(!notes.exists())
            notes.createNewFile();

        Process p = Runtime.getRuntime().exec("notepad.exe " + notes.getAbsolutePath());

        Runtime.getRuntime().exec("cmd.exe /c start \"Grading Environment\" call " + ExecName + " ^& echo: ^& pause ^& exit").waitFor();

        p.waitFor();

        Thread.sleep(500);
        return idx;
    }

    private static void execProc(String s) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(s).getInputStream()));
        String line;
        while((line = br.readLine()) != null)
            print(line);
    }
}
