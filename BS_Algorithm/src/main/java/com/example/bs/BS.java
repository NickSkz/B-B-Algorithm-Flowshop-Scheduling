package com.example.bs;

import com.example.nodes.Node;

import java.util.PriorityQueue;
import java.util.stream.Collectors;

public class BS {

    public PriorityQueue<Node> nodesQueue;

    public BS() {
//        nodesQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.LB));
        nodesQueue = new PriorityQueue<>((n1, n2) ->  {
            if (n1.LB <= n2.LB) {
                return -1;
            } else {
                return 1;
            }
        });
    }

    public void work(int n, Node paterNode, int k) {

        if (n <= 0) {
            return;
        }

        var tempQueue = new PriorityQueue<Node>((n1, n2) ->  {
            if (n1.LB <= n2.LB) {
                return -1;
            } else {
                return 1;
            }
        });

        for (int i = 0; i < n; ++i) {
            if (paterNode.subPermutation.contains(i)) {
                continue;
            }
            Node tempNode = new Node(i, paterNode.subPermutation);


            tempQueue.add(tempNode);
        }

        int tempSize = tempQueue.size();
        for (int i = 0; i < tempSize; ++i) {

            if (i == k) {
                break;
            }

            Node temp = tempQueue.remove();
            nodesQueue.add(temp);
            System.out.println("NODE CREATED, LU = " + temp.LB + " MIN LU = " + Node.currentGlobalLB + " PERM SIZE: "
                    + temp.subPermutation.size() + " PERMUT: " + temp.subPermutation.stream().map(v -> v+1).collect(Collectors.toList()));
        }

        for (int i = 0; i < n; ++i) {
            if (nodesQueue.size() > 0) {
                Node tempNode = nodesQueue.remove();
                System.out.println();
                System.out.println("REMOVED NODE WIHT LU = " + tempNode.LB + " PQUEUE SIZE = " + nodesQueue.size());
                System.out.println();

                if (tempNode.LB <= Node.currentBestLU) {
                    Node.currentGlobalLB = 9999999;
                    work(n, tempNode, k);
                }
            }
        }
    }
}
