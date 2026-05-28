package by.it.group451051.shiman.lesson01;

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

        // Период Пизано - период, с которым повторяются остатки от деления
        // чисел Фибоначчи на m
        long period = findPisanoPeriod(m);

        // Находим эквивалентное n в пределах периода
        long equivalentN = n % period;

        // Если equivalentN равен 0 или 1, возвращаем соответствующее значение
        if (equivalentN == 0) return 0;
        if (equivalentN == 1) return 1;

        // Вычисляем остаток для equivalentN
        long prev = 0;
        long curr = 1;

        for (long i = 2; i <= equivalentN; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }

        return curr;
    }

    // Метод для нахождения периода Пизано для данного m
    private long findPisanoPeriod(int m) {
        long prev = 0;
        long curr = 1;
        long period = 0;

        // Период Пизано ищется до момента, когда снова появятся
        // остатки 0 и 1 подряд
        for (long i = 0; i <= (long)m * m; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
            period++;

            // Если снова появилась пара (0, 1) - мы нашли период
            if (prev == 0 && curr == 1) {
                return period;
            }
        }

        return period;
    }
}