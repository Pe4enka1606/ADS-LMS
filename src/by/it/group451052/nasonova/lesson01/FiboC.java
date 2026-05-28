package by.it.group451052.nasonova.lesson01;

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
        //Решение сложно найти интуитивно
        //возможно потребуется дополнительный поиск информации
        //см. период Пизано
        int period = pisanoPeriod(m);
        long nMod = n % period;
        return fibonacciModulo(nMod, m);
    }
private int pisanoPeriod(int m) {
        int prev  = 0;
        int curr = 1;

        for (int i = 0; i < 6 * m; i++) {
            int next = (prev + curr) % m;
            prev = curr;
            curr = next;

            if (prev == 0 && curr == 1) {
                return i + 1;
            }
        }
        return 6 * m;
}
private long fibonacciModulo(long n, int m) {
        if (n == 0) return 0L;
        if (n == 1) return 1L;

        int prev = 0;
        int curr = 1;

        for (long i = 2; i <= n; i++) {
            int next = (prev + curr) % m;
            prev = curr;
            curr = next;
        }
        return curr;
}
}

