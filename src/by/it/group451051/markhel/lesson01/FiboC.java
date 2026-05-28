package by.it.group451051.markhel.lesson01;

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
        // Используем период Пизано
        // Период Пизано - это период, с которым последовательность Фибоначчи по модулю m повторяется

        if (n <= 1) return n % m;

        // Находим период Пизано для m
        long period = findPisanoPeriod(m);
        long remainder = n % period;

        // Вычисляем F(remainder) % m
        if (remainder <= 1) return remainder;

        long prev = 0;
        long curr = 1;
        for (long i = 2; i <= remainder; i++) {
            long temp = (prev + curr) % m;
            prev = curr;
            curr = temp;
        }

        return curr % m;
    }

    private long findPisanoPeriod(int m) {
        long a = 0, b = 1, c;

        // Период Пизано не превышает 6*m
        for (int i = 0; i < 6 * m; i++) {
            c = (a + b) % m;
            a = b;
            b = c;

            // Если вернулись к начальной паре (0,1), значит нашли период
            if (a == 0 && b == 1) {
                return i + 1;
            }
        }

        return 6 * m; // fallback, хотя по теории Пизано всегда найдется период ≤ 6*m
    }
}

