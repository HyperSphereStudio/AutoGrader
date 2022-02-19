package com.hypersphere;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.hypersphere.Parse.CParser;
import org.antlr.v4.runtime.ParserRuleContext;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;



public class Utils {

    private static final List<String> rules = Arrays.asList(CParser.ruleNames);

    public static final ObjectMapper mapper = createMapper();

    public static void printStringTree(ParserRuleContext ctx){
        println(ctx.toStringTree(rules));
    }

    public static String join(Object... array){
        return join("", array);
    }

    public static String joins(List l){
        return join(" ", l);
    }

    public static String joins(Object... array){
        return join(" ", array);
    }

    public static String emptynull(Object o){
        return o == null ? "" : o.toString();
    }

    public static <T> String join(String delim, T... array){
        if(array == null || array.length == 0)
            return "";
        StringBuilder sb = new StringBuilder(array[0] == null ? "" : array[0].toString());
        for(int i = 1; i < array.length; ++i){
            sb.append(delim);
            if(array[i] != null)
                sb.append(array[i]);
        }
        return sb.toString();
    }

    public static String join(String delim, List list){
        if(list == null || list.size() == 0)
            return "";
        StringBuilder sb = new StringBuilder(list.get(0) == null ? "" : list.get(0).toString());
        for(int i = 1; i < list.size(); ++i){
            sb.append(delim);
            if(list.get(i) != null)
                sb.append(list.get(i));
        }
        return sb.toString();
    }

    public static boolean browseurl(URL url){
            Desktop desktop = Desktop.getDesktop();
            try {
                desktop.browse(url.toURI());
                return true;
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
                return false;
            }
    }

    private static ObjectMapper createMapper(){
        ObjectMapper map = new ObjectMapper();
        map.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return map;
    }


    public static <T> T fromJson(Reader r, Class<T> clazz){
        try {
            return mapper.readValue(r, clazz);
        }catch(MismatchedInputException e){
            try{
                return mapper.readValue("{}", clazz);
            }catch(Exception e2){
                e.printStackTrace();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void toPrettyJson(Writer w, Object o){
        try{
            mapper.writerWithDefaultPrettyPrinter().writeValue(w, o);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String toPrettyJson(Object o){
        try{
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static File mk(File f, boolean dir){
        if(!f.exists()) {
            try {
                if(dir)
                    f.mkdirs();
                else f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static <T> List<T> SyncList(){
        return Collections.synchronizedList(new ArrayList<T>());
    }

    public static void println(Object... strs){
        for(Object str : strs)
            System.out.println(str);
    }

    public static void print(Object... strs){
        for(Object str : strs)
            System.out.print(str);
    }

    public static void WriteFile(String s, File f){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(f))){
            bw.append(s);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String ReadFile(File f){
        try{
            return new String ( Files.readAllBytes( f.toPath() ) );
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
