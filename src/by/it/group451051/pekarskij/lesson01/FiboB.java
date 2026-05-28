package by.it.group451051.pekarskij.lesson01;

import java.math.BigInteger;

public class FiboB {

    private long startTime = System.currentTimeMillis();

    private long time() {
        return System.currentTimeMillis() - startTime;
    }

    public static void main(String[] args) {

        //вычисление чисел простым быстрым методом
        FiboB fibo = new FiboB();
        int n = 55555;
        System.out.printf("fastB(%d)=%d \n\t time=%d \n\n", n, fibo.fastB(n), fibo.time());
    }

    BigInteger fastB(Integer n) {
    //здесь нужно реализовать вариант с временем O(n) и памятью O(n)

    if (n == 0) return BigInteger.ZERO;
    if (n == 1) return BigInteger.ONE;

    BigInteger[] fib = new BigInteger[n + 1];
    fib[0] = BigInteger.ZERO;
    fib[1] = BigInteger.ONE;

    for (int i = 2; i <= n; i++) {
        fib[i] = fib[i - 1].add(fib[i - 2]);
    }

    return fib[n];
}

}

