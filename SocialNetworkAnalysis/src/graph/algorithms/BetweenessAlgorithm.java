/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.algorithms;

import Factory.NodeFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;
import sorting.Element;

/**
 *
 * @author Rachita
 */
/**
 *
 * references : 'http://ella.slis.indiana.edu/~katy/L579/brandes.pdf' Brandes
 * Algorithm - a faster algorithm for betweenness centrality
 */
public class BetweenessAlgorithm {

//    public Map<Integer, Double> calculateBetweeness(double[][] matrix){
    public List<Element<String, Double>> calculateBetweeness(NodeFactory small_nodeFactory, double[][] matrix) {
        // init the hashmap

        // C = dict((v,0) for v in V)
        Map<Integer, Double> C = new HashMap<>();
        for (int c1 = 0; c1 < matrix[0].length; c1++) {
            C.put(c1, 0.0);
        }
//		System.out.println(" <init> c for " + C);

        //for s in V:
        for (int s = 0; s < matrix[0].length; s++) {
//			System.out.println("starting for "+ s);

            Stack<Integer> S = new Stack<>();

            //P = dict((w,[]) for w in V)
            Map<Integer, List<Integer>> P = new HashMap<>();
//			System.out.print(" <init> P for " + s +" ::");
            for (Integer p1 = 0; p1 < matrix[0].length; p1++) {
//				System.out.print(" " + p1);
                P.put(p1, new LinkedList<>());
            }
//			System.out.println("  P " + P);
            //g = dict((t, 0) for t in V); g[s] = 1
            Map<Integer, Double> g = new HashMap<>();
//			System.out.print("  <init> g for " + s+" ::");
            for (int g1 = 0; g1 < matrix[0].length; g1++) {
//				System.out.print("  " + g1);
                g.put(g1, 0.0);
            }
            //g[s] = 1
            g.put(s, 1.0);
//			System.out.println(" g " + g);

            //d = dict((t,-1) for t in V); d[s] = 0
            Map<Integer, Double> d = new HashMap<>();
//			System.out.print("  <init> d for " + s+" ::");
            for (int d1 = 0; d1 < matrix[0].length; d1++) {
//				System.out.print(" " + d1);
                d.put(d1, -1.0);
            }
            // d[s] = 0
            d.put(s, 0.0);
//			System.out.println("  d " + d);	

            //Q = deque([])
            LinkedList<Integer> Q = new LinkedList<>();
//			System.out.print("  <init> Q for node " + s);

            //Q.append(s)
            Q.add(s);
//			System.out.println("  adding in Q " + Q);

            //while Q:
            while (!Q.isEmpty()) {
				//System.out.println("inside");

                //v = Q.popleft()
                int v = Q.remove();
//				System.out.println("   poll first " + v +" in Q " + Q);
                // S.append(v)
                S.push(v);

                //for w in A[v]:
                //foreach neighbor w of v do
                for (int w = 0; w < matrix[v].length; w++) {
                    //for (int wInner =0 ; wInner< matrix[0].length ; wInner++) {
                    if (matrix[v][w] != 0) {
//							System.out.println("   iterating thro v,w " + v + "," + w + " " + d);
                        //System.out.println("   iterating thro w " + v);
                        // if d[w] < 0:
                        if (d.get(w) < 0) {
                            //Q.append(w)
                            Q.add(w);
//								System.out.println("    adding " + w + " in Q "
//										+ Q);
                            //Double Tvalue = d.get(w);

//			                   d[w] = d[v] + 1
                            d.put(w, d.get(v) + 1);
                        }

//							System.out.println("   checking for v,w " + v + "," + w + " " + d);
                        // if d[w] == d[v] + 1:
                        if (d.get(w) == d.get(v) + 1) {
//								System.out.println("    d[w] " + d.get(w));
//								System.out.println("    d[v] " + d.get(v));
                            // Double Tvalue = g.get(w);
                            // g[w] = g[w] + g[v]
//								System.out.println("    updating g " + w + " " + (g.get(w) + d.get(v)));
                            g.put(w, g.get(w) + g.get(v));

                            ((LinkedList) P.get(w)).add(v);

                            // P[w].append(v)
                            //Tvalue.add(v);
                            //System.out.println("   updating P[] to " + P.get(w));
//								System.out.println("   updating P " + P);
                        }
                        //	}
                    }
                }
//				System.out.println();
            }

            //e = dict((v, 0) for v in V)
            Map<Integer, Double> e = new HashMap<>();
            for (int e1 = 0; e1 < matrix[0].length; e1++) {
                e.put(e1, 0.0);
            }
//			System.out.print("  <init> e for " + e);
//			System.out.println();
//			
//			System.out.println("   Resultant P is "+ P + " for " + s);
//			
//			System.out.println("   Resultant g is "+ g);

            //  while S:
            while (!S.isEmpty()) {
//				System.out.println("  iterating S " + S + " for " + s);

                // w = S.pop()
                //int w = ((LinkedList<Double>) S).pollFirst();
                int w = S.pop();
//				System.out.println("   w " + w);
                //for v in P[w]:

                List<Integer> Ps = P.get(w);
                for (int vi = 0; vi < P.get(w).size(); vi++) {
                    int v = P.get(w).get(vi);
//					System.out.println("   iterating P element " + v);

//					System.out.println("   updating " + v +" " + e.get(v));
                    //e[v] = e[v] + (g[v]/g[w]) * (1 + e[w])
//					if(g.get(w) == 0)
//						{ System.out.println("   continue executed");continue;}
//					System.out.println("   elements of tempe " + e.get(v) + " " + g.get(v) + " " + g.get(w) + " " + e.get(w));
                    Double tempe = e.get(v) + ((g.get(v) / g.get(w)) * (1 + e.get(w)));

                    e.put(v, tempe);
//					System.out.print("   adding in e " + v + "," + tempe);
//					System.out.println();
                }

//	               if w != s:
                if (w != s) {
                    Double tempw = C.get(w);
                    tempw += e.get(w);
//						System.out.println("   adding new in C, w " + w +" tempw " + tempw );
//	                   C[w] = C[w] + e[w]
                    C.put(w, tempw);

//						System.out.println("    updating c " + w +  " -> " + tempw );
                }

//				System.out.println("   c " + C);
            }

//			System.out.println();
//			System.out.println("******");
        }

        List<Element<String, Double>> sorted_betweenneness = new ArrayList<>();
        for (int i = 0; i < C.size(); i++) {
            sorted_betweenneness.add(new Element<>(small_nodeFactory.returnIdNameMap().get(i), C.get(i)));
        }
//		return C;
        List<Element<String, Double>> sorted_betweenneness_filtered = sorted_betweenneness.stream()
			   .filter(p -> p.value > 0).collect(Collectors.toList());
        Collections.sort(sorted_betweenneness_filtered);
        sorted_betweenneness = null;
        return sorted_betweenneness_filtered;

    }

    public static void main(String[] args) {

        double[][] adjacency_matrix = new double[5][5];
        adjacency_matrix[0][1] = 1;
        adjacency_matrix[0][4] = 1;

        adjacency_matrix[1][0] = 1;
        adjacency_matrix[1][2] = 1;
        adjacency_matrix[1][4] = 1;

        adjacency_matrix[2][1] = 1;
        adjacency_matrix[2][3] = 1;

        adjacency_matrix[3][2] = 1;
        adjacency_matrix[3][4] = 1;

        adjacency_matrix[4][0] = 1;
        adjacency_matrix[4][1] = 1;
        adjacency_matrix[4][3] = 1;

        BetweenessAlgorithm ba = new BetweenessAlgorithm();
        NodeFactory nodefactory = new NodeFactory();
        nodefactory.getNode("A");
        nodefactory.getNode("B");
        nodefactory.getNode("C");
        nodefactory.getNode("D");
        nodefactory.getNode("E");
        System.out.println(ba.calculateBetweeness(nodefactory, adjacency_matrix));
    }

}
