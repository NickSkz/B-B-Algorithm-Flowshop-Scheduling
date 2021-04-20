package com.example.Main;

import com.example.b_b.B_B;
import com.example.generator.RandomNumberGenerator;
import com.example.nodes.Node;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;

public class App {

    public static void main (String[] args) {

//        Node.times = new int[][]{
//                {6, 3},
//                {4, 1},
//                {2, 5}
//        };

//        // Exapmle from Schrage's book (from 60s)
//        Node.times = new int[][]{
//                {13, 3, 12},
//                {7, 12, 16},
//                {26, 9, 7},
//                {2, 6, 1}
//        };


        Scanner sc = new Scanner(System.in);

        System.out.print("Enter seed: ");
        long seed = sc.nextLong();

        System.out.print("Enter job number: ");
        int jobs = sc.nextInt();

        System.out.print("Enter maschine number: ");
        int maschinen = sc.nextInt();


        RandomNumberGenerator generator = new RandomNumberGenerator(seed);
        Node.times = new int[jobs][maschinen];
        for (int i = 0; i < Node.times.length; ++i) {
            for (int j = 0; j < Node.times[0].length; ++j) {
                Node.times[i][j] = generator.nextInt(1, 99);
                System.out.print(Node.times[i][j] + " ");
            }
            System.out.println();
        }

        B_B algorithm = new B_B();
        algorithm.work(Node.times.length, new Node(new ArrayList<>()));

        System.out.print("BEST PERMUTATION = ");
        System.out.println(Node.currentSolution.subPermutation.stream().map(v -> v+1).collect(Collectors.toList()));
        System.out.println("CMAX = " + Node.currentBestLU);
    }
}
