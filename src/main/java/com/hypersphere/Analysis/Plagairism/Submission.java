package com.hypersphere.Analysis.Plagairism;

import com.hypersphere.Analysis.Static.Code;
import com.hypersphere.Analysis.Static.IntVector;
import com.hypersphere.Parse.CBaseListener;
import com.hypersphere.Parse.CParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileReader;

public class Submission {

    private final File source;
    private final int[] freqs = new int[CParser.ruleNames.length];
    private int[] normalizedfreqs;

    public Submission(File f, int[] basefreqs) {
        source = f;
        try(FileReader fr = new FileReader(f)){

            new ParseTreeWalker().walk(new CBaseListener(){
                @Override
                public void enterEveryRule(ParserRuleContext ctx) {
                    freqs[ctx.getRuleIndex()]++;
                }
            }, Code.getParser(fr).translationUnit());

            if(basefreqs != null){
                normalizedfreqs = new int[freqs.length];
                for(int i = 0; i < freqs.length; ++i){
                    normalizedfreqs[i] = freqs[i] - basefreqs[i];
                }
            }else normalizedfreqs = freqs;
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public double getSimilarity(Submission p) {
        return (IntVector.GetCosineSimilarity(freqs, p.freqs) + 1) / 2.0;
    }

    public double getNormalizedSimilarity(Submission p) {
        return (IntVector.GetCosineSimilarity(normalizedfreqs, p.normalizedfreqs) + 1) / 2.0;
    }

    public File getSource() {
        return source;
    }

    public int[] getFreqs() {
        return freqs;
    }

    public int[] getNormalizedfreqs() {
        return normalizedfreqs;
    }

    public void setNormalizedfreqs(int[] normalizedfreqs) {
        this.normalizedfreqs = normalizedfreqs;
    }

    public String toString(){
        return source.getName();
    }

}