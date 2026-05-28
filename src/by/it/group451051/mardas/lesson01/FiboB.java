package by.it.group451051.mardas.lesson01;

import java.math.BigInteger;

/*
 * Вам необходимо выполнить способ вычисления чисел Фибоначчи с вспомогательным массивом
 * без ограничений на размер результата (BigInteger)
 */

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
        BigInteger[] mas_fib;
        mas_fib = new BigInteger[n+1];
        mas_fib[0]=BigInteger.ZERO;
        mas_fib[1]=BigInteger.ONE;
        for (int i=2;i<=n;i++) mas_fib[i]=mas_fib[i-1].add(mas_fib[i-2]);
        if (n==0) return BigInteger.ZERO;
        if (n==1) return BigInteger.ONE;
        if (n>1) return mas_fib[n];
        return null;
    }

}

