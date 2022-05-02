package com.hypersphere.Analysis.Plagairism;

public class SubmissionPair implements Comparable<SubmissionPair> {

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