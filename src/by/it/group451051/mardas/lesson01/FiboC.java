package by.it.group451051.mardas.lesson01;

/*
 * Даны целые числа 1<=n<=1E18 и 2<=m<=1E5,
 * необходимо найти остаток от деления n-го числа Фибоначчи на m.
 * время расчета должно быть не более 2 секунд
 */

import java.math.BigInteger;

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

        BigInteger m2=BigInteger.valueOf(m);
        BigInteger[] mas_fib = new BigInteger[9999];
        BigInteger[] mas_ost = new BigInteger[9999];
        int kol=1;
        int size=0;
        mas_fib[0]=BigInteger.ZERO;
        mas_ost[0]=BigInteger.ZERO;
        mas_fib[1]=BigInteger.ONE;
        mas_ost[1]=BigInteger.ONE;
        while (true) {
            kol++;
            mas_fib[kol] = mas_fib[kol - 1].add(mas_fib[kol - 2]);
            mas_ost[kol] = mas_fib[kol].mod(m2);
            if (kol % 2 == 1) {
                size = 1;
                for (int i = 1; i <= (kol + 1) / 2; i++)
                    if (mas_ost[i - 1].compareTo(mas_ost[i - 1 + (kol + 1) / 2]) != 0) {
                        size = 0;
                        break;
                    }
            }
            if (size == 1) {
                size = (kol + 1) / 2;
                break;
            }
        }
        return mas_ost[Math.toIntExact(n % size)].longValue();
    }
}

