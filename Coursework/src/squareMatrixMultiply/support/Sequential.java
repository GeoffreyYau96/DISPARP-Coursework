package squareMatrixMultiply.support;

import squareMatrixMultiply.Matrix;
import squareMatrixMultiply.MatrixMultiplier;

public final class Sequential implements MatrixMultiplier {

    @Override
    public Matrix multiply(Matrix left, Matrix right) {

        Matrix result = new Matrix(left.getRows(), right.getColumns());
        
        for (int y = 0; y < left.getRows(); ++y) {
            for (int x = 0; x < right.getColumns(); ++x) {
                double sum = 0.0;

                for (int i = 0; i < left.getColumns(); ++i) {
                    sum += left.get(i, y) * right.get(x, i);
                }

                result.set(x, y, sum);

            }
        }
        return result;
    }
}
