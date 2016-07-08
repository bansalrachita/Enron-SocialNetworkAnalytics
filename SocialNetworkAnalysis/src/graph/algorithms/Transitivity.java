/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package graph.algorithms;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Rachita
 */
public class Transitivity {
    
    
    public Boolean[][] tClosure(Boolean[][] matrix){
        int len = matrix.length;
        for(int intermediate=0; intermediate < len; intermediate++){
            for(int source=0; source < len; source++){
                for(int target=0; target < len; target++){
                    matrix[source][target] = (matrix[source][target] 
                            || (matrix[source][intermediate] && matrix[intermediate][target]));
                  
                }
            }
        }
        return matrix;
    } 
    
    public void print(Boolean[][] matrix) throws IOException
    {
        PrintWriter outBool = new PrintWriter(new FileWriter("transitivematrix.txt",
                true), true);
        outBool.println("Printing Closure----->");
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                int t = (matrix[i][j] == true) ? 1 : 0;
                outBool.print("  " + t);
            }
            outBool.println();
        }
    }
    
    public static void printclusters(Boolean[][] matrix) throws IOException{
        
         PrintWriter out = new PrintWriter(new FileWriter("NodeClusters.txt",
                true), true);
         
         out.println("Printing Clusters----->");
         for (int i = 0; i < matrix.length; i++) {
            int sumofpaths = 0;
            for (int j = 0; j < matrix.length; j++) {
                int t = (matrix[i][j] == true) ? 1 : 0;
                sumofpaths += t;
            }
            out.print(i + ", " + sumofpaths);
            out.println();
        }
    }
}
