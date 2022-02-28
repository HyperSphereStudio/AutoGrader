package com.hypersphere.IDE.SpellChecker;

import com.hypersphere.AutoGrader;
import com.hypersphere.GUI.GUIChild;
import com.hypersphere.IDE.CNTRL.IDEPanel;
import org.fife.com.swabunga.spell.engine.SpellDictionaryHashMap;
import org.fife.ui.rsyntaxtextarea.spell.SpellingParser;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class IDESpellChecker implements GUIChild<IDEPanel> {

    public IDESpellChecker(){}

    public static HashMap<String, Reader> ReadResourceZip(String name){
        HashMap<String, Reader> map = new HashMap<>();
        try (ZipInputStream zis = new ZipInputStream(AutoGrader.class.getResourceAsStream(name))){
            ZipEntry ze;
            while((ze = zis.getNextEntry()) != null) {
                map.put(ze.getName(), new InputStreamReader(new ByteArrayInputStream(zis.readAllBytes())));
                zis.closeEntry();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void init(JFrame frame, IDEPanel ide) {
        List<String> allowedNames = Arrays.asList("color", "labeled", "center", "ize", "yze", "programming");
        HashMap<String, Reader> zip = ReadResourceZip("/english_dic.zip");

        try {
            SpellDictionaryHashMap dict = new SpellDictionaryHashMap(zip.get("eng_com.dic"));
            for (String other : allowedNames)
                dict.addDictionary(zip.get(other + ".dic"));
            ide.getTextArea().addParser(new SpellingParser(dict));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy(JFrame frame, IDEPanel ide) {

    }
}
