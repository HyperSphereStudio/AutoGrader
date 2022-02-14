package com.hypersphere.Analysis;

public class IntVector {

    public interface DFunction {
        double accept(int[] i);
    }

    private static double VSum(DFunction consumer, int[]... vectors){
        double sum = 0;
        int[] active = new int[vectors.length];
        for(int i = 0, len = vectors[0].length; i < len; ++i){
            for(int i2 = 0; i2 < vectors.length; ++i2)
                active[i2] = vectors[i2][i];
            sum += consumer.accept(active);
        }
        return sum;
    }

    public static double GetNormalizedCosineSimilarity(int[] freqs1, int[] freqs2, int[] basefreqs){
        freqs1 = freqs1.clone();
        freqs2 = freqs2.clone();
        for(int i = 0; i < freqs1.length; ++i){
            freqs1[i] -= basefreqs[i];
            freqs2[i] -= basefreqs[i];
        }
        return GetCosineSimilarity(freqs1, freqs2);
    }

    public static double GetCosineSimilarity(int[] freqs1, int[] freqs2){
        return VSum(i -> i[0] * i[1], freqs1, freqs2) / (GetLength(freqs1) * GetLength(freqs2));
    }

    public static double GetLength(int[] freqs){
        return Math.sqrt(VSum(i -> Math.pow(i[0], 2), freqs));
    }

}
