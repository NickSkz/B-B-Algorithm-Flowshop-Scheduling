package com.example.b_b;

import com.example.nodes.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class B_B {

    public PriorityQueue<Node> nodesQueue;

    public B_B () {
//        nodesQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.LB));
        nodesQueue = new PriorityQueue<>((n1, n2) ->  {
            if (n1.LB <= n2.LB) {
                return -1;
            } else {
                return 1;
            }
        });
    }

    public void work(int n, Node paterNode) {

        if (n <= 0) {
            return;
        }

        for (int i = 0; i < n; ++i) {
            if (paterNode.subPermutation.contains(i)) {
                continue;
            }
            Node tempNode = new Node(i, paterNode.subPermutation);


            nodesQueue.add(tempNode);
            System.out.println("NODE CREATED, LU = " + tempNode.LB + " MIN LU = " + Node.currentGlobalLB + " PERM SIZE: "
                    + tempNode.subPermutation.size() + " PERMUT: " + tempNode.subPermutation.stream().map(v -> v+1).collect(Collectors.toList()));

        }

        for (int i = 0; i < n; ++i) {
            if (nodesQueue.size() > 0) {
                Node tempNode = nodesQueue.remove();
                System.out.println();
                System.out.println("REMOVED NODE WIHT LU = " + tempNode.LB + " PQUEUE SIZE = " + nodesQueue.size());
                System.out.println();

                if (tempNode.LB <= Node.currentBestLU) {
                    Node.currentGlobalLB = 9999999;
                    work(n, tempNode);
                }
            }
        }
    }
}
