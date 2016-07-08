package graph.algorithms;

public class FloydWarshall {

    private double distancematrix[][];
    private double floydwarshallmatrix[][];
    private final double numberofvertices;
    public static final double INFINITY = Double.POSITIVE_INFINITY;
//        public static final double EPSILON = 0.000001;

    public FloydWarshall(int numberofvertices) {
        distancematrix = new double[numberofvertices][numberofvertices];
        floydwarshallmatrix = new double[numberofvertices][numberofvertices];
        this.numberofvertices = numberofvertices;
    }

    /**
 *
 * @returns a matrix which the shortest path of every Node from every other Node
 * All pairs shortest path
 */
    public double[][] runFloydwarshall(double adjacencymatrix[][]) {
        // normalize matrix
        for (int source = 0; source < numberofvertices; source++) {
            for (int destination = 0; destination < numberofvertices; destination++) {
                if (adjacencymatrix[source][destination] >= 1) {
                    distancematrix[source][destination] = 1;
                } else {
                    distancematrix[source][destination] = INFINITY;
                }
            }
        }

        for (int intermediate = 0; intermediate < numberofvertices; intermediate++) {
            for (int source = 0; source < numberofvertices; source++) {
                for (int destination = 0; destination < numberofvertices; destination++) {
                    if (distancematrix[source][intermediate]
                            + distancematrix[intermediate][destination] < distancematrix[source][destination]) {
                        distancematrix[source][destination] = distancematrix[source][intermediate]
                                + distancematrix[intermediate][destination];
                    }
                }
            }
        }

        for (int source = 0; source < numberofvertices; source++) {

            for (int destination = 0; destination < numberofvertices; destination++) {
                floydwarshallmatrix[source][destination] = distancematrix[source][destination];
            }

        }
        distancematrix = null;
        return floydwarshallmatrix;

    }

    public void printMatrix(double[][] matrix) {
        System.out.println();
        for (int source = 0; source < numberofvertices; source++) {
            System.out.print(source + "\t");
            for (int destination = 0; destination < numberofvertices; destination++) {
                System.out.print(matrix[source][destination] + "\t");
            }
            System.out.println();
        }
    }

    public void printSingleMatrix(double[] matrix) {
        System.out.println();
        for (int source = 0; source < numberofvertices; source++) {
            System.out.print(source + "\t" + matrix[source]);
            System.out.println();
        }
    }

}
