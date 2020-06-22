package squareMatrixMultiply.support;

import squareMatrixMultiply.Matrix;
import squareMatrixMultiply.MatrixMultiplier;

public final class Parallel implements MatrixMultiplier {

    private static final int Min_Workload = 10_000;

    @Override
    public Matrix multiply(Matrix left, Matrix right) {

        int workload = left.getRows() * right.getColumns() * right.getRows();
        int numberOfThreads = Runtime.getRuntime().availableProcessors();
        //each thread has some workload
        numberOfThreads = Math.min(numberOfThreads, workload / Min_Workload);
        //make sure number of threads don't exceed the number of rows
        numberOfThreads = Math.min(numberOfThreads, left.getRows());
        numberOfThreads = Math.max(numberOfThreads, 1);
        
        // the last thread will just execute the seq version
        if (numberOfThreads == 1) {
            return new Sequential().multiply(left, right);
        }
        
        Matrix result = new Matrix(left.getRows(), right.getColumns());
        MultiplierThread[] threads = new MultiplierThread[numberOfThreads - 1];
        
        int RowsPerThreads = left.getRows() / numberOfThreads;
        int startRow = 0;
        
        // add threads and set the startRow of each thread
        for (int i = 0; i < threads.length; ++i) {
            threads[i] = new MultiplierThread(left, right, result, startRow, RowsPerThreads);
            threads[i].start();
            startRow += RowsPerThreads;
        }
        
        new MultiplierThread(left, right, result, startRow, RowsPerThreads + left.getRows() % RowsPerThreads).run();
        
        // join result from each thread together
        for (MultiplierThread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException ex) {
                throw new RuntimeException("A thread interrupted", ex);
            }
        }
        
        return result;
    }

    private static final class MultiplierThread extends Thread {

        private final Matrix left;
        private final Matrix right;
        private final Matrix result;
        private final int startRow;
        private final int RowsPerThreads;

        public MultiplierThread(Matrix left, Matrix right, Matrix result, int startRow, int rows) {
            this.left = left;
            this.right = right;
            this.result = result;
            this.startRow = startRow;
            this.RowsPerThreads = rows;
        }

        @Override
        public void run() {
            for (int y = startRow; y < startRow + RowsPerThreads; ++y) {
                for (int x = 0; x < right.getColumns(); ++x) {
                    double sum = 0.0;

                    for (int i = 0; i < left.getColumns(); ++i) {
                        sum += left.get(i, y) * right.get(x, i);
                    }

                    result.set(x, y, sum);
                }
            }
        }

    }
}
