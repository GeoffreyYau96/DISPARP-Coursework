package squareMatrixMultiply;

import java.util.Random;
import squareMatrixMultiply.support.Parallel;
import squareMatrixMultiply.support.Sequential;

public class Demo {
    // matrix size
    final static int N = 1000;

    public static void main(String[] args) {
        Random random = new Random();
        // 1st matrix
        Matrix m1 = getRandomMatrix(N, N, random);
        // 2nd matrix
        Matrix m2 = getRandomMatrix(N, N, random);
        
        // create two new pair o
        Matrix m1b = new Matrix(m1);
        Matrix m2b = new Matrix(m2);
        
        long startTimeSeq = System.currentTimeMillis();
        Matrix seq = new Sequential().multiply(m1, m2);
        long endTimeSeq = System.currentTimeMillis();

        System.out.println("Sequential: " + (endTimeSeq - startTimeSeq));
        //System.out.println(seq);

        long startTimeParallel = System.currentTimeMillis();
        Matrix parallel = new Parallel().multiply(m1b, m2b);
        long endTimeParallel = System.currentTimeMillis();

        System.out.println("Parallel: " + (endTimeParallel - startTimeParallel));
        //System.out.println(parallel);
        
        // check whether they both have some value
        //System.out.println("Same result: " + seq.equals(parallel));

    }
    
    private static Matrix getRandomMatrix(int rows, int cols, Random random) {
        Matrix m = new Matrix(rows, cols);
        
        // generate random number for each element in the matrix
        for (int x = 0; x < cols; ++x) {
            for (int y = 0; y < rows; ++y) {
                m.set(x, y, random.nextDouble());
            }
        }

        return m;
    }
}
