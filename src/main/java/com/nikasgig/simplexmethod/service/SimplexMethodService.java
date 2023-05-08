package com.nikasgig.simplexmethod.service;

import java.util.List;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 *
 * @author NikAS GiG
 */
public class SimplexMethodService {

    public static int num = 1;
    
    public static double[][] simplexMethod(double[][] A, double[] b, double[] c) {
        // Initialize the tableau
        double[][] table = new double[A.length + 1][A[0].length + 1];
        for (int i = 0; i < A.length; i++) {
            System.arraycopy(A[i], 0, table[i + 1], 0, A[0].length);
        }
        System.arraycopy(c, 0, table[0], 0, c.length);
        for (int i = 0; i < b.length; i++) {
            table[i + 1][A[0].length] = b[i];
        }
        int m = table.length;
        int n = table[0].length;
        while (true) {
            // Find the entering variable
            Integer entering = null;
            for (int j = 0; j < n - 1; j++) {
                if (table[0][j] > 0 && (entering == null || table[0][j] > table[0][entering])) {
                    entering = j;
                }
            }

            if (entering == null) {
                // Optimal solution found
                break;
            }
            // Find the leaving variable
            Integer leaving = null;
            for (int i = 1; i < m; i++) {
                if (table[i][entering] > 0) {
                    if (leaving == null) {
                        leaving = i;
                    } else if (table[i][n - 1] / table[i][entering]
                            < table[leaving][n - 1] / table[leaving][entering]) {
                        leaving = i;
                    }
                }
            }

            if (leaving == null) {
                // Unbounded solution
                return null;
            }

            // Pivot around the entering and leaving variables
            double pivot = table[leaving][entering];
            for (int j = 0; j < n; j++) {
                table[leaving][j] /= pivot;
            }
            for (int i = 0; i < m; i++) {
                if (i != leaving) {
                    double factor = table[i][entering];
                    for (int j = 0; j < n; j++) {
                        table[i][j] -= factor * table[leaving][j];
                    }
                }
            }
        }

        return table;
    }

    public static double[] extractResults(double[][] table) {
        int m = table.length;
        int n = table[0].length;
        System.out.println("I am here 1");
        double[] result = {0,0};
        for (int j = 0; j < n - 1; j++) {
            final int colIndex = j;
            List<double[]> nonzeroRows;
            nonzeroRows = Arrays.stream(table)
                    .map((double[] row) -> new double[]{row[colIndex], 0})
                    .filter(pair -> pair[0] != 0)
                    .collect(Collectors.toList());
            if (nonzeroRows.size() == 1 && nonzeroRows.get(0)[0] == 1) {
                result[1] = table[(int) nonzeroRows.get(0)[1]][n - 1];
                if (j == n - 2) {
                    result[0] = table[0][n - 1];
                }
            }
            System.out.println("I am here 2 + " + j);
        }
        return result;
    }

//    public static StringBuilder simplex(double[][] A, double[] b, double[] c) {
//        StringBuilder result = new StringBuilder();
//        int m = b.length; // кількість обмежень
//        int n = c.length; // кількість змінних
//
//        // Створення нової матриці A з базисними та небазисними змінних
//        double[][] newA = new double[m][m + n];
//        for (int i = 0; i < m; i++) {
//            for (int j = 0; j < n; j++) {
//                newA[i][j] = A[i][j];
//            }
//            newA[i][n + i] = 1;
//        }
//
//        // Створення нового масиву c з коєфіцієнтами базисних та небазисних змінних
//        double[] newc = new double[m + n];
//        for (int i = 0; i < n; i++) {
//            newc[i] = c[i];
//        }
//
//        // Знаходження початкової базисної точки
//        int[] basis = new int[m];
//        for (int i = 0; i < m; i++) {
//            basis[i] = n + i;
//        }
//
//        // Виконання ітерацій симплекс методу
//        while (true) {
//            // Обрахування вектора поточних базисних змінних та значення функції
//            double[] x = new double[n + m];
//            double value = 0;
//            for (int i = 0; i < m; i++) {
//                x[basis[i]] = b[i];
//                value += newc[basis[i]] * b[i];
//            }
//
//            // Обрахування вектора допоміжних змінних
//            double[] y = new double[n + m];
//            for (int j = 0; j < n + m; j++) {
//                double sum = 0;
//                for (int i = 0; i < m; i++) {
//                    sum += newA[i][j] * x[basis[i]];
//                }
//                y[j] = newc[j] - sum;
//            }
//
//            // Перевірка на оптимальність
//            boolean optimal = true;
//            for (int j = 0; j < n + m; j++) {
//                if (y[j] > 0) {
//                    optimal = false;
//                    break;
//                }
//            }
//            if (optimal) {
//                System.out.println("Optimal solution found: " + Arrays.toString(x));
//                System.out.println("Optimal value: " + value);
//                result.append("Optimal solution found: ").append(Arrays.toString(x));
//                result.append("\n");
//                result.append("Optimal value: ").append(value);
//                result.append("\n");
//                return result;
//            }
//
//            // Вибір ведучої змінної
//            int lead = -1;
//            for (int j = 0; j < n + m; j++) {
//                if (y[j] > 0) {
//                    if (lead == -1 || y[j] > y[lead]) {
//                        lead = j;
//                    }
//                }
//            }
//
//            if (lead == -1) {
//                System.out.println("Unbounded solution");
//                result.append("Unbounded solution");
//                result.append("\n");
//                return result;
//            }
//
//            // Вибір ведучого рядка
//            int row = -1;
//            for (int i = 0; i < m; i++) {
//                if (newA[i][lead] > 0) {
//                    if (row == -1 || x[basis[i]] / newA[i][lead] < x[basis[row]] / newA[row][lead]) {
//                        row = i;
//                    }
//                }
//            }
//
//            if (row == -1) {
//                System.out.println("Unbounded solution");
//                result.append("Unbounded solution");
//                result.append("\n");
//                return result;
//            }
//
//            // Виконання елементарного рядкового перетворення
//            double pivot = newA[row][lead];
//            for (int j = 0; j < n + m; j++) {
//                newA[row][j] /= pivot;
//            }
//            b[row] /= pivot;
//
//            for (int i = 0; i < m; i++) {
//                if (i != row) {
//                    double factor = newA[i][lead];
//                    for (int j = 0; j < n + m; j++) {
//                        newA[i][j] -= factor * newA[row][j];
//                    }
//                    b[i] -= factor * b[row];
//                }
//            }
//
//            // Оновлення базисної змінної
//            basis[row] = lead;
//            System.out.println(num + " ");
//            num++;
//            if (num >= 1000) {
//                System.out.println("Loop of calculate is more 1000 times");
//                result.append("Loop of calculate is more 1000 times");
//                result.append("\n");
//                System.out.println("Simplex method is most likely unsolvable");
//                result.append("Simplex method is most likely unsolvable");
//                num = 1;
//                return result;
//            }
//        }
//    }
}
