package com.hypersphere.Analysis.Plagairism;

import java.io.File;
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
}




