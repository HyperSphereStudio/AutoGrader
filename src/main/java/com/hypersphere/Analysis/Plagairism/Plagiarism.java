package com.hypersphere.Analysis.Plagairism;

import com.hypersphere.Analysis.Static.Code;
import com.hypersphere.Analysis.Static.IntVector;
import com.hypersphere.Grader.GraderFrame;
import com.hypersphere.Parse.CBaseListener;
import com.hypersphere.Parse.CParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Plagiarism{

    private final List<SubmissionPair> pairs, normalizedPairs;

    public Plagiarism(File instructorFile, File submissionDirectory){
        Submission base = null;
        if(instructorFile != null && instructorFile.exists())
            base = new Submission(instructorFile, null);
        final int[] basefreqs = base == null ? null : base.getFreqs();

        Submission[] p = Arrays.stream(submissionDirectory.listFiles()).map(x -> new Submission(x, basefreqs))
                .toArray(Submission[]::new);

        pairs = new ArrayList<>();
        normalizedPairs = new ArrayList<>();
        HashSet<String> pairSet = new HashSet<>();

        for (Submission value : p)
            for (Submission submission : p) {
                String key = value.getSource().getName() + "::" + submission.getSource().getName();
                String reverseKey = submission.getSource().getName() + "::" + value.getSource().getName();
                if (!pairSet.contains(key) && !pairSet.contains(reverseKey) && !key.equals(reverseKey)) {
                    pairs.add(new SubmissionPair(value, submission));

                    if(!value.getSource().equals(instructorFile) && !submission.getSource().equals(instructorFile)) {
                        SubmissionPair np = new SubmissionPair(value, submission);
                        np.setNormalizedMode(true);
                        normalizedPairs.add(np);
                    }

                    pairSet.add(key);
                }
            }

        Collections.sort(pairs, Collections.reverseOrder());
        Collections.sort(normalizedPairs, Collections.reverseOrder());
    }

    public List<SubmissionPair> getAllPairs(File sourceFile){
        List<SubmissionPair> l = new ArrayList<>();
        for(SubmissionPair pair : pairs){
            if(pair.getSub1().getSource().equals(sourceFile) || pair.getSub2().getSource().equals(sourceFile)){
                l.add(pair);
            }
        }
        return l;
    }

    public List<SubmissionPair> getAllNormalizedPairs(File sourceFile){
        List<SubmissionPair> l = new ArrayList<>();
        for(SubmissionPair pair : normalizedPairs){
            if(pair.getSub1().getSource().equals(sourceFile) || pair.getSub2().getSource().equals(sourceFile)){
                l.add(pair);
            }
        }
        return l;
    }

    public List<SubmissionPair> getPairs(){
        return pairs;
    }

    public List<SubmissionPair> getNormalizedPairs(){
        return normalizedPairs;
    }

    public String toString(){
        return "Plagiarism Test. Pair Results:" + pairs + " \nNormalized Pair Results:" + normalizedPairs;
    }

    public static void Test(){
        try{
              new GraderFrame();
          //  Code c = new Code(new File("file/test.c"));
           // System.out.println(c);
         //   Utils.println(new Plagiarism(new File("file/test.c"), new File("file")));
        }catch(Exception e){
            e.printStackTrace();
        }
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
