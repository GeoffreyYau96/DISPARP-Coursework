package squareMatrixMultiply;

import java.util.Arrays;
import java.util.Objects;

public final class Matrix {

    private final int columns;
    private final int rows;
    private final double[] array;

    public Matrix(int rows, int columns) {
        this.rows = checkRows(rows);
        this.columns = checkColumns(columns);
        this.array = new double[rows * columns];
    }

    public Matrix(Matrix other) {
        Objects.requireNonNull(other, "The other matrix is null.");
        this.rows = other.rows;
        this.columns = other.columns;
        this.array = other.array.clone();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double get(int x, int y) {
        return array[index(x, y)];

    }

    public void set(int x, int y, double value) {
        array[index(x, y)] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String seprartor = "";

        for (int y = 0; y < rows; ++y) {
            sb.append(seprartor);
            seprartor = "\n";

            for (int x = 0; x < columns; ++x) {
                sb.append(get(x, y)).append(" ");
            }

        }

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!getClass().equals(o.getClass())) {
            return false;
        }

        Matrix other = (Matrix) o;

        if (getRows() != other.getRows()) {
            return false;
        }

        if (getColumns() != other.getColumns()) {
            return false;
        }

        return Arrays.equals(array, other.array);
    }

    private int index(int x, int y) {
        return y * columns + x;
    }


    private int checkRows(int rows) {
        return check(rows, "The nunber of rows is too small" + rows);
    }

    private int checkColumns(int columns) {
        return check(columns, "The nunber of columns is too small" + columns);
    }

    private int check(int number, String errorMessage) {
        if (number < 1) {
            throw new IllegalArgumentException(errorMessage);
        }
        return number;
    }
}
