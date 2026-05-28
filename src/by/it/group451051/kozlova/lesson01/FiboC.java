package by.it.group451051.kozlova.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

public class FiboC {
    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {
        FiboC fibo = new FiboC();
        long n = 1000000000000000000L;
        int m = 100000;
        System.out.printf("fasterC(%d,%d)=%d \n\t time=%d ms\n\n", n, m, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        int period = pisanoPeriod(m);
        long n_mod = n % period;
        return fibonacciModulo(n_mod, m);
    }

    private int pisanoPeriod(int m) {
        long previous = 0;
        long current = 1;
        for (int i = 0; i < m * 6; i++) {
            long temp = (previous + current) % m;
            previous = current;
            current = temp;
            if (previous == 0 && current == 1)
                return i + 1;
        }
        return m;
    }

    private long fibonacciModulo(long n, int m) {
        if (n == 0) return 0;
        if (n == 1) return 1;

        long[] result = fiboMatrix(n);

        long fibN = result[0] % m;
        return fibN;
    }

    private long[] fiboMatrix(long n) {

        long[] result = {1, 0};
        long[][] base = {{1, 1}, {1, 0}};
        long power = n - 1;

        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiplyMatrixVector(base, result);
            }
            base = multiplyMatrix(base, base);
            power >>= 1;
        }
        return result;
    }

    private long[] multiplyMatrixVector(long[][] matrix, long[] vector) {
        long a = matrix[0][0] * vector[0] + matrix[0][1] * vector[1];
        long b = matrix[1][0] * vector[0] + matrix[1][1] * vector[1];
        return new long[]{a, b};
    }


    private long[][] multiplyMatrix(long[][] a, long[][] b) {
        long[][] res = new long[2][2];
        res[0][0] = a[0][0] * b[0][0] + a[0][1] * b[1][0];
        res[0][1] = a[0][0] * b[0][1] + a[0][1] * b[1][1];
        res[1][0] = a[1][0] * b[0][0] + a[1][1] * b[1][0];
        res[1][1] = a[1][0] * b[0][1] + a[1][1] * b[1][1];
        return res;
    }
}

