package com.example.nodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Node {

    public ArrayList<Integer> subPermutation;
    public int LB;
    public static int currentGlobalLB = 99999999;
    public static Node currentSolution;
    public static int currentBestLU = 99999999;
    public static int[][] times;

    public Node(int job, ArrayList<Integer> subPermutation) {
        this.subPermutation = new ArrayList<>(subPermutation);
        this.subPermutation.add(job);
        caluclateLower();
    }

    public Node(ArrayList<Integer> subPermutation) {
        this.subPermutation = new ArrayList<>(subPermutation);
        caluclateLower();
    }

    private void caluclateLower() {

        var maxFactors = new ArrayList<Integer>();
        int[][] virtualCmax = new int[subPermutation.size()][times[0].length];

        if (subPermutation.size() < 1) {
            return;
        }

        if (subPermutation.size() > times.length) {
            return;
        }

        virtualCmax[0][0] = times[subPermutation.get(0)][0];
        //For 1 task
        for (int j = 1; j < times[0].length; ++j) {
            virtualCmax[0][j] = virtualCmax[0][j-1] +  times[subPermutation.get(0)][j];
        }



//        if (subPermutation.size() == 1) {
//            for (var i : virtualCmax[0])
//            System.out.println(i);
//        }


        for(int i = 1; i < subPermutation.size(); ++i) {
            virtualCmax[i][0] = virtualCmax[i-1][0] + times[subPermutation.get(i)][0];
            for (int j = 1; j < times[0].length; ++j) {
                virtualCmax[i][j] = Math.max(virtualCmax[i][j - 1], virtualCmax[i - 1][j]) + times[subPermutation.get(i)][j];
            }
        }

//        for (int u = 0; u < virtualCmax.length; ++u) {
//            for (int v = 0; v < virtualCmax[0].length; ++v) {
//                System.out.print(virtualCmax[u][v] + " ");
//            }
//            System.out.println();
//        }
//        System.out.println();


        int[] subsums = new int[times[0].length];
        //k - maschines
        for (int k = 0; k < times[0].length; ++k) {
            int subsum = 0;
            for (int i = 0; i < times.length; ++i) {
                if (subPermutation.contains(i)) {
                    continue;
                } else {
                    subsum += times[i][k];
                }
            }

            subsums[k] = subsum;

//            if (subPermutation.size() == 1) {
//                System.out.println("YUP " + subsums[k]);
//            }
        }


        var minimus = new int[times[0].length][times.length];
        for (int k = 0; k < times[0].length; ++k) {
            for (int i = 0; i < times.length; ++i) {
                int minimumSum = 0;

                if (subPermutation.contains(i)) {
                    continue;
                }

                for (int j = k + 1; j < times[0].length; ++j) {
                    minimumSum += times[i][j];
                }

                if (minimumSum != 0) {
                    minimus[k][i] = minimumSum;
                } else if (k == times[0].length - 1) {
                    minimus[k][i] = minimumSum;
                }
            }

//            if (subPermutation.size() == 1) {
//                System.out.println(Arrays.stream(minimus[k]).min().getAsInt());
//            }
        }

        for (int j = 0; j < times[0].length; ++j) {

            int min = 9999999;
            boolean missesMinFlag = true;
            for (int k = 0; k < minimus[j].length; ++k) {
                if (subPermutation.contains(k)) {
                    continue;
                }
                if (minimus[j][k] < min) {
                    min = minimus[j][k];
                    missesMinFlag = false;
                }
            }




//                System.out.println("C = " + virtualCmax[virtualCmax.length - 1][virtualCmax[0].length - 1]);
//                System.out.println("S = " + subsums[j]);

                if (!missesMinFlag) {
                    maxFactors.add(virtualCmax[virtualCmax.length - 1][j] + subsums[j] + min);
//                    System.out.println("M = " + min);
                } else {
                    maxFactors.add(virtualCmax[virtualCmax.length - 1][j] + subsums[j]);
//                    System.out.println("M = " + 0);
                }
//                System.out.println();


        }


//        for (var item : maxFactors) {
//            System.out.println("FEFWEFW: " + item);
//        }


        LB = Collections.max(maxFactors);
//        System.out.println(LB);

        if (LB <= currentGlobalLB) {
            currentGlobalLB = LB;
        }

        if (LB <= currentBestLU && subPermutation.size() == times.length) {
            currentBestLU = LB;
            currentSolution = this;
            System.out.println("INCOMING CURRENT BEST");
            System.out.println("CMAX = " + currentBestLU);
        }
    }
}
