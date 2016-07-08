package graph.algorithms;
import java.util.ArrayList;
import java.util.List;
import org.jblas.ComplexDouble;
import org.jblas.ComplexDoubleMatrix;
import org.jblas.DoubleMatrix;
import org.jblas.Eigen;
//cited from 'http://www.markhneedham.com/blog/2013/08/05/javajblas-calculating-eigenvector-centrality-of-an-adjacency-matrix/'

public class EigenUtil {

	public static List<Double> getPrincipalEigenvector(DoubleMatrix matrix) {
		int maxIndex = getMaxIndex(matrix);
		ComplexDoubleMatrix eigenVectors = Eigen.eigenvectors(matrix)[0];
		return getEigenVector(eigenVectors, maxIndex);
	}

	private static int getMaxIndex(DoubleMatrix matrix) {
		ComplexDouble[] doubleMatrix = Eigen.eigenvalues(matrix).toArray();
		int maxIndex = 0;
		for (int i = 0; i < doubleMatrix.length; i++) {
			double newnumber = doubleMatrix[i].abs();
			if ((newnumber > doubleMatrix[maxIndex].abs())) {
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private static List<Double> getEigenVector(ComplexDoubleMatrix eigenvector,
			int columnId) {
		ComplexDoubleMatrix column = eigenvector.getColumn(columnId);

		List<Double> values = new ArrayList<Double>();
		for (ComplexDouble value : column.toArray()) {
			values.add(value.abs());
		}
		return values;
	}

	public static List<Double> normalised(List<Double> principalEigenvector) {
		double total = sum(principalEigenvector);
		List<Double> normalisedValues = new ArrayList<Double>();
		for (Double aDouble : principalEigenvector) {
			normalisedValues.add(aDouble / total);
		}
		return normalisedValues;
	}

	private static double sum(List<Double> principalEigenvector) {
		double total = 0;
		for (Double aDouble : principalEigenvector) {
			total += aDouble;
		}
		return total;
	}

	public static void main(String[] args) {
//		DoubleMatrix matrix = new DoubleMatrix(new double[][] {
//				{ 1, 1, 0, 0, 1, 0, 0 }, 
//				{ 1, 1, 0, 0, 1, 0, 0 },
//				{ 0, 0, 1, 1, 1, 0, 0 }, 
//				{ 0, 0, 1, 1, 1, 0, 0 },
//				{ 1, 1, 1, 1, 1, 0.5, 1 }, 
//				{ 0, 0, 0, 0, 1, 1, 1 },
//				{ 0, 0, 0, 0, 1, 1, 1 }, });
		EigenUtil eigen = new EigenUtil();
		DoubleMatrix matrix = new DoubleMatrix(new double[][] {
				{ 0, 1.5, 1.5, 1.5, 2, 3 }, 
				{ 0, 0, 0, 0, 0, 0 },
				{ 0, 0.5, 0, 0.5, 0.5, 1 }, 
				{ 0, 0, 0, 0, 0, 0 },
				{ 0, 0, 0, 0, 0, 0 }, 
				{ 0, 1.5, 1.5, 1.5, 1.5, 0 }, });

		ComplexDoubleMatrix eigenvalues = Eigen.eigenvalues(matrix);
		for (ComplexDouble eigenvalue : eigenvalues.toArray()) {
			System.out.print(String.format("%.2f ", eigenvalue.abs()));
		}
		System.out.println();
		List<Double> principalEigenvector = EigenUtil.getPrincipalEigenvector(matrix);
		System.out.println("principalEigenvector = " + principalEigenvector);
		
		List<Double> normalisedPrincipalEigenvector = EigenUtil.normalised(principalEigenvector);
		System.out.println("normalisedPrincipalEigenvector = " + normalisedPrincipalEigenvector);


	}
}
