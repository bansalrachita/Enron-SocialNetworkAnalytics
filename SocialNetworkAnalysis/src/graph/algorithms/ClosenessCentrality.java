/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph.algorithms;

import Factory.NodeFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import sorting.Element;

/**
 *
 * @author Rachita
 */
public class ClosenessCentrality {

    /**
     *
     * references :
     * 'http://toreopsahl.com/2010/03/20/closeness-centrality-in-networks-with-disconnected-components/'
     */
    public double[] calculateClosenessCentrality(double[][] matrix) {

        FloydWarshall floydwarshall = new FloydWarshall(matrix.length);
        double[][] floydwarshallmatrix = floydwarshall.runFloydwarshall(matrix);

		//printMatrix(floydwarshallmatrix);
        double[] closenessMatrix = new double[floydwarshallmatrix.length];
        int counterNodes = 0;
        double sumOfNodes = 0;
        for (int i = 0; i < floydwarshallmatrix.length; i++) {
            counterNodes = 0;
            sumOfNodes = 0;
            for (int j = 0; j < floydwarshallmatrix[0].length; j++) {
                counterNodes++;
                sumOfNodes += 1 / floydwarshallmatrix[i][j];
                System.out.println("1/floydwarshallmatrix[i][j]  ::" + floydwarshallmatrix[i][j]);
            }
//			}
            if (counterNodes == 0) {
                closenessMatrix[i] = 0;
            } else {
//				closenessMatrix[i] = sumOfNodes/counterNodes;
                closenessMatrix[i] = sumOfNodes / (floydwarshallmatrix.length - 1);
                System.out.println(counterNodes + " :: " + sumOfNodes + " :: " + i + " :: " + closenessMatrix[i]);
            }
        }

        return closenessMatrix;

    }

    public List<Element<String, Double>> calculateClosenessCentrality(NodeFactory small_nodeFactory, double[][] floydwarshallmatrix) {

        double[] closenessMatrix = new double[floydwarshallmatrix[0].length];

        double sumOfNodes = 0;
        for (int i = 0; i < floydwarshallmatrix.length; i++) {

            sumOfNodes = 0;
            for (int j = 0; j < floydwarshallmatrix[0].length; j++) {
                sumOfNodes += 1 / floydwarshallmatrix[i][j];
            }

            closenessMatrix[i] = sumOfNodes / (floydwarshallmatrix.length - 1);
            System.out.println(sumOfNodes + " :: " + i + " :: " + closenessMatrix[i]);

        }
        floydwarshallmatrix = null;
        List<Element<String, Double>> sorted_closeness = new ArrayList<>();
//        System.out.print("sorted_closeness : " );
        for (int i = 0; i < closenessMatrix.length; i++) {
//            System.out.print(small_nodeFactory.returnIdNameMap().get(i));
            sorted_closeness.add(new Element<>(small_nodeFactory.returnIdNameMap().get(i), closenessMatrix[i]));
        }
        closenessMatrix = null;
        List<Element<String, Double>> sorted_closeness_filtered = sorted_closeness.stream()
			   .filter(p -> p.value > 0).collect(Collectors.toList());
        Collections.sort(sorted_closeness_filtered);
        sorted_closeness = null;
        //return closenessMatrix;
        return sorted_closeness_filtered;

    }

    public static void main(String[] arg) {
        double floydwarshallmatrix[][];

//		 double[][] adjacency_matrix = new double[][] {
//		 { 1, 1, 0, 0, 1, 0, 0 },
//		 { 1, 1, 0, 0, 1, 0, 0 },
//		 { 0, 0, 1, 1, 1, 0, 0 },
//		 { 0, 0, 1, 1, 1, 0, 0 },
//		 { 1, 1, 1, 1, 1, 0.5, 1 },
//		 { 0, 0, 0, 0, 1, 1, 1 },
//		 { 0, 0, 0, 0, 1, 1, 1 }, };
//		double[][] adjacency_matrix = new double[][] { 
//				{ 0, 1, 0, 1 },
//				{ 0, 1, 1, 1 },
//				{ 0, 0, 0, 1 }, 
//				{ 0, 0, 0, 0 } };
//		
		/*
         * http://www.geeksforgeeks.org/dynamic-programming-set-16-floyd-warshall-algorithm/
         * Following matrix shows the shortest distances between every pair of vertices
         *     0      5      8      9
         *   INF      0      3      4
         *   INF    INF      0      1
         *   INF    INF    INF      0
         */
        double[][] adjacency_matrix = new double[8][8];
        adjacency_matrix[0][1] = 1;
        //adjacency_matrix[2][1]=3;
        adjacency_matrix[2][0] = 1;
        adjacency_matrix[2][6] = 1;
        adjacency_matrix[3][1] = 1;
        adjacency_matrix[4][6] = 1;
        adjacency_matrix[6][4] = 1;
        adjacency_matrix[6][5] = 1;
        adjacency_matrix[6][7] = 1;
        adjacency_matrix[7][5] = 1;
        FloydWarshall floydwarshall = new FloydWarshall(adjacency_matrix.length);
        floydwarshallmatrix = floydwarshall.runFloydwarshall(adjacency_matrix);
        System.out.println("floydWarshall matrix");
        floydwarshall.printMatrix(floydwarshallmatrix);
//		System.out.println("floydWarshall matrix");
        ClosenessCentrality c = new ClosenessCentrality();
        floydwarshall.printSingleMatrix(c.calculateClosenessCentrality(adjacency_matrix));

//                if(1/INFINITY-EPSILON < 0)
//                    System.out.println(1/(int)(INFINITY));
        // scan.close();
    }
}
