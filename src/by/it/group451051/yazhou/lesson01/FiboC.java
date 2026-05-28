package by.it.group451051.yazhou.lesson01;

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
        int n = 10;
        int m = 2;
        System.out.printf("fasterC(%d)=%d \n\t time=%d \n\n", n, fibo.fasterC(n, m), fibo.time());
    }

    long fasterC(long n, int m) {
        if (n == 0) return 0;
        if (n == 1) return 1 % m;
        if (m == 1) return 0;

        long period = pisanoPeriod(m);

        long reducedN = n % period;

        return fibMod(reducedN, m);
    }

    private long pisanoPeriod(int m) {
        long prev = 0;
        long curr = 1;
        long period = 0;

        for (int i = 0; i < m * m; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
            period++;

            // Pisano period starts with 0,1
            if (prev == 0 && curr == 1) {
                return period;
            }
        }
        return period;
    }

    private long fibMod(long n, int m) {
        if (n == 0) return 0;
        if (n == 1) return 1 % m;

        long a = 0;
        long b = 1;
        for (long i = 2; i <= n; i++) {
            long c = (a + b) % m;
            a = b;
            b = c;
        }
        return b;
    }

}