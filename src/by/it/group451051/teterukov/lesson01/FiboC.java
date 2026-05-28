package by.it.group451051.teterukov.lesson01;

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
        if (n <= 1)
            return n % m;
        long a = 0;
        long b = 1;
        long period_Pisano = 0;
//поиск периода Пизано
        //ссылка на wiki где доказано почему 6*m, а не m*m https://en.wikipedia.org/wiki/Pisano_period
        for (int i = 0; i < 6 * m; i++) {
            // переход на новое число для проверки (нужно чтобы было 0,1, тогда период найден)
            // самый быстрый способ вычисления числа Фибоначчи, где не нужна заполнять кеш(матрицу) ненужными числами
            long next_number = (a + b) % m;
            a = b;
            b = next_number;
            if (a == 0 && b == 1) {
                period_Pisano = i + 1;
                break;
            }
        }
        n = n % period_Pisano;

        a = 0;
        b = 1;
        for (int i = 2; i <= n; i++) {
            long next_number = (a + b) % m;
            a = b;
            b = next_number;
        }

        return b;
    }

}