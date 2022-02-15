package com.hypersphere.Plagairism;

import com.hypersphere.Analysis.Code;
import com.hypersphere.Analysis.Impl;
import com.hypersphere.Analysis.IntVector;
import com.hypersphere.Parse.CParser;
import com.hypersphere.Utils;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.util.*;

public class Plagiarism{

    public static void Test(){
        try{
            CheckForPlagiarism(new File("file/test.c"), new File("file"));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void CheckForPlagiarism(File baseFile, File fileDir){
        Submission base = null;
        if(baseFile != null && baseFile.exists())
            base = new Submission(baseFile, null);
        final int[] basefreqs = base == null ? null : base.getFreqs();

        Submission[] p = Arrays.stream(fileDir.listFiles()).map(x -> new Submission(x, basefreqs))
                .toArray(Submission[]::new);

        List<SubmissionPair> pairs = new ArrayList<>();
        List<SubmissionPair> normalizedpairs = new ArrayList<>();
        HashSet<String> pairSet = new HashSet<>();

        for(int i = 0; i < p.length; ++i)
            for(int i2 = 0; i2 < p.length; ++i2){
                String key = p[i].getSource().getName() + "::" + p[i2].getSource().getName();
                String reverseKey = p[i2].getSource().getName() + "::" + p[i].getSource().getName();
                if(!pairSet.contains(key) && !pairSet.contains(reverseKey) && !key.equals(reverseKey)){
                    pairs.add(new SubmissionPair(p[i], p[i2]));

                    SubmissionPair np = new SubmissionPair(p[i], p[i2]);
                    np.setNormalizedMode(true);
                    normalizedpairs.add(np);

                    pairSet.add(key);
                }
            }

        Collections.sort(pairs, Collections.reverseOrder());
        Collections.sort(normalizedpairs, Collections.reverseOrder());

        Utils.println(pairs);
        Utils.println(normalizedpairs);
    }
}

class SubmissionPair implements Comparable<SubmissionPair> {

    private boolean normalizedMode;
    private double similarity;
    private Submission sub1, sub2;

    public SubmissionPair(Submission sub1, Submission sub2){
        this.sub1 = sub1;
        this.sub2 = sub2;
        this.similarity = sub1.getSimilarity(sub2);
    }

    @Override
    public int compareTo(SubmissionPair o) {
        return Double.compare(similarity, o.similarity);
    }

    public boolean isNormalizedMode() {
        return normalizedMode;
    }

    public void setNormalizedMode(boolean normalizedMode) {
        this.normalizedMode = normalizedMode;
        setSimilarity(getSub1().getNormalizedSimilarity(getSub2()));
    }

    public double getSimilarity() {
        return similarity;
    }

    public void setSimilarity(double similarity) {
        this.similarity = similarity;
    }

    public Submission getSub1() {
        return sub1;
    }

    public void setSub1(Submission sub1) {
        this.sub1 = sub1;
    }

    public Submission getSub2() {
        return sub2;
    }

    public void setSub2(Submission sub2) {
        this.sub2 = sub2;
    }

    public String toString(){
        return "Pair{" + similarity + "}(" + sub1 + "," + sub2 + ")";
    }
}

class Submission {

    private final File source;
    private final int[] freqs = new int[CParser.ruleNames.length];
    private int[] normalizedfreqs;

    public Submission(File f, int[] basefreqs) {
        source = f;
        try(FileReader fr = new FileReader(f)){

            CParser cp = Code.getParser(fr);
            CParser.FunctionDefinitionContext pc = cp.functionDefinition();

            recursiveWalk(pc, freqs);

            if(basefreqs != null){
                normalizedfreqs = new int[freqs.length];
                for(int i = 0; i < freqs.length; ++i){
                    normalizedfreqs[i] = freqs[i] - basefreqs[i];
                }
            }
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

    private static void recursiveWalk(ParseTree tree, int[] freqs){
        if(tree != null){
            for(int i = 0; i < tree.getChildCount(); ++i){
                recursiveWalk(tree.getChild(i), freqs);
            }
            if(tree instanceof ParserRuleContext)
                freqs[((ParserRuleContext) tree).getRuleIndex()]++;
        }
    }
}
